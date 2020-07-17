/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladors;

import controladors.exceptions.NonexistentEntityException;
import entitat.Potenciador;
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
public class PotenciadorJpaController implements Serializable {

    public PotenciadorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("M06uf2ProjecteJPAversio01PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Potenciador potenciador) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(potenciador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Potenciador potenciador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            potenciador = em.merge(potenciador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = potenciador.getIdpotenciador();
                if (findPotenciador(id) == null) {
                    throw new NonexistentEntityException("The potenciador with id " + id + " no longer exists.");
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
            Potenciador potenciador;
            try {
                potenciador = em.getReference(Potenciador.class, id);
                potenciador.getIdpotenciador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The potenciador with id " + id + " no longer exists.", enfe);
            }
            em.remove(potenciador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Potenciador> findPotenciadorEntities() {
        return findPotenciadorEntities(true, -1, -1);
    }

    public List<Potenciador> findPotenciadorEntities(int maxResults, int firstResult) {
        return findPotenciadorEntities(false, maxResults, firstResult);
    }

    private List<Potenciador> findPotenciadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Potenciador.class));
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

    public Potenciador findPotenciador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Potenciador.class, id);
        } finally {
            em.close();
        }
    }

    public int getPotenciadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Potenciador> rt = cq.from(Potenciador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
