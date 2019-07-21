package com.example.cokesfa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cokesfa.sessionmanager.UserSessionManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_user_score.*

class UserScore : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_score)

        var counter = 0
        val userEmail = UserSessionManager(this).getUserDetails()
        val rootName = userEmail.substring(0,userEmail.indexOf("@"))
        val ref = FirebaseDatabase.getInstance().getReference("Orders").child(rootName)
        ref.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var msg=""
                for(d in p0.children) {
                    counter+=1
                }
                var percentage = counter*10
                Log.d("Counter", percentage.toString())
                if(percentage>=100) {
                    msg = "Your score is 100%"
                    textView9.setText(msg)
                }
                else {
                    msg = "Your score is $percentage%"
                    textView9.setText(msg)
                }
            }

        })


    }
}
