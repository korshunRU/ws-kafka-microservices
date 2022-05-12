package ru.korshun.service1

import com.fasterxml.jackson.annotation.JsonProperty

data class Message(
  val id: Long,

  @JsonProperty("session_id")
  val sessionId: Long,

  @JsonProperty("MC1_timestamp")
  val mc1Timestamp: Long,

  @JsonProperty("MC2_timestamp")
  val mc2Timestamp: Long = 0,

  @JsonProperty("MC3_timestamp")
  val mc3Timestamp: Long = 0,

  @JsonProperty("end_timestamp")
  var endTimestamp: Long = 0
)
