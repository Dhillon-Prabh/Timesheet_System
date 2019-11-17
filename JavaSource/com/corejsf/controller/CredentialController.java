package com.corejsf.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.corejsf.access.CredentialManager;
import com.corejsf.model.Credential;

@Named("credController")
@SessionScoped
public class CredentialController implements Serializable {
    private static final long serialVersionUID = 1L;

    private Credential currentCred;
    
    @EJB
    private CredentialManager credManager;
    
    public CredentialController() { }
    
    public void setCurrentCred(Credential c) {
        currentCred = c;
    }
    
    public Credential getCurrentCred() {
        return currentCred;
    }
    
    public void updatePassword(String curPassword, String newPassword, String confirmNewPassword) {
        if (currentCred.getPassword().equals(curPassword) && newPassword.equals(confirmNewPassword)) {
            currentCred.setPassword(newPassword);
            credManager.merge(currentCred);
        }
    }
}
