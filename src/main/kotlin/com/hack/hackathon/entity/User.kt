package com.hack.hackathon.entity

import com.hack.hackathon.security.Role
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity(name = "users")
@Table(name = "users")

data class User(
    var role: Role,
    @NotNull(message = "Значение не должно быть пустым")
    @Column(name = "group_") var group: String,
    @NotNull(message = "Значение не должно быть пустым") @Size(
        min = 5,
        max = 15,
        message = "Длина пароля должна быть не менее 5 символов и не более 15 символов!"
    ) var password: String,
    @Size(
        min = 5, max = 15, message = "Длина имени пользователя должна быть не менее 5 символов и не более 15 символов!"
    ) @NotNull(message = "Значение не должно быть пустым")
    var username: String,
    @NotNull(message = "Значение не должно быть пустым")
    var homeAddress: String,
    @Id @GeneratedValue var id: Long? = null
)