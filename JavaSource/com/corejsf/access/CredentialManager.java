package com.corejsf.access;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.corejsf.model.Credential;

/**
 * Credential Manager handles the database calls 
 * @author psing
 *
 */
@Dependent
@Stateless
public class CredentialManager implements Serializable {
    private static final long serialVersionUID = 1L;
    @PersistenceContext(unitName="timesheet-jpa") EntityManager em;
    
    /**
     * finds the credential with this id
     * @param id
     * @return
     */
    public Credential find(int id) {
        return em.find(Credential.class, id);
    }
    
    /**
     * finds the credentials with this user name
     * @param name
     * @return
     */
    public Credential findByUserName(String name) {
        TypedQuery<Credential> query = em.createQuery("select c from Credential c where c.userName=:name",
                Credential.class);
        query.setParameter("name", name);
        Credential cred = null;
        try {
            cred = query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
        return cred;
    }
    
    /**
     * adds to the credentials table
     * @param cred
     */
    public void persist(Credential cred) {
        em.persist(cred);
    }
    
    /**
     * updates the credentials table
     * @param cred
     */
    public void merge(Credential cred) {
        em.merge(cred);
    }
    
    /**
     * removes from the credentials table
     * @param cred
     */
    public void remove(Credential cred) {
        cred = find(cred.getId());
        em.remove(cred);
    }
    
    /**
     * returns a map with the combos of the username and password
     * @return
     */
    public Map<String, String> getCombo() {
        TypedQuery<Credential> query = em.createQuery("select c from Credential c",
                Credential.class);
        List<Credential> credList = query.getResultList();
        Map<String, String> credentials = new HashMap<String, String>();
        for (Credential cred : credList) {
            credentials.put(cred.getEmp().getUserName(), cred.getPassword());
        }
        return credentials;
    }
}
