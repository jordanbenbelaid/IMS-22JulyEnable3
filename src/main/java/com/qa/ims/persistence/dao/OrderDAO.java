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

public class OrderDAO implements Dao<Order> {
	
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Order modelFromResultSet(ResultSet resultSet) {
		try {
			CustomerDAO custDAO = new CustomerDAO();
			Long id = resultSet.getLong("id");
			String orderNumber = resultSet.getString("order_number");
			Long customerId = resultSet.getLong("customer_id");
			Customer customer = custDAO.read(customerId);
			return new Order(id, orderNumber, customer);
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.info("Order does not exist");
			return null;
		}
		
	}
	
	@Override
	public List<Order> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");) {
			Order order;
			List<OrderLineItem> lineItems;
			OrderLineItemDAO lineItemDAO = new OrderLineItemDAO();
			List<Order> orders = new ArrayList<>();
			while (resultSet.next()) {
				order = modelFromResultSet(resultSet);
				lineItems = lineItemDAO.readByOrderId(order.getId());
				if(lineItems.size() > 0) {
					for(OrderLineItem lineItem : lineItems) {
						order.addOrderLineItem(lineItem);
					}
				}

				orders.add(order);
			}
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	@Override
	public Order read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE id = ?");) {
			statement.setLong(1, id);
			OrderLineItemDAO lineItemDAO = new OrderLineItemDAO();
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				Order order = modelFromResultSet(resultSet);
				if (order != null) {
					List<OrderLineItem> lineItems = lineItemDAO.readByOrderId(order.getId());
					if(lineItems.size() > 0) {
						for(OrderLineItem lineItem : lineItems) {
							order.addOrderLineItem(lineItem);
						}
					}
				}
				return order;
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	public Order readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order create(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO orders(order_number, customer_id) VALUES (?,?)");) {
			statement.setString(1, order.getOrderNumber());
			statement.setLong(2, order.getCustomer().getId());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order update(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE orders SET order_number = ?, customer_id = ?, order_total = ? WHERE id = ?");) {
			statement.setString(1, order.getOrderNumber());
			statement.setLong(2, order.getCustomer().getId());
			statement.setDouble(3, order.getOrderTotal());
			statement.setLong(4, order.getId());
			statement.executeUpdate();
			return read(order.getId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE id = ?");) {
			statement.setLong(1, id);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

}
