package com.corejsf.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import com.corejsf.access.CredentialManager;
import com.corejsf.model.Credential;

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
    
    public void validateCurPassword(FacesContext context, UIComponent component, Object value) {

        UIInput curPassword = (UIInput) component.findComponent("curPassword");
        
        String password = (String) curPassword.getSubmittedValue();

        if (!password.equals(currentCred.getPassword())) {
            FacesMessage message = 
                    new FacesMessage("Credential validation failed.", 
                            "Password does not match current password");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
    public void validateNewPassword(FacesContext context, UIComponent component, Object value) {

        UIInput newPassword = (UIInput) component.findComponent("newPassword");
        UIInput confirmNewPassword = (UIInput) component.findComponent("confirmNewPassword");
        
        String password = (String) newPassword.getLocalValue();
        String password2 = (String) confirmNewPassword.getSubmittedValue();

        if (!password.equals(password2)) {
            FacesMessage message = 
                    new FacesMessage("Password match failed.", 
                            "New passwords do not match");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
