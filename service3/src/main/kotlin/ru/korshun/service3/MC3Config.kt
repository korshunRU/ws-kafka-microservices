package ru.korshun.service3

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.web.client.RestTemplate


@Configuration
class MC3Config {

  @Value("\${spring.kafka.consumer.bootstrap-servers}")
  lateinit var kafkaServer : String

  @Bean
  fun consumerConfigs(): Map<String, Any> {
    return mapOf<String, Any>(
      ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to true,
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaServer,
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
      JsonDeserializer.TYPE_MAPPINGS to "message:ru.korshun.service3.Message",
      ConsumerConfig.CLIENT_ID_CONFIG to "kafkaConsumerId"
    )
  }

  @Bean
  fun batchFactory(): KafkaListenerContainerFactory<*> {
    return ConcurrentKafkaListenerContainerFactory<String, Message>().apply {
      consumerFactory = consumerFactory()
      isBatchListener = true
      setMessageConverter(BatchMessagingMessageConverter(converter()))
    }
  }

  @Bean
  fun consumerFactory(): ConsumerFactory<String, Message> {
    return DefaultKafkaConsumerFactory(
      consumerConfigs(),
      StringDeserializer(),
      JsonDeserializer(Message::class.java)
    )
  }

  @Bean
  fun converter(): StringJsonMessageConverter {
    return StringJsonMessageConverter()
  }

  @Bean
  @LoadBalanced
  fun restTemplate(): RestTemplate {
    return RestTemplateBuilder().build()
  }

  @Bean
  fun getObjectMapper(): ObjectMapper {
    return jacksonObjectMapper()
  }

}