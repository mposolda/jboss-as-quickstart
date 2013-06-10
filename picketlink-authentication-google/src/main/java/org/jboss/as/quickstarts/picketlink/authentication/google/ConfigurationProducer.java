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

import javax.enterprise.inject.Produces;

import org.picketlink.social.auth.conf.GoogleConfiguration;

/**
 * Class to obtain the configuration
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class ConfigurationProducer {

    @Produces
    public GoogleConfiguration configure(){
        final String clientID  = System.getProperty("GOOGLE_CLIENT_ID");
        final String clientSecret = System.getProperty("GOOGLE_CLIENT_SECRET");
        final String returnURL = System.getProperty("GOOGLE_RETURN_URL");

        final String accessType = System.getProperty("GOOGLE_ACCESS_TYPE");
        final String appName = System.getProperty("GOOGLE_APP_NAME");
        final String secureRandomAlgorithmName = System.getProperty("SECURE_RANDOM_ALG_NAME");

        final String scope = System.getProperty("GOOGLE_SCOPE");

        GoogleConfiguration googleConfiguration = new GoogleConfiguration() {
            @Override
            public String getClientID() {
                return clientID;
            }

            @Override
            public String getClientSecret() {
                return clientSecret;
            }

            @Override
            public String getScope() {
                return scope;
            }

            @Override
            public String getReturnURL() {
                return returnURL;
            }

            @Override
            public String getAccessType() {
                return accessType;
            }

            @Override
            public String getApplicationName() {
                return appName;
            }

            @Override
            public String getRandomAlgorithm() {
                return secureRandomAlgorithmName;
            }
        };
        return googleConfiguration;
    }
}
