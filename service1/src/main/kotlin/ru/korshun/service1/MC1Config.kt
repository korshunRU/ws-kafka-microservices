package ru.korshun.service1

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer


@Configuration
@EnableWebSocketMessageBroker
class MC1Config : WebSocketMessageBrokerConfigurer {

  override fun registerStompEndpoints(registry: StompEndpointRegistry) {
    registry.apply {
      addEndpoint("/websocket-service1")
      addEndpoint("/websocket-service1/ws").setAllowedOriginPatterns("*")
        .withSockJS()
    }
  }

  override fun configureMessageBroker(registry: MessageBrokerRegistry) {
    registry.apply {
      enableSimpleBroker("/topic")
      setApplicationDestinationPrefixes("/app")
      setPreservePublishOrder(true)
    }
  }

  @Bean
  fun getObjectMapper() : ObjectMapper {
    return jacksonObjectMapper()
  }

  @Bean
  @Primary
  fun threadPoolTaskScheduler(): TaskScheduler {
    return ThreadPoolTaskScheduler()
  }

}