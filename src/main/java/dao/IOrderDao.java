package dao;

import entity.Order;
import entity.User;
import java.util.List;

public interface IOrderDao {
    void saveOrder(Order order);
    Order findOrderById(int id);
    List<Order> findOrdersByUser(User user);
    List<Order> getAllOrders();
    void updateOrderStatus(int orderId, String status);
} 