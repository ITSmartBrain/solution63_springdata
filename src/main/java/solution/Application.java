package solution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
Часть 1: Стандартные методы репозитория
1. Реализовать базовые CRUD операции
2. Добавить проверку существования товара по имени
3. Реализовать подсчет товаров по категории

Часть 2: Named Query методы
1. Найти все активные товары в указанной категории
2. Найти товары с ценой в заданном диапазоне
3. Найти топ-3 самых дорогих товара
4. Обновить количество товара по ID
5. Найти товары, добавленные после указанной даты
6. Посчитать среднюю цену по категориям

Дополнительное задание
1. Реализовать метод для поиска товаров по нескольким категориям
2. Создать метод для частичного обновления товара (например, только цены и количества)
3. Реализовать пагинацию для метода поиска активных товаров

Написать Интерфейс ProductService и его реализацию ProductServiceImpl

Написать контроллер ProductController

Написать Глобальный обработчик исключений
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}

