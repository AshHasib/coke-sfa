package com.example.cokesfa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.cokesfa.models.Order
import com.example.cokesfa.sessionmanager.UserSessionManager
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_order.*
import java.text.SimpleDateFormat
import java.util.*

class OrderActivity : AppCompatActivity() {


    val SELECT_PRODUCT = "Select Product"
    val SET_QUANTITY = "Set Quantity"
    val SELECT_REGION = "Select Region"

    var counter =0;

    val orders = arrayListOf<Order>()

    lateinit var sessionManager : UserSessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        sessionManager = UserSessionManager(this)

        loadSpinners()


        btnStack.setOnClickListener {
            stackOrder()
        }


        btnSubmit.setOnClickListener {
            submitOrder()
        }
    }





    /**
     * Submitting the stacked orders
     */
    private fun submitOrder() {
        if(orders.size>0) {
            val node = sessionManager.getUserDetails().substring(0,sessionManager.getUserDetails().indexOf("@"))
            val orderRef = FirebaseDatabase
                .getInstance()
                .getReference("Orders")
                .child(node)
                .push()

            for(item in orders) {
                orderRef.setValue(item)
            }


            Toast.makeText(this,"Your order has been submitted", Toast.LENGTH_LONG).show()

            clearAll()
        }

        else {
            Toast.makeText(this,"Make orders first", Toast.LENGTH_LONG).show()
        }
    }

















    /**
     * Stacking an order
     */
    private fun stackOrder () {
        val productName = productSpinner.selectedItem.toString()
        val quantity = quantitySpinner.selectedItem.toString()
        val region = regionSpinner.selectedItem.toString()
        val currentDate:String = SimpleDateFormat("dd-MM-yyyy").format(Date())
        val deadLine = txtDeadline.text.toString()
        val psrEmail = sessionManager.getUserDetails()
        if(isValid(productName,quantity, region, deadLine)) {

            val order= Order(oId = 1, date = currentDate, deadline = deadLine, psrEmail = psrEmail,productQuantity = quantity,
                productName = productName,region = region)

            orders.add(order)

            counter++
            btnSubmit.setText("SUBMIT ($counter)")

            Toast.makeText(this, "Your order has been stacked", Toast.LENGTH_LONG).show()

        }

        else {
            Toast.makeText(this,"Fill up the form properly", Toast.LENGTH_LONG).show()
        }
    }










    /**
     * Validating the input data
     */
    private fun isValid(productName: String, quantity: String, region:String, deadLine: String): Boolean {
        if(productName.equals(SELECT_PRODUCT)) return false
        if(region.equals(SELECT_REGION)) return false
        if(quantity.equals(SET_QUANTITY)) return false
        if(deadLine.isBlank()) return false
        else return true

    }











    /**
     * Loading the spinners in the form
     */
    private fun loadSpinners() {
        val productList = arrayListOf<String>(SELECT_PRODUCT, "Coke", "Fanta", "Sprite", "Kinley")
        val quantityList = arrayListOf<String>(SET_QUANTITY, "2","4", "6", "8", "10", "11", "12")
        val regionList = arrayListOf<String>(SELECT_REGION, "Cumilla", "Rajshahi", "Khulna", "Dhaka", "Chittagong")
        productSpinner.prompt= "Select Product"
        quantitySpinner.prompt = "Set Quantity"

        val productAdapter = ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, productList)
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        productSpinner.adapter = productAdapter

        val quantityAdapter = ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, quantityList)
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        quantitySpinner.adapter=quantityAdapter

        val regionAdapter= ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,regionList)
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        regionSpinner.adapter=regionAdapter
    }






    /**
     * Clearing the form
     */
    private fun clearAll() {
        txtDeadline.setText("")
        txtOptDesc.setText("")
        btnSubmit.setText("SUBMIT")
    }
}
