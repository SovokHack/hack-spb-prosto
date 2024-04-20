package com.hack.hackathon.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
open class WithId {
    @Id var id : Long? = null
}