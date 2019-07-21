package com.example.cokesfa.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import android.content.Intent

import android.net.Uri
import android.support.v7.widget.CardView
import android.util.Log
import android.widget.TextView
import com.example.cokesfa.R
import com.example.cokesfa.UserScore
import com.example.cokesfa.models.PSR
import com.example.cokesfa.sessionmanager.UserSessionManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v:View= inflater.inflate(R.layout.fragment_home,container,false)


        /**
         * init
         */
        val txtTopSellerName = v.findViewById<TextView>(R.id.txtTopSellerName)
        val txtTopSellerRegion = v.findViewById<TextView>(R.id.txtTopSellerRegion)
        val numOrders = v.findViewById<TextView>(R.id.numOrders)

        val yourProgress = v.findViewById<ProgressBar>(com.example.cokesfa.R.id.yourProgress)
        yourProgress.progressTintList= ColorStateList.valueOf(Color.RED)
        setProgress()







        /**
         * Listeners
         */
        val cardVisitSite = v.findViewById<CardView>(R.id.cardVisitSite)
        cardVisitSite.setOnClickListener{
            openWebsite()
        }



        val cardYourScore = v.findViewById<CardView>(R.id.cardYourScore)
        cardYourScore.setOnClickListener {
            startActivity(Intent(context, UserScore::class.java))
        }



        val cardPsrPolicy = v.findViewById<CardView>(R.id.cardPsrPolicy)
        cardPsrPolicy.setOnClickListener {
            val url = "https://www.nlg.nhs.uk/content/uploads/2015/01/Sales-Representatives-Policy-DCP183.pdf"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }




        //setting the top seller CardView
        setTopSeller()









        return v
    }


















    /**
     * Open website
     */
    private fun openWebsite() {
        val url = "http://www.coca-cola.com.bd/bn/home/"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }












    /**
     * Set progress
     */

    private fun setProgress() {
        var counter = 0
        val userEmail = UserSessionManager(context!!).getUserDetails()
        val rootName = userEmail.substring(0,userEmail.indexOf("@"))
        val ref = FirebaseDatabase.getInstance().getReference("Orders").child(rootName)
        ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for(d in p0.children) {
                    counter++
                }

                yourProgress.setProgress(counter*10)
                txtProgress.setText("${counter*10}%")
            }

        })
    }














    /**
     * Set top seller
     */
    private fun setTopSeller() {


        val rootRef = FirebaseDatabase.getInstance().getReference("Orders")
        rootRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var nodeTopUser = ""
                var temp = 0L
                for(ds in p0.children) {
                    if(ds.childrenCount>temp) {
                        temp=ds.childrenCount
                        nodeTopUser=ds.key.toString()
                    }
                }

                numOrders.setText("Total $temp orders")
                setTopSellerProperties(nodeTopUser)


            }

        })



    }




    private fun setTopSellerProperties(nodeName:String){

        val email = nodeName+"@gmail.com"
        val userRef = FirebaseDatabase.getInstance().getReference("Users")
        userRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for(d in p0.children) {
                    val psr = d.getValue(PSR::class.java)
                    if(psr!!.email.equals(email)) {

                        Log.d("USEREMAIL",psr.name)
                        txtTopSellerName.setText(psr.name)
                        txtTopSellerRegion.setText(psr.region)

                    }
                }

            }
        })
    }



}