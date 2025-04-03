package solution.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // ========== Стандартные методы ==========
    // findAll() - уже есть
    // findById() - уже есть
    // save() - уже есть
    // deleteById() - уже есть

    /**
     * Проверка существования товара по имени (стандартный метод)
     */
    boolean existsByName(String name);

    /**
     * Подсчет товаров по категории (стандартный метод)
     */
    long countByCategory(String category);

    // ========== Named Query методы ==========

    /**
     * Найти все активные товары в указанной категории
     */
    List<Product> findByActiveTrueAndCategory(String category);

    /**
     * Найти товары с ценой в заданном диапазоне
     */
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") Double minPrice,
                                   @Param("maxPrice") Double maxPrice);

    /**
     * Найти топ-3 самых дорогих товара
     */
    List<Product> findTop3ByOrderByPriceDesc();

    /**
     * Обновить количество товара по ID
     */
    @Modifying
    @Query("UPDATE Product p SET p.quantity = :quantity WHERE p.id = :id")
    void updateQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    /**
     * Посчитать среднюю цену по категориям
     */
    @Query("SELECT p.category, AVG(p.price) FROM Product p GROUP BY p.category")
    List<Object[]> findAveragePriceByCategory();

    /**
     * Найти товары по нескольким категориям
     */
    List<Product> findByCategoryIn(List<String> categories);

    /**
     * Частичное обновление товара
     */
    @Modifying
    @Query("UPDATE Product p SET p.price = :price, p.quantity = :quantity WHERE p.id = :id")
    void updatePriceAndQuantity(@Param("id") Long id,
                                @Param("price") Double price,
                                @Param("quantity") Integer quantity);

    /**
     * Поиск активных товаров с пагинацией
     */
    Page<Product> findByActiveTrue(Pageable pageable);
}
