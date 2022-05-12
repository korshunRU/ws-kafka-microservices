package ru.korshun.service2

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter

open class WSClientSessionHandler(
  private val objectMapper: ObjectMapper
) : StompSessionHandlerAdapter() {

  @Autowired
  private lateinit var receiveManager: ReceiveManager

  @Value("\${ws.topic}")
  lateinit var wsTopic : String

  override fun handleFrame(
    headers: StompHeaders,
    payload: Any?) {
    receiveManager.receiveFromMc1(
      objectMapper.readValue(payload.toString(), Message::class.java))
  }

  override fun afterConnected(
    session: StompSession,
    connectedHeaders: StompHeaders) {
    session.subscribe(wsTopic, this)
  }
}