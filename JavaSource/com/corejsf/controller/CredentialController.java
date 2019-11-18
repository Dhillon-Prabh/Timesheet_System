package com.corejsf.controller;

import java.io.Serializable;

import javax.ejb.EJB;
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

    /**
     * holds the credentials of the current user
     */
    private Credential currentCred = new Credential();
    
    /**
     * default password to which the admin can reset the password of the user
     */
    private String defaultPassword = "default";

    @EJB
    private CredentialManager credManager;
    
    /**
     * default constructor
     */
    public CredentialController() { }
    
    /**
     * setter for the current credentails
     * @param c
     */
    public void setCurrentCred(Credential c) {
        currentCred = c;
    }
    
    /**
     * getter for the current credentials
     */
    public Credential getCurrentCred() {
        return currentCred;
    }
    
    /**
     * updates the password in the credentials database
     * @param curPassword
     * @param newPassword
     * @param confirmNewPassword
     */
    public void updatePassword(String curPassword, String newPassword, String confirmNewPassword) {
        if (currentCred.getPassword().equals(curPassword) && newPassword.equals(confirmNewPassword)) {
            currentCred.setPassword(newPassword);
            credManager.merge(currentCred);
            FacesMessage facesMessage = new FacesMessage("Successfully updated password", "Password updated");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }
    }
    
    /**
     * resets the password. Performed only by the admin
     * @param username
     */
    public void resetPassword(String username) {
        Credential cred = credManager.findByUserName(username);
        cred.setPassword(defaultPassword);
        credManager.merge(cred);
    }
    
    /**
     * validates if the entered current password is correct, before updating password.
     * @param context
     * @param component
     * @param value
     */
    public void validateCurPassword(FacesContext context, UIComponent component, Object value) {

        UIInput curPassword = (UIInput) component.findComponent("curPassword");
        
        String password = (String) curPassword.getSubmittedValue();

        if (!password.equals(currentCred.getPassword())) {
            FacesMessage message = com.corejsf.util.Messages.getMessage(
                    "com.corejsf.controller.messages", "checkPassword", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
    /**
     * validates the new password against the repeat password field.
     * @param context
     * @param component
     * @param value
     */
    public void validateNewPassword(FacesContext context, UIComponent component, Object value) {

        UIInput newPassword = (UIInput) component.findComponent("newPassword");
        UIInput confirmNewPassword = (UIInput) component.findComponent("confirmNewPassword");
        
        String password = (String) newPassword.getLocalValue();
        String password2 = (String) confirmNewPassword.getSubmittedValue();

        if (!password.equals(password2)) {
            FacesMessage message = com.corejsf.util.Messages.getMessage(
                    "com.corejsf.controller.messages", "checkPasswords", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
