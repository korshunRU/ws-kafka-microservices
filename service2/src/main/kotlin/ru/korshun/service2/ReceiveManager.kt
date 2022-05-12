package ru.korshun.service2

import org.springframework.stereotype.Service

@Service
class ReceiveManager(
  private val sendManager: SendManager
) {

  fun receiveFromMc1(message: Message) {
    sendManager.sendToMc3(message)
  }

}