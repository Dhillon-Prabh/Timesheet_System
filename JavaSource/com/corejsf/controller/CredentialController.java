package com.corejsf.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.corejsf.access.CredentialManager;
import com.corejsf.model.Credential;
import com.corejsf.model.Employee;

@Named("credController")
@SessionScoped
public class CredentialController implements Serializable {
    private static final long serialVersionUID = 1L;

    private Credential currentCred = new Credential();
    private String defaultPassword = "default";

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
    
    public void resetPassword(String username) {
        Credential cred = credManager.findByUserName(username);
        cred.setPassword(defaultPassword);
        credManager.merge(cred);
    }
}
