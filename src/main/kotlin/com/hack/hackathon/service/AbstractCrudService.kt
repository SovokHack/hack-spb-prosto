package com.hack.hackathon.service

import com.hack.hackathon.entity.WithId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
abstract class AbstractCrudService<T : WithId> {
    abstract fun  supplierNotFound() : (Long) -> java.lang.RuntimeException

    abstract fun getRepository(): JpaRepository<T, Long>

    fun save(entity: T): T {
        return getRepository().save(entity)
    }

    fun get(id: Long): T {
        return getRepository().findById(id).orElseThrow { supplierNotFound().invoke(id) }
    }

    fun page(page: Int, size: Int): Page<T> {
        return getRepository().findAll(PageRequest.of(page.coerceAtLeast(1), size.coerceAtLeast(10)))
    }

     fun <DTO> update(id: Long, entityToUpdate: DTO, mapper: (T, DTO) -> T): T {
        val entity = get(id)
        return save(mapper(entity, entityToUpdate))
    }
}