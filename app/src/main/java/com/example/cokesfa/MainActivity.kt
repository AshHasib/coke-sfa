package com.example.cokesfa

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.cokesfa.adapters.ViewPagerAdapter
import com.example.cokesfa.fragments.HomeFragment
import com.example.cokesfa.fragments.OrderFragment
import com.example.cokesfa.fragments.TransactionFragment
import com.example.cokesfa.models.PSR
import com.example.cokesfa.sessionmanager.UserSessionManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val userSessionManager by lazy {
        UserSessionManager(this)
    }

    private val statsDialog by lazy {
        Dialog(this)
    }





    //@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //startActivity(Intent(this,LoginActivity::class.java))

        checkLogin()

        setupHeader()

    }







    /**
     * Checking login state of the app
     */
    private fun checkLogin() {
        if(!userSessionManager.isUserLoggedIn()) {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }












    /**
     * Set up the header part of the MainActivity
     */
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











    /**
     * Creating the menu in the toolbar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }










    /**
     * Handling listeners for menu items
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId) {
            R.id.menu_location -> {
                startActivity(Intent(this,MapActivity::class.java))
            }

            android.R.id.home -> {
                //Toast.makeText(this,"Menu",Toast.LENGTH_LONG).show()
                statsDialog.run {
                    setContentView(R.layout.stats_dialog)
                    window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    initDialogComponents(this)
                    show()
                }
            }

        }

        return true
    }


    /**
     * Handling components of dialog box
     */
    private fun initDialogComponents(d:Dialog) {


        val closeBtn=d.findViewById<ImageView>(R.id.close_btn)
        closeBtn.setOnClickListener{
            d.dismiss()
        }


        val btnLogout=d.findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            userSessionManager.deleteSession()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }




}
