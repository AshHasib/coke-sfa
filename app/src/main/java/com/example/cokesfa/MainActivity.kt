package com.example.cokesfa

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import com.example.cokesfa.adapters.ViewPagerAdapter
import com.example.cokesfa.fragments.HomeFragment
import com.example.cokesfa.fragments.OrderFragment
import com.example.cokesfa.fragments.TransactionFragment
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupHeader()

        val adapter=ViewPagerAdapter(supportFragmentManager)




        adapter.addFragment("Home",HomeFragment())
        adapter.addFragment("Orders",OrderFragment())
        adapter.addFragment("Transactions",TransactionFragment())

        viewPager.adapter=adapter
        tabLayout.setupWithViewPager(viewPager)

    }


    fun setupHeader(){
        val actionBar:ActionBar?

        setSupportActionBar(toolBar)

        actionBar=supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

}
