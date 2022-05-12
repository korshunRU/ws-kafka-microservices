package ru.korshun.service3

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Aspect
@Component
class S3Aspect {

  @Pointcut("execution(* ru.korshun.service3.ReceiveManager.*(Message))")
  fun receive() {}

  @Pointcut("execution(* ru.korshun.service3.SendManager.*(Message))")
  fun send() {}

  @Around("receive()")
  fun aroundFuncReceive(joinPoint: ProceedingJoinPoint): Any? {
    val data: Message = joinPoint.args[0] as Message
    logger.info { "Received from mc2, data = $data" }
    return joinPoint.proceed()
  }

  @Around("send()")
  fun aroundFuncSend(joinPoint: ProceedingJoinPoint) : Any? {
    val data : Message = joinPoint.args[0] as Message
    val result = joinPoint.proceed()
    logger.info { "Sent to mc1, data = $data" }
    return result
  }

}