package ru.korshun.service2

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class SendManager(
  private val kafkaTemplate: KafkaTemplate<String, Message>,
  @Value("\${spring.kafka.topic}") val kafkaTopic : String
  ) {

  fun sendToMc3(message: Message) {
    message.mc2Timestamp = System.currentTimeMillis()
    kafkaTemplate.send(kafkaTopic, message)
  }
}