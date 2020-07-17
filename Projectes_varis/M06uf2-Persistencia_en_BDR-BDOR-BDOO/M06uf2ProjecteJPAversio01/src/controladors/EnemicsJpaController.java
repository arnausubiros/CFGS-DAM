/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladors;

import controladors.exceptions.NonexistentEntityException;
import entitat.Enemics;
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
public class EnemicsJpaController implements Serializable {

    public EnemicsJpaController() {
         this.emf = Persistence.createEntityManagerFactory("M06uf2ProjecteJPAversio01PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Enemics enemics) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(enemics);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Enemics enemics) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            enemics = em.merge(enemics);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = enemics.getIdenemics();
                if (findEnemics(id) == null) {
                    throw new NonexistentEntityException("The enemics with id " + id + " no longer exists.");
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
            Enemics enemics;
            try {
                enemics = em.getReference(Enemics.class, id);
                enemics.getIdenemics();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The enemics with id " + id + " no longer exists.", enfe);
            }
            em.remove(enemics);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Enemics> findEnemicsEntities() {
        return findEnemicsEntities(true, -1, -1);
    }

    public List<Enemics> findEnemicsEntities(int maxResults, int firstResult) {
        return findEnemicsEntities(false, maxResults, firstResult);
    }

    private List<Enemics> findEnemicsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Enemics.class));
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

    public Enemics findEnemics(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Enemics.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnemicsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Enemics> rt = cq.from(Enemics.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
