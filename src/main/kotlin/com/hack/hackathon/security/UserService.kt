package com.hack.hackathon.security

import com.hack.hackathon.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserImpl {
        val user = userRepository.findByUsername(username)
        if (user.isPresent) {
            return UserImpl(user.get())
        } else {
            throw UsernameNotFoundException(username)
        }
    }

    fun registerUser(user: User): User {
        user.password = passwordEncoder.encode(user.password)
        return userRepository.save(user)
    }

    fun usernameExists(username: String) : Boolean {
        return userRepository.existsUserByUsername(username)
    }

    fun updateUser(username: String, user: User): User {
        val userr =loadUserByUsername(username)
        return userRepository.save(user)
    }
}

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String?): Optional<User>
    fun existsUserByUsername(username: String?): Boolean
}