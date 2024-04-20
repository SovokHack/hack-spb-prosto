package com.hack.hackathon.security

import com.hack.hackathon.entity.User
import com.vaadin.flow.spring.security.AuthenticationContext
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component


@Component
class SecurityService(private val authenticationContext: AuthenticationContext) {
    val authenticatedUser: User?
        get() {
            val opt = authenticationContext.getAuthenticatedUser(UserImpl::class.java)
            return if (opt.isPresent) {
                opt.get().user
            } else {
                null
            }
        }

    fun logout() {
        authenticationContext.logout()
    }
}