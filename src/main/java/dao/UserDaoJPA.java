package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.User;

public class UserDaoJPA implements IUserDao {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    public UserDaoJPA() {
        try {
            emf = Persistence.createEntityManagerFactory("gestionproduits");
            em = emf.createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error initializing database connection: " + e.getMessage());
        }
    }

    @Override
    public void addUser(User user) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(user);
        et.commit();
    }

    @Override
    public User findUserById(int id) {
        return em.find(User.class, id);
    }

    @Override
    public User findUserByUsername(String username) {
        try {
            Query q = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
            q.setParameter("username", username);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findUserByEmail(String email) {
        try {
            Query q = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
            q.setParameter("email", email);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        Query q = em.createQuery("SELECT u FROM User u");
        return q.getResultList();
    }

    @Override
    public void updateUser(User user) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(user);
        et.commit();
    }

    @Override
    public void deleteUser(int id) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        User user = findUserById(id);
        if (user != null) {
            em.remove(user);
        }
        et.commit();
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = findUserByUsername(username);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }
} 