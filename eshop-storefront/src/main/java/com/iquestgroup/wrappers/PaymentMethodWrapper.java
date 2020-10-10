package com.iquestgroup.wrappers;

import com.iquestgroup.constants.PaymentMethod;

/**
 * Class used to wrap up the payment method.
 * E.g.:
 *{
 *  "paymentMethod": "CARD"
 *}
 */
public class PaymentMethodWrapper {
    private PaymentMethod paymentMethod;

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
