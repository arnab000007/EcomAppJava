package com.example.ecomappjava.services.product;

import com.example.ecomappjava.dtos.product.SortParam;
import com.example.ecomappjava.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {

    Product addProduct(Product product);
    Page<Product> searchProducts(String query, Integer page, Integer size, List<SortParam> sortParams);
    Product getProductById(Long id);
    Product updateProduct(Long productId, Product product);
    List<Product> getAllProductsForCategory(String categoryName);
    Product updateProductStock(Long productId, Integer stock);
}
