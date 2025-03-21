package com.example.ecomappjava.services.product;

import com.example.ecomappjava.Reporsitory.CategoryRepository;
import com.example.ecomappjava.Reporsitory.ProductRepository;
import com.example.ecomappjava.dtos.product.SortParam;
import com.example.ecomappjava.dtos.product.SortType;
import com.example.ecomappjava.exceptions.product.CategoryNotFoundException;
import com.example.ecomappjava.exceptions.product.ProductNotFoundException;
import com.example.ecomappjava.models.Category;
import com.example.ecomappjava.models.Product;
import com.example.ecomappjava.models.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Product addProduct(Product product) {

        Optional<Category> categoryOptional = categoryRepository.findCategoryByName(product.getCategory().getName());
        if (categoryOptional.isPresent()) {
            product.setCategory(categoryOptional.get());
        } else {
            Category category = product.getCategory();
            category.setState(State.ACTIVE);
            category = categoryRepository.save(category);
            product.setCategory(category);
        }

        product.setState(State.ACTIVE);
        product = productRepository.save(product);
        return product;

    }

    @Override
    public Page<Product> searchProducts(String query, Integer page, Integer size, List<SortParam> sortParams) {
        Sort sort = null;

        if(sortParams != null && !sortParams.isEmpty()){

            for(int i = 0; i < sortParams.size(); i++){
                SortParam sortParam = sortParams.get(i);
                Sort.Direction direction = sortParam.getSortType() == SortType.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC;
                if(sort == null) {
                    sort = Sort.by(direction, sortParam.getSortCriteria());
                }
                else{
                    sort = sort.and(Sort.by(direction, sortParam.getSortCriteria()));
                }
            }
        }

        Pageable pageable = sort != null ? PageRequest.of(page, size, sort) : PageRequest.of(page, size) ;
        return productRepository.findByNameContainingAndState(query, State.ACTIVE,  pageable);
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findProductByIdAndState(id, State.ACTIVE);

        return productOptional.orElse(null);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Optional<Category> categoryOptional = categoryRepository.findCategoryByName(product.getCategory().getName());
        if (categoryOptional.isPresent()) {
            product.setCategory(categoryOptional.get());
        } else {
            Category category = product.getCategory();
            category.setState(State.ACTIVE);
            category = categoryRepository.save(category);
            product.setCategory(category);
        }
        product.setId(productId);
        product.setState(State.ACTIVE);
        product = productRepository.save(product);
        return product;
    }

    @Override
    public List<Product> getAllProductsForCategory(String categoryName) {
        Optional<Category> categoryOptional = categoryRepository.findCategoryByName(categoryName);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            return category.getProducts();
        }
        else {
            throw new CategoryNotFoundException("Category not found");
        }
    }

    @Override
    public Product updateProductStock(Long productId, Integer stock) {
        Product product = getProductById(productId);
        if (product == null){
            throw new ProductNotFoundException("Product not found");
        }
        product.setStock(product.getStock() - stock);

        product = productRepository.save(product);
        return product;
    }
}
