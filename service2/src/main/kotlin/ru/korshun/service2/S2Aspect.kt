package ru.korshun.service2

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Aspect
@Component
class S2Aspect {

  @Pointcut("execution(* ru.korshun.service2.ReceiveManager.receiveFromMc1(Message))")
  fun receive() {}

  @Pointcut("execution(* ru.korshun.service2.SendManager.sendToMc3(Message))")
  fun send() {}

  @Pointcut("execution(* ru.korshun.service2.WSClientSessionHandler.afterConnected(..))")
  fun connect() {}

  @Around("receive()")
  fun aroundFuncReceive(joinPoint: ProceedingJoinPoint): Any? {
    val data: Message = joinPoint.args[0] as Message
    logger.info { "Received from mc1, data = $data" }
    return joinPoint.proceed()
  }

  @Around("send()")
  fun aroundFuncSend(joinPoint: ProceedingJoinPoint) : Any? {
    val data : Message = joinPoint.args[0] as Message
    val result = joinPoint.proceed()
    logger.info { "Sent to mc3, data = $data" }
    return result
  }

  @After("connect()")
  fun afterConnect() {
    logger.info { "Connected to service1's websocket" }
  }

}