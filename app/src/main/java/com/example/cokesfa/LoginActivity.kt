package com.example.cokesfa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.button.MaterialButton
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.cokesfa.models.PSR
import com.example.cokesfa.regex.EmailChecker
import com.example.cokesfa.regex.PasswordChecker
import com.example.cokesfa.sessionmanager.UserSessionManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var userSessionManager:UserSessionManager

    val list= arrayListOf<PSR>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        userSessionManager= UserSessionManager(this)




        //The list of all users are loaded from JSON tree
        val userListReference:DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        userListReference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(ds: DataSnapshot) {
                for(d in ds.children) {
                    val psr=d.getValue(PSR::class.java)
                    //Log.d("PSRLIST",psr!!.email)
                    list.add(psr!!)
                }
            }

        })



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
                finish()
            }

            else {
                Toast.makeText(this,"Enter correct information",Toast.LENGTH_LONG).show()
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

        else if(!PasswordChecker(password!!).isLengthed()) {
            Toast.makeText(this,"Password must be 8 characters long",Toast.LENGTH_LONG).show()
            return false
        }

        else if(!PasswordChecker(password!!).isValid()) {
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
        var flag=false


        for(l in list) {
            if(l.email.equals(username) and l.password.equals(password)) return true
            //Log.d("PSRLIST",l.email)
        }


        return flag
    }

}
