package com.stop.api.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EntityScan({"com.stop.model"})
@EnableJpaRepositories("com.stop.repository")
@EnableScheduling
@EnableTransactionManagement
@EnableWebSocketMessageBroker
public class StopBackendConfig implements WebSocketMessageBrokerConfigurer {

  private static final int CONNECTION_TIMEOUT = 5_000;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/secured/user/queue/specific-user");
    config.setApplicationDestinationPrefixes("/stop");
    config.setUserDestinationPrefix("/secured/user");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/stop/secured").setAllowedOrigins("*").withSockJS();
  }

  /**
   * Initialize rest template for client calls.
   * 
   * @return rest template
   */
  @Bean
  public RestTemplate getRestTemplate() {
    final HttpComponentsClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory();
    rf.setConnectTimeout(CONNECTION_TIMEOUT);
    rf.setReadTimeout(0);
    return new RestTemplate(rf);
  }

}
