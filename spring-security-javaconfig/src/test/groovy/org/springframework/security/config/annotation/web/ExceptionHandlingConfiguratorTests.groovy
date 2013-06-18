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
package org.springframework.security.config.annotation.web

import org.springframework.security.config.annotation.BaseSpringSpec
import org.springframework.security.config.annotation.LifecycleManager;
import org.springframework.security.config.annotation.authentication.AuthenticationManagerBuilder
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.channel.ChannelDecisionManagerImpl
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.access.channel.InsecureChannelProcessor
import org.springframework.security.web.access.channel.SecureChannelProcessor

/**
 *
 * @author Rob Winch
 */
class ExceptionHandlingConfiguratorTests extends BaseSpringSpec {

    def "exception LifecycleManager"() {
        setup: "initialize the AUTH_FILTER as a mock"
            LifecycleManager lifecycleManager = Mock()
        when:
            HttpConfiguration http = new HttpConfiguration(lifecycleManager, authenticationBldr)
            http
                .exceptionHandling()
                    .and()
                .build()

        then: "ExceptionTranslationFilter is registered with LifecycleManager"
            1 * lifecycleManager.registerLifecycle(_ as ExceptionTranslationFilter) >> {ExceptionTranslationFilter o -> o}
    }
}