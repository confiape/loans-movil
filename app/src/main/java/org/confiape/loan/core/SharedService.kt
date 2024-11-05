package org.confiape.loan.core

import android.content.Context
import android.content.SharedPreferences
import org.confiape.loan.models.PersonDto

object SharedService {
    fun saveToken(context: Context, name: String, token: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        editor.putString(name, token)
        editor.apply()
    }
    fun saveUser(context: Context,  personDto: PersonDto) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        editor.putString("user_name", personDto.name)
        editor.apply()
    }
    fun getUserName(context: Context): String {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        return sharedPreferences.getString("user_name", null) ?: ""
    }

    fun getToken(name: String, context: Context): String {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        return sharedPreferences.getString(name, null) ?: ""
    }
}