/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladors;

import controladors.exceptions.NonexistentEntityException;
import entitat.Nau;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author arnau
 */
public class NauJpaController implements Serializable {

    public NauJpaController() {
      this.emf = Persistence.createEntityManagerFactory("M06uf2ProjecteJPAversio01PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Nau nau) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nau);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Nau nau) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nau = em.merge(nau);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nau.getIdnau();
                if (findNau(id) == null) {
                    throw new NonexistentEntityException("The nau with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nau nau;
            try {
                nau = em.getReference(Nau.class, id);
                nau.getIdnau();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nau with id " + id + " no longer exists.", enfe);
            }
            em.remove(nau);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Nau> findNauEntities() {
        return findNauEntities(true, -1, -1);
    }

    public List<Nau> findNauEntities(int maxResults, int firstResult) {
        return findNauEntities(false, maxResults, firstResult);
    }

    private List<Nau> findNauEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Nau.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Nau findNau(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Nau.class, id);
        } finally {
            em.close();
        }
    }

    public int getNauCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Nau> rt = cq.from(Nau.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
