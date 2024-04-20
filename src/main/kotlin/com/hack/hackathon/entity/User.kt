package com.hack.hackathon.entity

import com.hack.hackathon.security.Role
import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy

@Entity(name = "users")
@Table(name = "users")
data class User (
    var role : Role,
    @Column(name = "group_")
    var group : String,
    var password : String,
    var username : String,
    var homeAddress : String,
    @OneToMany(mappedBy = "user") var events : MutableList<Event> = mutableListOf(),
    @Id @GeneratedValue var id: Long? = null
) {
}