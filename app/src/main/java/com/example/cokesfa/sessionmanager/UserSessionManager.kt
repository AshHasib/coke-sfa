package com.example.cokesfa.sessionmanager

import android.content.Context
import com.example.cokesfa.models.PSR
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserSessionManager(val context: Context) {

    val PREF_NAME="UserSessionManager"
    val KEY_NAME="NAME"
    val KEY_EMAIL="EMAIL"
    val KEY_PHONE_NUMBER="PHONE_NUMBER"
    val KEY_REGION="REGION"
    val KEY_PASSWORD="PASSWORD"
    val IS_LOGGED_IN="LOGGED_IN"
    val MODE_PRIVATE=0

    val preferences=context.getSharedPreferences(PREF_NAME,MODE_PRIVATE)



    fun createSession(username: String, password :String) {
        val editor=preferences.edit()

        editor.putString(KEY_EMAIL,username)
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



    fun getUserDetails () : String {
        return preferences.getString(KEY_EMAIL,"NoEmail")
    }



}