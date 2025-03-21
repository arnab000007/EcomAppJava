package com.example.ecomappjava.Reporsitory;

import com.example.ecomappjava.models.AuthUser;
import com.example.ecomappjava.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findCategoryByName(String name);
    Category save(Category category);
    Optional<Category> findCategoryById(Long id);
}
