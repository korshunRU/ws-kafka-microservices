package ru.korshun.service2

import com.fasterxml.jackson.annotation.JsonProperty

data class Message(
  val id: Long,

  @JsonProperty("session_id")
  val sessionId: Long,

  @JsonProperty("MC1_timestamp")
  val mc1Timestamp: Long,

  @JsonProperty("MC2_timestamp")
  var mc2Timestamp: Long,

  @JsonProperty("MC3_timestamp")
  val mc3Timestamp: Long,

  @JsonProperty("end_timestamp")
  val endTimestamp: Long
)