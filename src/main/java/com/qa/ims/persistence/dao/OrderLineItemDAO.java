package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderLineItem;
import com.qa.ims.utils.DBUtils;

public class OrderLineItemDAO implements Dao<OrderLineItem> {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public OrderLineItem modelFromResultSet(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderLineItem> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderLineItem read(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
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

	@Override
	public OrderLineItem update(OrderLineItem t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
