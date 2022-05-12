package ru.korshun.service1

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Aspect
@Component
class S1Aspect {

  @Pointcut("execution(* ru.korshun.service1.EndPointsV1Service.receiveFromMc3(Message))")
  fun receive() {}

  @Pointcut("execution(* ru.korshun.service1.EndPointsV1Service.startTask(..))")
  fun start() {}

  @Pointcut("execution(* ru.korshun.service1.EndPointsV1Service.stopTask(..))")
  fun stop() {}

  @Around("receive()")
  fun aroundFuncReceive(joinPoint: ProceedingJoinPoint): Any? {
    val data: Message = joinPoint.args[0] as Message
    val result = joinPoint.proceed()
    logger.info { "Received from mc3 and saved to DB, data = $data" }
    return result
  }

  @AfterReturning("start()", returning="retVal")
  fun afterStart(retVal : Boolean) {
    logger.info {
      if (retVal) "Task is successfully started"
      else "Trying to start already started task"
    }
  }

  @AfterReturning("stop()", returning="retVal")
  fun afterStop(retVal : Boolean) {
    logger.info {
      if (retVal) "Task is successfully stopped"
      else "Trying to stop already stopped task"
    }
  }

}