package ru.korshun.service3

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class SendManager(
  private val restTemplate: RestTemplate,
  private val objectMapper: ObjectMapper,
  @Value("\${mc1.url}") private val mc1Url : String
) {

  fun sendToMc1(message: Message): ResponseEntity<String>  {
    val headers = HttpHeaders().apply {
      contentType = MediaType.APPLICATION_JSON
    }
    val request = HttpEntity<String>(objectMapper.writeValueAsString(message), headers)
    return restTemplate.exchange(mc1Url, HttpMethod.POST, request, String::class.java)
  }

}