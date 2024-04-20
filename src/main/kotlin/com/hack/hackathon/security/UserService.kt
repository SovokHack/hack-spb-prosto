package com.hack.hackathon.security

import com.hack.hackathon.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return UserImpl(userRepository.findByUsername(username))
    }

    fun registerUser(user: User): User {
        user.password = passwordEncoder.encode(user.password)
        return userRepository.save(user)
    }

    fun usernameExists(username: String) : Boolean {
        return userRepository.existsUserByUsername(username)
    }
}

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String?): User
    fun existsUserByUsername(username: String?): Boolean
}