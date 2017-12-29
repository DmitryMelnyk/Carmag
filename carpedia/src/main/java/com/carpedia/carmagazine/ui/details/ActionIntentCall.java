package com.carpedia.carmagazine.ui.details;

/**
 * Created by d264 on 12/27/17.
 */

public class ActionIntentCall implements ActionIntent {

    private final String phoneNumber;

    public ActionIntentCall(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
