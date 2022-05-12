package ru.korshun.service2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableEurekaClient
@EnableAspectJAutoProxy
class Service2Application

fun main(args: Array<String>) {
  runApplication<Service2Application>(*args)
}
