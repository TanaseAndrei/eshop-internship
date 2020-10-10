package com.iquestgroup.decoder;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.StringTokenizer;

/**
 * Decoder class used for extracting encoded username and password in header
 */
public class Base64Decoder {
    private static final String AUTHORIZATION = "authorization";

    /**
     * @param httpServletRequest is the request from user
     * @return the username decoded of the user making the request
     */
    public String getUsernameDecodedFromUrl(HttpServletRequest httpServletRequest) {
        String authenticationCredentials = httpServletRequest.getHeader(AUTHORIZATION);

        final StringTokenizer tokenizer = new StringTokenizer(decodeString(authenticationCredentials), ":");
        return tokenizer.nextToken();
    }

    /**
     * @param httpServletRequest is the request from user
     * @return the password decoded of the user making the request
     */
    public String getPasswordDecodedFromUrl(HttpServletRequest httpServletRequest) {
        String authenticationCredentials = httpServletRequest.getHeader(AUTHORIZATION);

        final StringTokenizer tokenizer = new StringTokenizer(decodeString(authenticationCredentials), ":");
        tokenizer.nextToken();
        return tokenizer.nextToken();
    }

    private String decodeString(String encodedString) {
        String usernameAndPassword;
        byte[] decodedBytes = Base64.getDecoder().decode(
                encodedString);
        usernameAndPassword = new String(decodedBytes, StandardCharsets.UTF_8);
        return usernameAndPassword;
    }
}
