package ru.korshun.service3

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service


@Service
class ReceiveManager(val sendManager: SendManager) {

  @KafkaListener(
    topics = ["\${spring.kafka.topic}"],
    groupId = "\${spring.kafka.group-id}"
  )
  fun consume(message: Message) {
    message.mc3Timestamp = System.currentTimeMillis()
    sendManager.sendToMc1(message)
  }

}