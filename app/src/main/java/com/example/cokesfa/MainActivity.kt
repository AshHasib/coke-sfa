package com.example.cokesfa

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import com.example.cokesfa.adapters.ViewPagerAdapter
import com.example.cokesfa.fragments.HomeFragment
import com.example.cokesfa.fragments.OrderFragment
import com.example.cokesfa.fragments.PSRFragment
import com.example.cokesfa.sessionmanager.UserSessionManager
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


        /**
         * Some dummy checking codes


        val ref = FirebaseDatabase.getInstance().getReference("Users")

        ref.addListenerForSingleValueEvent(object:ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for(snapShot in p0.children) {
                    val psr=snapShot.getValue(PSR::class.java)
                    Log.d("PSR",psr!!.name)
                    Log.d("PSR",psr!!.email)
                    Log.d("PSR",psr!!.phoneNumber)
                }
            }

        })

         */


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


        val btnLogout=d.findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            userSessionManager.deleteSession()
            d.dismiss()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }




}
