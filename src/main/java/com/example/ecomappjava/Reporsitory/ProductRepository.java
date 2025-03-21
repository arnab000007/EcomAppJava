package com.example.ecomappjava.Reporsitory;

import com.example.ecomappjava.models.AuthUser;
import com.example.ecomappjava.models.Product;
import com.example.ecomappjava.models.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);
    Optional<Product> findProductByIdAndState(Long id, State state);

    @Query("SELECT p FROM Product p WHERE p.state = :state AND p.name LIKE(CONCAT('%', :keyword, '%'))")
    Page<Product> findByNameContainingAndState(@Param("keyword")String keyword, @Param("state") State state, Pageable pageable);
}
