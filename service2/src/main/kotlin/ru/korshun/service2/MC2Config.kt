package ru.korshun.service2

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.messaging.converter.StringMessageConverter
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.WebSocketTransport

@Configuration
class MC2Config {

  @Value("\${ws.server}")
  lateinit var wsServer : String

  @Value("\${spring.kafka.producer.bootstrap-servers}")
  lateinit var kafkaServer : String

  @Bean
  @LoadBalanced
  fun webSocketStompClient(
    webSocketClient: WebSocketClient,
    stompSessionHandler: StompSessionHandler
  ): WebSocketStompClient {
    return WebSocketStompClient(webSocketClient).apply {
      messageConverter = StringMessageConverter()
      connect(wsServer, stompSessionHandler)
    }
  }

  @Bean
  fun webSocketClient(): WebSocketClient {
    val list = listOf(
      WebSocketTransport(StandardWebSocketClient()),
      RestTemplateXhrTransport()
    )
    return SockJsClient(list)
  }

  @Bean
  fun stompSessionHandler(): StompSessionHandler {
    return WSClientSessionHandler(getObjectMapper())
  }

  @Bean
  fun getObjectMapper() : ObjectMapper {
    return jacksonObjectMapper()
  }

  @Bean
  fun consumerConfigs(): Map<String, Any> {
    return mapOf<String, Any>(
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
      JsonSerializer.TYPE_MAPPINGS to "message:ru.korshun.service2.Message",
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaServer,
      ProducerConfig.CLIENT_ID_CONFIG to "kafkaProducerId"
    )
  }

  @Bean
  fun kafkaTemplate() : KafkaTemplate<String, Message> {
    return KafkaTemplate<String, Message>(
      DefaultKafkaProducerFactory(consumerConfigs())
    )
  }

}