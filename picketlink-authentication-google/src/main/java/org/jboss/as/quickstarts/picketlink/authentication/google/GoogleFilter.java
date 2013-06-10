/*
 * JBoss, Home of Professional Open Source
 *
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.picketlink.authentication.google;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.picketlink.Identity;
import org.picketlink.authentication.AuthenticationException;
import org.picketlink.social.standalone.google.GoogleConstants;

/**
 * Filter for Google+ Login
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
@ApplicationScoped
@WebFilter("/*")
public class GoogleFilter implements Filter {

    @Inject
    private Instance<Identity> identityInstance;

    @Inject
    private MessagesChecker messagesChecker;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        try{
            Identity identity = identityInstance.get();

            String login = request.getParameter("login");

            if (login != null) {
                initAuthentication(httpRequest);
            }

            String authState = getAuthState(httpRequest);

            if (login != null || authState != null) {
                ThreadLocalUtils.currentRequest.set(httpRequest);
                ThreadLocalUtils.currentResponse.set((HttpServletResponse) response);

                login(identity);
            }

            chain.doFilter(request,response);
        }finally{
            ThreadLocalUtils.currentRequest.set(null);
            ThreadLocalUtils.currentResponse.set(null);
        }
    }

    private String getAuthState(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            return (String)session.getAttribute(GoogleConstants.ATTRIBUTE_AUTH_STATE);
        } else {
            return null;
        }
    }

    // Cleanup state from session
    private void initAuthentication(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.removeAttribute(GoogleConstants.ATTRIBUTE_AUTH_STATE);
            session.removeAttribute(GoogleConstants.ATTRIBUTE_VERIFICATION_STATE);
        }
    }

    private void login(Identity identity) {
        try {
            identity.login();
        } catch (AuthenticationException ae) {
            String message = ae.getMessage();
            if (ae.getCause() != null) {
                message = message + ": " + ae.getCause().getMessage();
            }
            messagesChecker.addErrorMessage(message);

            ae.printStackTrace();
        }
    }
}
