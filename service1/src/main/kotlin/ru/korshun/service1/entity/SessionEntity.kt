package ru.korshun.service1.entity

import javax.persistence.*

@Entity
@Table(name = "sessions")
class SessionEntity(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  var id: Long,

  @Column(name = "session_id", nullable = false)
  var sessionId: Long,

  @Column(name = "mc1_timestamp")
  var mc1Timestamp: Long,

  @Column(name = "mc2_timestamp")
  var mc2Timestamp: Long,

  @Column(name = "mc3_timestamp")
  var mc3Timestamp: Long,

  @Column(name = "end_timestamp")
  var endTimestamp: Long
)