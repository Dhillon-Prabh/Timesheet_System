package com.corejsf.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.corejsf.model.Credential;

@Named("credController")
@SessionScoped
public class CredentialController implements Serializable {
    private static final long serialVersionUID = 1L;

    private Credential currentCred;
    
    public CredentialController() { }
    
    public void setCurrentCred(Credential c) {
        currentCred = c;
    }
    
    public Credential getCurrentCred() {
        return currentCred;
    }
}
