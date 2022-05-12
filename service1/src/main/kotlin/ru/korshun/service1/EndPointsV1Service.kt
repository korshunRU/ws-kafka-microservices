package ru.korshun.service1

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.core.MessageSendingOperations
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Service
import ru.korshun.service1.entity.SessionEntity
import ru.korshun.service1.mapper.MessageMapper
import ru.korshun.service1.repo.SessionRepo
import java.util.concurrent.ScheduledFuture

@Service
class EndPointsV1Service(
  private val sessionRepo: SessionRepo,
  private val mapper: MessageMapper,
  private val sendOperation : MessageSendingOperations<String>,
  private val objectMapper: ObjectMapper,
  private val taskScheduler: TaskScheduler
) {

  @Value("\${ws.topic}")
  lateinit var topic: String

  @Value("\${timer.period}")
  var period: Long = 0

  var scheduleFuture: ScheduledFuture<*>? = null

  fun startTask(): Boolean {
    if (scheduleFuture == null) {
      scheduleFuture = taskScheduler.scheduleAtFixedRate(task(), period)
      return true
    }
    return false
  }

  fun receiveFromMc3(message: Message) {
    message.endTimestamp = System.currentTimeMillis()
    val entity = mapper.toEntity(message)
    sessionRepo.save(entity)
  }

  fun stopTask(): Boolean {
    if (scheduleFuture != null) {
      scheduleFuture!!.cancel(false)
      return true
    }
    return false
  }

  fun task(): Runnable {
    return Runnable {
      val lastRecord = sessionRepo.findFirstByOrderByIdDesc()
        .orElse(SessionEntity(0, 0, 0, 0, 0, 0))
      val msg = Message(
        ++lastRecord.id,
        ++lastRecord.sessionId,
        System.currentTimeMillis()
      )
      sendOperation.convertAndSend(topic, objectMapper.writeValueAsString(msg))
    }
  }

}