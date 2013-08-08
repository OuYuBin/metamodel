/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.metamodel.salesforce;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import junit.framework.TestCase;

/**
 * Abstract test case which consults an external .properties file for SFDC
 * credentials to execute the tests.
 */
public abstract class SalesforceTestCase extends TestCase {

    private String _username;
    private String _password;
    private String _securityToken;
    private boolean _configured;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Properties properties = new Properties();
        File file = new File(getPropertyFilePath());
        _configured = file.exists();
        if (_configured) {
            properties.load(new FileReader(file));
            _username = properties.getProperty("username");
            _password = properties.getProperty("password");
            _securityToken = properties.getProperty("securityToken");
        }
    }

    private String getPropertyFilePath() {
        String userHome = System.getProperty("user.home");
        return userHome + "/sfdc-credentials.properties";
    }

    protected String getInvalidConfigurationMessage() {
        return "!!! WARN !!! Salesforce ignored\r\n" + "Please configure salesforce credentials locally ("
                + getPropertyFilePath() + "), to run integration tests";
    }

    public boolean isConfigured() {
        return _configured;
    }

    public String getUsername() {
        return _username;
    }

    public String getPassword() {
        return _password;
    }

    public String getSecurityToken() {
        return _securityToken;
    }
}