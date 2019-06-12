package com.example.cokesfa

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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



    }


    fun setupHeader(){
        val actionBar:ActionBar?
        val adapter=ViewPagerAdapter(supportFragmentManager)


        setSupportActionBar(toolBar)

        actionBar=supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)




        adapter.addFragment("Home",HomeFragment())
        adapter.addFragment("Orders",OrderFragment())
        adapter.addFragment("Transactions",TransactionFragment())

        viewPager.adapter=adapter
        tabLayout.setupWithViewPager(viewPager)
    }










    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId) {
            R.id.menu_location -> {
                startActivity(Intent(this,MapActivity::class.java))
            }

        }

        return true
    }

}
