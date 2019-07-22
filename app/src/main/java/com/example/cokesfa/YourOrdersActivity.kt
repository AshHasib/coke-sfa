package com.example.cokesfa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.cokesfa.adapters.OrderAdapter
import com.example.cokesfa.models.Order
import com.example.cokesfa.sessionmanager.UserSessionManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_your_orders.*

class YourOrdersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_orders)

        val listOrder = arrayListOf<Order>()

        val orderAdapter = OrderAdapter(this,listOrder)


        val user = UserSessionManager(this).getUserDetails()

        val node = user.substring(0,user.indexOf("@"))

        val ref = FirebaseDatabase.getInstance().getReference("Orders").child(node)

        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for(ds in p0.children) {
                    val o = ds.getValue(Order::class.java)
                    listOrder.add(o!!)
                    orderAdapter.notifyDataSetChanged()
                }
            }

        })


        ordersRecycler.layoutManager = LinearLayoutManager(this)
        ordersRecycler.setHasFixedSize(true)
        ordersRecycler.adapter = orderAdapter


    }
}
