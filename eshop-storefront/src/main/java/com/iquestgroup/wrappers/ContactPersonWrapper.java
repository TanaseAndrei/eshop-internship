package com.iquestgroup.wrappers;

/**
 * Class used to wrap up the contact person details.
 * E.g. in POST's body:
 *{
 *  "contactPerson": "Person X, 0721xxxxxx"
 *}
 */
public class ContactPersonWrapper {
    private String contactPerson;

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }
}
