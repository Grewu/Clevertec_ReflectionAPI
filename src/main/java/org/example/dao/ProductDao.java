package org.example.dao;

import org.example.dto.ProductDto;
import org.example.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductDao {
    /**
     * Получает продукт по его уникальному идентификатору.
     *
     * @param uuid уникальный идентификатор продукта.
     * @return продукт с указанным идентификатором или null, если не найден.
     */
    ProductDto get(UUID uuid);

    /**
     * Получает список всех существующих продуктов.
     *
     * @return список содержащий информацию о всех продуктах.
     */
    List<ProductDto> getAll();

    /**
     * Создает новый продукт, используя предоставленную информацию.
     *
     * @param productDto информация о продукте, который нужно создать.
     * @return уникальный идентификатор, присвоенный продукту.
     */
    UUID create(ProductDto productDto);

    /**
     * Обновляет существующий продукт с использованием предоставленной информации.
     *
     * @param uuid       уникальный идентификатор продукта, который нужно обновить.
     * @param productDto информация для обновления существующего продукта.
     */
    void update(UUID uuid, ProductDto productDto);

    /**
     * Удаляет существующий продукт по его уникальному идентификатору.
     *
     * @param uuid уникальный идентификатор продукта, который нужно удалить.
     */
    void delete(UUID uuid);

    /**
     * Ищет в памяти продукт по идентификатору
     *
     * @param uuid идентификатор продукта
     * @return Optional<Product> если найден, иначе Optional.empty()
     */
    Optional<Product> findById(UUID uuid);

    /**
     * Ищет все продукты в памяти
     *
     * @return список найденных продуктов
     */
    List<Product> findAll();

    /**
     * Сохраняет или обновляет продукт в памяти
     *
     * @param product сохраняемый продукт
     * @return сохранённый продукт
     * @throws IllegalArgumentException если переданный продукт null
     */
    Product save(Product product);

}
