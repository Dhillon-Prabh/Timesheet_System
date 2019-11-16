package com.corejsf.controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.corejsf.model.Credential;

@Named("credController")
@SessionScoped
public class CredentialController {
    private Credential currentCred;
    
    public CredentialController() { }
    
    public void setCurrentCred(Credential c) {
        currentCred = c;
    }
    
    public Credential getCurrentCred() {
        return currentCred;
    }
}
