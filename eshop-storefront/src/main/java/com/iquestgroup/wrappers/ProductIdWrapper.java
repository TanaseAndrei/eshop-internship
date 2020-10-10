package com.iquestgroup.wrappers;

/**
 * When sending a simple id in JSON format through POST request in the body of
 * the request, JAX-RS needs a way to convert that simple id to an object.
 */
public class ProductIdWrapper {
    private long productId;

    public void setProductId(long productId) { this.productId = productId; }

    public long getProductId() {
        return productId;
    }
}
