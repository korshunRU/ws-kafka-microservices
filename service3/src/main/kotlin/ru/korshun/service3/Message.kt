package ru.korshun.service3

import com.fasterxml.jackson.annotation.JsonProperty

data class Message(
  val id: Long,

  @JsonProperty("session_id")
  val sessionId: Long,

  @JsonProperty("MC1_timestamp")
  val mc1Timestamp: Long,

  @JsonProperty("MC2_timestamp")
  val mc2Timestamp: Long,

  @JsonProperty("MC3_timestamp")
  var mc3Timestamp: Long,

  @JsonProperty("end_timestamp")
  val endTimestamp: Long
)