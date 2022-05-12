package ru.korshun.service1.repo

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.korshun.service1.entity.SessionEntity
import java.util.*

@Repository
interface SessionRepo : CrudRepository<SessionEntity, Long> {
  fun findFirstByOrderByIdDesc() : Optional<SessionEntity>
}