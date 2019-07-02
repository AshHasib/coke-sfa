package com.example.cokesfa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.button.MaterialButton
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.cokesfa.regex.EmailChecker
import com.example.cokesfa.regex.PasswordChecker
import com.example.cokesfa.sessionmanager.UserSessionManager
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var userSessionManager:UserSessionManager


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        userSessionManager= UserSessionManager(this)




        btnLogin?.setOnClickListener{
            val username=txtLoginEmail.text.toString()
            val password=txtLoginPassword.text.toString()
            Log.d("LoginActivity","Login Listener")
            saveToSession(username,password)
        }

    }




    /**
     * Creating a session for the user
     */
    private fun saveToSession(username:String?,password:String?) {
        if(isValid(username,password)) {
            if(isPsr(username,password)) {
                userSessionManager.createSession(username!!,password!!)
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }








    /**
     * Checking if the login data is valid regex-wise
     */
    private fun isValid(username: String?,password: String?) : Boolean{

        if (!EmailChecker(username!!).isValid()) {
            Toast.makeText(this,"Enter valid email",Toast.LENGTH_LONG).show()
            return false
        }

        else if (!PasswordChecker(password!!).isValid()) {
            Toast.makeText(this,"Enter a password with numbers and letters",Toast.LENGTH_LONG).show()
            return false
        }

        else if(!checkBox.isChecked) {
            Toast.makeText(this,"Please agree to the terms and conditions",Toast.LENGTH_LONG).show()
            return false
        }

        else return true
    }







    /**
     * Checking if the login data is already entered in the database
     */
    private fun isPsr(username: String?,password: String?) :Boolean {
        return true
    }

}
