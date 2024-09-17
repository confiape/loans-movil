package org.confiape.loan.core

import android.content.Context
import android.content.SharedPreferences

object SharedService {
    fun saveToken(context: Context, name: String, token: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        editor.putString(name, token)
        editor.apply()
    }

    fun getToken(name: String, context: Context): String {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        return sharedPreferences.getString(name, null) ?: ""
    }
}