package solution.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import solution.service.ProductService;
import solution.domain.Product;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.created(URI.create("/api/products/" + createdProduct.getId()))
                .body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @Valid @RequestBody Product productDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, productDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkProductExists(@RequestParam String name) {
        return ResponseEntity.ok(productService.productExists(name));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countProductsByCategory(@RequestParam String category) {
        return ResponseEntity.ok(productService.countProductsByCategory(category));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Product>> getActiveProductsByCategory(@RequestParam String category) {
        return ResponseEntity.ok(productService.getActiveProductsByCategory(category));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        return ResponseEntity.ok(productService.getProductsByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/top-expensive")
    public ResponseEntity<List<Product>> getTop3MostExpensiveProducts() {
        return ResponseEntity.ok(productService.getTop3MostExpensiveProducts());
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<Void> updateProductQuantity(@PathVariable Long id,
                                                      @RequestParam Integer quantity) {
        productService.updateProductQuantity(id, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/avg-price")
    public ResponseEntity<Map<String, Double>> getAveragePriceByCategory() {
        return ResponseEntity.ok(productService.getAveragePriceByCategory());
    }


    /**
     * Пример запроса:
     * http://localhost:8080/api/products/by-categories?categories=Electronics&categories=Clothing
     * @param categories список категорий
     * @return
     */
    @GetMapping("/by-categories")
    public ResponseEntity<List<Product>> getProductsByCategories(
            @RequestParam List<String> categories) {
        return ResponseEntity.ok(productService.getProductsByCategories(categories));
    }

    @PatchMapping("/{id}/price-quantity")
    public ResponseEntity<Void> updateProductPriceAndQuantity(
            @PathVariable Long id,
            @RequestParam Double price,
            @RequestParam Integer quantity) {
        productService.updateProductPriceAndQuantity(id, price, quantity);
        return ResponseEntity.ok().build();
    }

    /**
     * Примеры запросов:
     * http://localhost:8080/api/products/active-paged?page=0&size=10 - получение первой страницы с 10и записями
     * http://localhost:8080/api/products/active-paged?page=1&size=10 - получение второй страницы с 10и записями
     * http://localhost:8080/api/products/active-paged?page=3&size=10 - получение третьей страницы с 10и записями
     * @param page номер страницы
     * @param size кол-во записей на странице
     * @return
     */
    @GetMapping("/active-paged")
    public ResponseEntity<Page<Product>> getActiveProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getActiveProducts(PageRequest.of(page, size)));
    }
}
