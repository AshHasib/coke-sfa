package com.example.cokesfa

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.cokesfa.adapters.ViewPagerAdapter
import com.example.cokesfa.fragments.HomeFragment
import com.example.cokesfa.fragments.OrderFragment
import com.example.cokesfa.fragments.PSRFragment
import com.example.cokesfa.models.PSR
import com.example.cokesfa.sessionmanager.UserSessionManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.jar.Manifest

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
        adapter.addFragment("Order",OrderFragment())
        adapter.addFragment("PSRs",PSRFragment())

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

        val txtYourAccomplishment = d.findViewById<TextView>(R.id.txtYourAccomplishment)
        val txtYourPercentage = d.findViewById<TextView>(R.id.txtYourWorkPercentage)




        var counter = 0
        val userEmail = UserSessionManager(this).getUserDetails()
        val rootName = userEmail.substring(0,userEmail.indexOf("@"))
        val ref = FirebaseDatabase.getInstance().getReference("Orders").child(rootName)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for(d in p0.children) {
                    counter++
                }

                txtYourAccomplishment.setText("Your Accomplishment: $counter")
                txtYourPercentage.setText("Work Percentage: ${counter*10}%")
            }

        })






        val phoneCallBtn = d.findViewById<ImageView>(R.id.btnPhoneCall)

        phoneCallBtn.setOnClickListener{
            makePhoneCall()
        }




        val btnLogout=d.findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            userSessionManager.deleteSession()
            d.dismiss()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }


    /**
     * Making a phone call to demo distributor
     */
    private fun makePhoneCall() {
        val phoneNumber = "tel:01537171186"

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE),1)
        }
        else {
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber)))
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode==1) {
            if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                makePhoneCall()
            }
        }
    }




}
