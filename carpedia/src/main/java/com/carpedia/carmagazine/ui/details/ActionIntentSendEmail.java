package com.carpedia.carmagazine.ui.details;

/**
 * Created by d264 on 12/27/17.
 */

public class ActionIntentSendEmail implements ActionIntent {

    private final String email;

    public ActionIntentSendEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
