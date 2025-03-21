package com.example.ecomappjava.utility.auth.anotation;

import com.example.ecomappjava.Reporsitory.UserReporsitory;
import com.example.ecomappjava.exceptions.UnauthorizedException;
import com.example.ecomappjava.models.AuthUser;
import com.example.ecomappjava.models.State;
import com.example.ecomappjava.utility.auth.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class RoleCheckAspect {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private UserReporsitory userReporsitory;

    @Before("@annotation(RoleRequired)")
    public void checkRole(JoinPoint joinPoint) throws UnauthorizedException {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                throw new RuntimeException("No request context found");
            }

            HttpServletRequest request = attributes.getRequest();
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Authorization header missing or invalid");
            }

            String token = authHeader.substring(7);

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            RoleRequired roleRequired = method.getAnnotation(RoleRequired.class);
            String requiredRole = roleRequired.value();
            boolean checkUser = roleRequired.checkUser();

            Claims claims = jwtUtil.getClaims(token);
            List<String> roles = claims.get("roles", List.class);
            Long userId = claims.get("userId", Long.class);

            if (userId == null || roles == null) {
                throw new UnauthorizedException("Invalid token payload: Missing user details");
            }


            if (!roles.contains(requiredRole)) {
                throw new UnauthorizedException("You are not authorized to perform this action");
            }

            if(checkUser){
                Optional<AuthUser> userOptional = userReporsitory.findByIdAndState(userId, State.ACTIVE);
                if (userOptional.isEmpty()) {
                    throw new UnauthorizedException("User not found");
                }

                AuthUser user = userOptional.get();
                request.setAttribute("currentUser", user);
            }

        }
        catch (Exception e) {
            throw new UnauthorizedException("You are not authorized to perform this action due to invalid or expried token");
        }
    }
}
