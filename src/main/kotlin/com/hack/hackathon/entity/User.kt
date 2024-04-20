package com.hack.hackathon.entity

import com.hack.hackathon.security.Role
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.proxy.HibernateProxy

@Entity(name = "users")
@Table(name = "users")
data class User (
    var role : Role,
    var group : String,
    var password : String,
    var username : String,
    var homeAddress : String,
    @Id @GeneratedValue var id: Long? = null
) {
}