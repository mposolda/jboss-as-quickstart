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

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Bean, which allows to collect error messages from HTTP filter and display them later when JSF is available
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
@SessionScoped
@Named("messagesChecker")
public class MessagesChecker implements Serializable {

    private Set<String> errorMessages = new HashSet<String>();

    public boolean isMessagesAvailable() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return processPendingErrorMessages(facesContext) || checkFacesMessages(facesContext);
    }

    private boolean processPendingErrorMessages(FacesContext facesContext) {
        if (errorMessages.size() > 0) {
            for (String errorMessage : errorMessages) {
                facesContext.addMessage(null, new FacesMessage(errorMessage));
            }

            errorMessages.clear();
            return true;
        }

        return false;
    }

    private boolean checkFacesMessages(FacesContext facesContext) {
        return facesContext.getMessages().hasNext();
    }

    public void addErrorMessage(String message) {
        errorMessages.add(message);
    }


}