package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.Produit;

public class GestionProduitJPA implements IGestionProduit {

	private EntityManagerFactory emf;
	private EntityManager em;
	
	public GestionProduitJPA() {
		try {
			emf = Persistence.createEntityManagerFactory("gestionproduits");
			em = emf.createEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error initializing database connection: " + e.getMessage());
		}
	}
	
	@Override
	public void addProduct(Produit p) {
		// TODO Auto-generated method stub
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(p);
		et.commit();
		
	}

	@Override
	public List<Produit> getAllProducts() {
		// TODO Auto-generated method stub
		Query q = em.createQuery("select p from Produit p");
		return q.getResultList();
	}

	@Override
	public List<Produit> getProductsByMc(String mc) {
		// TODO Auto-generated method stub
		Query q = em.createQuery("select p from Produit p where p.nom like :x");
		q.setParameter("x", "%"+mc+"%");
		return q.getResultList();
	}

	@Override
	public Produit getProduct(int id) {
		// TODO Auto-generated method stub
		return em.find(Produit.class, id);
	}

	@Override
	public void deleteProduct(int id) {
		// TODO Auto-generated method stub
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.remove(getProduct(id));
		et.commit();
	}

	@Override
	public void updateProduct(Produit p) {
		// TODO Auto-generated method stub
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.merge(p);
		et.commit();
	}
	
}