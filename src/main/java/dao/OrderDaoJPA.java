package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.Order;
import entity.User;

public class OrderDaoJPA implements IOrderDao {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    public OrderDaoJPA() {
        try {
            emf = Persistence.createEntityManagerFactory("gestionproduits");
            em = emf.createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error initializing database connection: " + e.getMessage());
        }
    }

    @Override
    public void saveOrder(Order order) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(order);
        et.commit();
    }

    @Override
    public Order findOrderById(int id) {
        return em.find(Order.class, id);
    }

    @Override
    public List<Order> findOrdersByUser(User user) {
        Query q = em.createQuery("SELECT o FROM Order o WHERE o.user.id = :userId");
        q.setParameter("userId", user.getId());
        return q.getResultList();
    }

    @Override
    public List<Order> getAllOrders() {
        Query q = em.createQuery("SELECT o FROM Order o ORDER BY o.orderDate DESC");
        return q.getResultList();
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        Order order = findOrderById(orderId);
        if (order != null) {
            order.setStatus(status);
            em.merge(order);
        }
        et.commit();
    }
} 