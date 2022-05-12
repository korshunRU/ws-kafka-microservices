package ru.korshun.service1

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableEurekaClient
@EnableAspectJAutoProxy
class Service1Application

fun main(args: Array<String>) {
  runApplication<Service1Application>(*args)
}
