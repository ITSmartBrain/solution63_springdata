package solution.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solution.exception.BusinessException;
import solution.exception.ResourceNotFoundException;
import solution.domain.Product;
import solution.domain.ProductRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        if (productRepository.existsByName(product.getName())) {
            throw new BusinessException("Product with name '" + product.getName() + "' already exists");
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setCategory(productDetails.getCategory());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setActive(productDetails.getActive());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    @Override
    public boolean productExists(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public long countProductsByCategory(String category) {
        return productRepository.countByCategory(category);
    }

    @Override
    public List<Product> getActiveProductsByCategory(String category) {
        return productRepository.findByActiveTrueAndCategory(category);
    }

    @Override
    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }

    @Override
    public List<Product> getTop3MostExpensiveProducts() {
        return productRepository.findTop3ByOrderByPriceDesc();
    }

    @Override
    public void updateProductQuantity(Long id, Integer quantity) {
        productRepository.updateQuantity(id, quantity);
    }

    @Override
    public Map<String, Double> getAveragePriceByCategory() {
        List<Object[]> results = productRepository.findAveragePriceByCategory();
        return results.stream()
                .collect(Collectors.toMap(
                        arr -> (String) arr[0],
                        arr -> (Double) arr[1]
                ));
    }

    // Дополнительные методы
    @Override
    public List<Product> getProductsByCategories(List<String> categories) {
        return productRepository.findByCategoryIn(categories);
    }

    @Override
    public void updateProductPriceAndQuantity(Long id, Double price, Integer quantity) {
        productRepository.updatePriceAndQuantity(id, price, quantity);
    }

    @Override
    public Page<Product> getActiveProducts(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable);
    }
}
