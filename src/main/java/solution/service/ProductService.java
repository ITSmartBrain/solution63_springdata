package solution.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import solution.domain.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ProductService {
    Product getProductById(Long id);

    List<Product> getAllProducts();

    Product createProduct(Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    boolean productExists(String name);

    long countProductsByCategory(String category);

    List<Product> getActiveProductsByCategory(String category);

    List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice);

    List<Product> getTop3MostExpensiveProducts();

    void updateProductQuantity(Long id, Integer quantity);

    Map<String, Double> getAveragePriceByCategory();

    // Дополнительные методы
    List<Product> getProductsByCategories(List<String> categories);

    void updateProductPriceAndQuantity(Long id, Double price, Integer quantity);

    Page<Product> getActiveProducts(Pageable pageable);
}
