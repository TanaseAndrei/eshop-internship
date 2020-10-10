package com.iquestgroup.filters;

import com.iquestgroup.constants.RoleRequired;
import com.iquestgroup.decoder.Base64Decoder;
import com.iquestgroup.exceptions.NotFoundException;
import com.iquestgroup.services.LogInServiceImpl;
import com.iquestgroup.interfaces.LogInService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A filter class that verify if the user is authorized to make the request. There are 4 types of authentication:
 * admin, seller, customer and none
 */
public class AuthenticationFilter implements Filter {
    private Map<String, RoleRequired> requestsForWhichLoginIsRequired;
    private Base64Decoder base64Decoder;

    @Override
    public void init(FilterConfig filterConfig) {
        base64Decoder = new Base64Decoder();
        requestsForWhichLoginIsRequired = new HashMap<>();
        requestsForWhichLoginIsRequired.put("order", RoleRequired.CUSTOMER);
        requestsForWhichLoginIsRequired.put("cart", RoleRequired.CUSTOMER);
        requestsForWhichLoginIsRequired.put("favorites", RoleRequired.CUSTOMER);
        requestsForWhichLoginIsRequired.put("seller", RoleRequired.SELLER);
        requestsForWhichLoginIsRequired.put("shop/bindshop", RoleRequired.ADMIN);
    }

    @Override
    public void destroy() {

    }

    /**
     * This is the body of the filter, where the logic happens
     *
     * @param servletRequest  is the request from user
     * @param servletResponse is the response for user
     * @param filterChain     is the chain of filters to be executed before the request arrive in controller
     * @throws IOException      signals that a I/O exception has occurred
     * @throws ServletException is a general servlet exception that can occur
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

            String url = String.valueOf(httpServletRequest.getRequestURL());
            try {
                LogInService logInService = new LogInServiceImpl();
                RoleRequired roleRequired = getRoleRequired(url);
                if (roleRequired.name().equals(RoleRequired.NONE.name())) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    if (logInService.areCredentialsValid(getUsernameDecodedFromUrl(httpServletRequest),
                            getPasswordDecodedFromUrl(httpServletRequest), roleRequired)) {
                        filterChain.doFilter(servletRequest, servletResponse);
                    } else {
                        sendUnauthorizedResponse(servletResponse);
                    }
                }
            } catch (NotFoundException e) {
                e.getMessage();
            }
        }
    }

    /**
     * Return an unauthorized response for the user requesr
     *
     * @param servletResponse is the request of the user
     */
    private void sendUnauthorizedResponse(ServletResponse servletResponse) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    /**
     * @param url of the request
     * @return the role required for the user to have for the request
     */
    private RoleRequired getRoleRequired(String url) {
        for (Map.Entry<String, RoleRequired> entry : requestsForWhichLoginIsRequired.entrySet()) {
            if (url.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return RoleRequired.NONE;
    }

    private String getUsernameDecodedFromUrl(HttpServletRequest httpServletRequest) {
        return base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
    }

    private String getPasswordDecodedFromUrl(HttpServletRequest httpServletRequest) {
        return base64Decoder.getPasswordDecodedFromUrl(httpServletRequest);
    }
}
