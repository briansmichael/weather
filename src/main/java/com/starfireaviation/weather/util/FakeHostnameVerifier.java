/*
 *  Copyright (C) 2022 Starfire Aviation, LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.starfireaviation.weather.util;

import javax.net.ssl.HostnameVerifier;

/**
 * FakeHostnameVerifier.
 */
public class FakeHostnameVerifier implements HostnameVerifier {

    /**
     * Always return true.
     *
     * @param hostname the host name.
     * @param session the SSL session used on the connection to host.
     * @return boolean indicating the host name is trusted.
     */
    @Override
    public boolean verify(final String hostname, final javax.net.ssl.SSLSession session) {
        return true;
    }
}
