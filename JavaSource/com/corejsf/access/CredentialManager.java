package com.corejsf.access;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.corejsf.model.Credential;
import com.corejsf.model.Employee;

@Dependent
@Stateless
public class CredentialManager implements Serializable {
    private static final long serialVersionUID = 1L;
    @PersistenceContext(unitName="timesheet-jpa") EntityManager em;
    
    public Credential find(int id) {
        return em.find(Credential.class, id);
    }
    
    public Credential findByUserName(String name) {
        TypedQuery<Credential> query = em.createQuery("select c from Credential c where c.userName=:name",
                Credential.class);
        Credential cred = query.getSingleResult();
        return cred;
    }
    
    public void persist(Credential cred) {
        em.persist(cred);
    }
    
    public void merge(Credential cred) {
        em.merge(cred);
    }
    
    public void remove(Credential cred) {
        cred = find(cred.getId());
        em.remove(cred);
    }
    
    public Map<String, String> getCombo() {
        TypedQuery<Credential> query = em.createQuery("select c from Credential c",
                Credential.class);
        List<Credential> credList = query.getResultList();
        Map<String, String> credentials = new HashMap<String, String>();
        for (Credential cred : credList) {
            credentials.put(cred.getUserName(), cred.getPassword());
        }
        return credentials;
    }
}
