package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.OrdersItems;
import com.qa.ims.utils.DBUtils;

public class OrdersItemsDAO implements Dao<OrdersItems>{
	
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public OrdersItems modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long orderId = resultSet.getLong("order_id");
		Long itemId = resultSet.getLong("item_id");
		Long quantity = resultSet.getLong("quantity");
		return new OrdersItems(orderId, itemId, quantity);
	}
	
	@Override
	public List<OrdersItems> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders_items");) {
			List<OrdersItems> ordersItems = new ArrayList<>();
			while (resultSet.next()) {
				ordersItems.add(modelFromResultSet(resultSet));
			}
			return ordersItems;
			
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}
	
	public OrdersItems readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders_items ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public OrdersItems create(OrdersItems ordersItems) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO orders_items(order_id, item_id, quantity) VALUES (?,?,?)");) {
			statement.setLong(1, ordersItems.getOrderId());
			statement.setLong(2, ordersItems.getItemId());
			statement.setLong(3, ordersItems.getQuantity());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public OrdersItems read(Long orderId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders_items WHERE order_id = ?");) {
			statement.setLong(1, orderId);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public OrdersItems update(OrdersItems ordersItems) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE orders_items SET item_id = ?, quantity = ? WHERE order_id = ?");) {
			statement.setLong(1, ordersItems.getItemId());
			statement.setLong(2, ordersItems.getQuantity());
			statement.setLong(3, ordersItems.getOrderId());
			statement.executeUpdate();
			return read(ordersItems.getOrderId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public int delete(long orderId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM customers WHERE order_id = ?");) {
			statement.setLong(1, orderId);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

	


	
	
}
