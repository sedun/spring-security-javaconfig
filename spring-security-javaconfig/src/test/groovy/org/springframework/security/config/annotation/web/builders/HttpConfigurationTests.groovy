/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.config.annotation.web.builders

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.beans.factory.BeanCreationException
import org.springframework.context.annotation.Configuration
import org.springframework.security.cas.web.CasAuthenticationFilter
import org.springframework.security.config.annotation.BaseSpringSpec
import org.springframework.security.config.annotation.web.builders.HttpConfiguration
import org.springframework.security.config.annotation.web.configuration.BaseWebConfig
import org.springframework.web.filter.OncePerRequestFilter

/**
 * HttpConfiguration tests
 *
 * @author Rob Winch
 *
 */
public class HttpConfigurationTests extends BaseSpringSpec {
    def "addFilter with unregistered Filter"() {
        when:
            loadConfig(UnregisteredFilterConfig)
        then:
            BeanCreationException success = thrown()
            success.message.contains "The Filter class ${UnregisteredFilter.name} does not have a registered order and cannot be added without a specified order. Consider using addFilterBefore or addFilterAfter instead."
    }

    @Configuration
    static class UnregisteredFilterConfig extends BaseWebConfig {
        protected void configure(HttpConfiguration http) throws Exception {
            http
                .addFilter(new UnregisteredFilter())
        }
    }

    static class UnregisteredFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request,
                HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            filterChain.doFilter(request, response);
        }
    }

    // https://github.com/SpringSource/spring-security-javaconfig/issues/104
    def "#104 addFilter CasAuthenticationFilter"() {
        when:
            loadConfig(CasAuthenticationFilterConfig)
        then:
            findFilter(CasAuthenticationFilter)
    }

    @Configuration
    static class CasAuthenticationFilterConfig extends BaseWebConfig {
        protected void configure(HttpConfiguration http) throws Exception {
            http
                .addFilter(new CasAuthenticationFilter())
        }
    }
}