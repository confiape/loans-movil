package org.confiape.loan.login

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor() {



    suspend fun login(username: String, password: String): Boolean {
        return username == "user" && password == "pas"
    }
}