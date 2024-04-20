package com.hack.hackathon.entity

import com.hack.hackathon.security.Role
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.proxy.HibernateProxy

@Entity
data class User (
    var role : Role,
    var group : String,
    var password : String,
    var username : String,
    @Id @GeneratedValue var id: Long? = null
) {
}