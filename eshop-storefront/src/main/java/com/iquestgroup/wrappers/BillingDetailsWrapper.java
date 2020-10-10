package com.iquestgroup.wrappers;

/**
 * Class used to wrap up the billing details.
 * E.g.:
 *{
 *  "billingDetails": "Person X, Str. Florilor Nr. 25, Craiova, Dolj"
 *}
 */
public class BillingDetailsWrapper {
    private String billingDetails;

    public void setBillingDetails(String billingDetails) {
        this.billingDetails = billingDetails;
    }

    public String getBillingDetails() {
        return billingDetails;
    }
}
