package ru.korshun.service1

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class EndpointsV1Controller(
  val endPointsV1Service: EndPointsV1Service
) {

  @GetMapping(
    path = ["/start"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
  )
  @ResponseStatus(HttpStatus.OK)
  fun start() : ResponseEntity<String> {
    val isStarted = endPointsV1Service.startTask()
    return ResponseEntity.status(HttpStatus.OK).body(
      if (isStarted) "Task is successfully started"
      else "Task is already started")
  }

  @GetMapping(
    path = ["/stop"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
  )
  @ResponseStatus(HttpStatus.OK)
  fun stop() : ResponseEntity<String> {
    val isStopped = endPointsV1Service.stopTask()
    return ResponseEntity.status(HttpStatus.OK).body(
      if (isStopped) "Task is successfully stopped"
      else "Task isn't started")
  }

  @PostMapping(
    path = ["/fromMC3"]
  )
  @ResponseStatus(HttpStatus.CREATED)
  fun fromMc3(@RequestBody message: Message) : ResponseEntity<String> {
    endPointsV1Service.receiveFromMc3(message)
    return ResponseEntity.status(HttpStatus.CREATED).body("Record successfully created")
  }

}