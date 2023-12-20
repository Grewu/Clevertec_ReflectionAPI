package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.db.ConnectionManager;
import org.example.dto.ProductDto;
import org.example.entity.Product;
import org.example.exception.ProductCreateException;
import org.example.exception.ProductDeleteException;
import org.example.exception.ProductNotFoundException;
import org.example.exception.ProductUpdateException;
import org.example.mapper.ProductMapper;
import org.example.pattern.singleton.ConnectionManagerSingleton;
import org.example.proxy.annotation.CreateProduct;
import org.example.proxy.annotation.DeleteProduct;
import org.example.proxy.annotation.GetProduct;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ProductDaoImpl implements ProductDao {
    private static final ConnectionManager connectionManager = ConnectionManagerSingleton.getInstance();
    private final ProductMapper productMapper;
    private static final String SELECT_BY_UUID_SQL = "SELECT * FROM product WHERE uuid = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM product";
    private static final String INSERT_SQL = "INSERT INTO product (uuid, name, description, price, created) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE product SET name = ?, description = ?, price = ?, created = ? WHERE uuid = ?";
    private static final String DELETE_SQL = "DELETE FROM product WHERE uuid = ?";

    @Override
    public ProductDto get(UUID uuid) {
        try (Connection connection = connectionManager.open();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_UUID_SQL)) {
            statement.setObject(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = new Product();
                product.setUuid((UUID) resultSet.getObject("uuid"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(new BigDecimal(resultSet.getString("price")));
                product.setCreated(LocalDateTime.parse(resultSet.getString("created")));
                return productMapper.toProductDto(product);
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
                Product product = new Product();
                product.setUuid((UUID) resultSet.getObject("uuid"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(new BigDecimal(resultSet.getString("price")));
                product.setCreated(LocalDateTime.parse(resultSet.getString("created")));
                products.add(productMapper.toProductDto(product));
            }
        } catch (SQLException e) {
            throw new ProductNotFoundException();
        }
        return products;
    }

    @Override
    @CreateProduct
    public UUID create(ProductDto productDto) {
        try (Connection connection = connectionManager.open();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setObject(1, productDto.uuid());
            statement.setString(2, productDto.name());
            statement.setString(3, productDto.description());
            statement.setBigDecimal(4, productDto.price());
            statement.executeUpdate();
            return productDto.uuid();
        } catch (SQLException e) {
            throw new ProductCreateException(e);
        }
    }

    @Override
    public void update(UUID uuid, ProductDto productDto) throws ProductUpdateException {
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
    @DeleteProduct
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
    @GetProduct
    public Optional<Product> findById(UUID uuid) {
        try (Connection connection = connectionManager.open();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_UUID_SQL)) {
            statement.setObject(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = new Product();
                product.setUuid((UUID) resultSet.getObject("uuid"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                String priceString = resultSet.getString("price");
                product.setPrice(new BigDecimal(priceString));
                String createdString = resultSet.getString("created");
                if (createdString != null && !createdString.isEmpty()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    product.setCreated(LocalDateTime.parse(createdString, formatter));
                }
                return Optional.of(product);
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
                Product product = new Product();
                product.setUuid((UUID) resultSet.getObject("uuid"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(new BigDecimal(resultSet.getString("price")));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(resultSet.getString("created"), formatter);
                product.setCreated(dateTime);
                products.add(product);
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

}
