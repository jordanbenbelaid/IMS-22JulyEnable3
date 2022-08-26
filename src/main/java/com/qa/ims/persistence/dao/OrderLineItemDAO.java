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

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderLineItem;
import com.qa.ims.utils.DBUtils;

public class OrderLineItemDAO implements Dao<OrderLineItem> {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	
	/**
	 * Creates an OrderLineItem instance from the result set
	 */
	@Override
	public OrderLineItem modelFromResultSet(ResultSet resultSet) {
		try {
			ItemDAO itemDAO = new ItemDAO();
			Long id = resultSet.getLong("id");
			Long itemId = resultSet.getLong("item_id");
			Long quantity = resultSet.getLong("quantity");
			Long orderId = resultSet.getLong("order_id");
			Item item = itemDAO.read(itemId);
			return new OrderLineItem(id, item, quantity, orderId);
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.info("New line item for this order");
			return null;
		}
		
	}

	
	/**
	 * Reads all OrderLineItems from the database
	 */
	@Override
	public List<OrderLineItem> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM order_line_items");) {
			List<OrderLineItem> orderLineItems = new ArrayList<>();
			while (resultSet.next()) {
				orderLineItems.add(modelFromResultSet(resultSet));
			}
			return orderLineItems;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	
	/**
	 * Reads an OrderLineItem from the database using the id
	 */
	@Override
	public OrderLineItem read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM order_line_items WHERE id = ?");) {
			statement.setLong(1, id);
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
	
	/**
	 * Reads an OrderLineItem from the database using the order id
	 */
	public List<OrderLineItem> readByOrderId(Long orderId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM order_line_items WHERE order_id = ?");) {
			statement.setLong(1, orderId);
			try (ResultSet resultSet = statement.executeQuery();) {
				List<OrderLineItem> orderLineItems = new ArrayList<>();
				while (resultSet.next()) {
					orderLineItems.add(modelFromResultSet(resultSet));
				}
				return orderLineItems;
			}
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}
	
	/**
	 * Reads OrderLineItems from the database using the order and item ids
	 */
	public OrderLineItem readByOrderItem(Long orderId, Long itemId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM order_line_items WHERE order_id = ? AND item_id = ?");) {
			statement.setLong(1, orderId);
			statement.setLong(2, itemId);
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
	
	
	/**
	 * Reads the last created OrderLineItem from the database
	 */
	public OrderLineItem readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM order_line_items ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Creates an OrederLineItem in the database
	 */
	@Override
	public OrderLineItem create(OrderLineItem orderLineItem) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO order_line_items(item_id, quantity, order_id) VALUES (?,?,?)");) {
			statement.setLong(1, orderLineItem.getItem().getId());
			statement.setLong(2, orderLineItem.getQuantity());
			statement.setLong(3, orderLineItem.getOrderId());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	
	/**
	 * Updates an OrederLineItem in the database
	 */
	@Override
	public OrderLineItem update(OrderLineItem orderLineItem) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE order_line_items SET item_id = ?, quantity = ?, order_id = ? WHERE id = ?");) {
			statement.setLong(1, orderLineItem.getItem().getId());
			statement.setLong(2, orderLineItem.getQuantity());
			statement.setLong(3, orderLineItem.getOrderId());
			statement.setLong(4, orderLineItem.getId());
			statement.executeUpdate();
			return read(orderLineItem.getId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;

	}

	/**
	 * Deletes an OrderLineItem from the database
	 */
	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM order_line_items WHERE id = ?");) {
			statement.setLong(1, id);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

}
