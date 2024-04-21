package com.hack.hackathon.entity

import com.hack.hackathon.security.Role
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.NotBlank

@Entity(name = "users")
@Table(name = "users")
data class User(
    var role: Role,

    @Column(name = "group_")
    @NotBlank(message = "Группа не должна быть пустой") // Используем @NotBlank вместо @NotEmpty
    var group: String,

    @NotBlank(message = "Пароль не должен быть пустым") // Используем @NotBlank вместо @NotEmpty
    @Size(min = 5, max = 15, message = "Пароль должен содержать от 5 до 15 символов")
    var password: String,

    @NotBlank(message = "Имя пользователя не должно быть пустым") // Используем @NotBlank вместо @NotEmpty
    @Size(min = 5, max = 15, message = "Имя пользователя должно содержать от 5 до 15 символов")
    var username: String,

    @NotBlank(message = "Домашний адрес не должен быть пустым") // Используем @NotBlank вместо @NotEmpty
    @Embedded var homeAddress: Coordinate,

    @Id @GeneratedValue
    var id: Long? = null
)
