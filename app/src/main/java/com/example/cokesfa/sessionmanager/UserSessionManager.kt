package com.example.cokesfa.sessionmanager

import android.content.Context

class UserSessionManager(val context: Context) {

    val PREF_NAME="UserSessionManager"
    val KEY_EMAIL="EMAIL"
    val KEY_PASSWORD="PASSWORD"
    val IS_LOGGED_IN="LOGGED_IN"
    val MODE_PRIVATE=0

    val preferences=context.getSharedPreferences(PREF_NAME,MODE_PRIVATE)



    fun createSession(email:String,password:String) {
        val editor=preferences.edit()
        editor.putString(KEY_EMAIL,email)
        editor.putString(KEY_PASSWORD,password)
        editor.putBoolean(IS_LOGGED_IN,true)
        editor.apply()
    }

    fun deleteSession(){
        val editor=preferences.edit()
        editor.clear()
        editor.putBoolean(IS_LOGGED_IN,false)
        editor.apply()
    }


    fun isUserLoggedIn() : Boolean{
        return preferences.getBoolean(IS_LOGGED_IN, false)
    }


}