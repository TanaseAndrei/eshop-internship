package com.iquestgroup.wrappers;

import com.iquestgroup.constants.ShippingMethod;

/**
 * Class used to wrap up the shipping method.
 * E.g.:
 *{
 *  "shippingMethod": "COURIER"
 *}
 */
public class ShippingMethodWrapper {
    private ShippingMethod shippingMethod;

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }
}
