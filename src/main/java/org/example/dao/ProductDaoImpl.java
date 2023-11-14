package org.example.dao;

import org.example.dto.ProductDto;
import org.example.entity.Product;
import org.example.exception.*;
import org.example.singleton.ConnectionManagerSingleton;
import org.example.util.ConnectionManager;


import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductDaoImpl implements ProductDao {
    private static final ConnectionManager connectionManager = ConnectionManagerSingleton.getInstance();

    private static final String SELECT_BY_UUID_SQL = "SELECT * FROM products WHERE uuid = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM products";
    private static final String INSERT_SQL = "INSERT INTO products (uuid, name, description, price, created) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE products SET name = ?, description = ?, price = ?, created = ? WHERE uuid = ?";
    private static final String DELETE_SQL = "DELETE FROM products WHERE uuid = ?";

    @Override
    public ProductDto get(UUID uuid) {
        try (Connection connection = connectionManager.open();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_UUID_SQL)) {
            statement.setObject(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapProductDto(resultSet);
            }
        } catch (SQLException e) {
            throw new ProductNotFoundException(uuid);
        }
        return null;
    }

    @Override
    public List<ProductDto> getAll() {
        List<ProductDto> products = new ArrayList<>();
        try (Connection connection = connectionManager.open();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL);
            while (resultSet.next()) {
                products.add(mapProductDto(resultSet));
            }
        } catch (SQLException e) {
            throw new ProductNotFoundException();
        }
        return products;
    }

    @Override
    public UUID create(ProductDto productDto) {
        try (Connection connection = connectionManager.open();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            UUID uuid = UUID.randomUUID();
            statement.setObject(1, uuid);
            statement.setString(2, productDto.name());
            statement.setString(3, productDto.description());
            statement.setBigDecimal(4, productDto.price());
            statement.executeUpdate();
            return uuid;
        } catch (SQLException e) {
            throw new ProductCreateException(e);
        }
    }

    @Override
    public void update(UUID uuid, ProductDto productDto) {
        try (Connection connection = connectionManager.open();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setObject(1, uuid);
            statement.setString(2, productDto.name());
            statement.setString(3, productDto.description());
            statement.setBigDecimal(4, productDto.price());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ProductUpdateException(uuid);
        }
    }

    @Override
    public void delete(UUID uuid) {
        try (Connection connection = connectionManager.open();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setObject(1, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ProductDeleteException(uuid);
        }
    }
    @Override
    public Optional<Product> findById(UUID uuid) {
        try (Connection connection = connectionManager.open();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_UUID_SQL)) {
            statement.setObject(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapProduct(resultSet));
            }
        } catch (SQLException e) {
            throw new ProductNotFoundException(uuid);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = connectionManager.open();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL);
            while (resultSet.next()) {
                products.add(mapProduct(resultSet));
            }
        } catch (SQLException e) {
            throw new ProductNotFoundException();
        }
        return products;
    }

    @Override
    public Product save(Product product) {
        try (Connection connection = connectionManager.open();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setObject(1, product.getUuid());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setBigDecimal(4, product.getPrice());
            statement.setObject(5, product.getCreated());
            statement.executeUpdate();
            return product;
        } catch (SQLException e) {
            throw new ProductCreateException(e);
        }
    }

    private ProductDto mapProductDto(ResultSet resultSet) throws SQLException {
        return new ProductDto(
                (UUID) resultSet.getObject("uuid"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getBigDecimal("price"),
                resultSet.getTimestamp("created").toLocalDateTime()
        );
    }
    private Product mapProduct(ResultSet resultSet) throws SQLException {
        UUID uuid = (UUID) resultSet.getObject("uuid");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        BigDecimal price = resultSet.getBigDecimal("price");
        LocalDateTime created = resultSet.getObject("created", LocalDateTime.class);
        return new Product(uuid, name, description, price,created);
    }
}
