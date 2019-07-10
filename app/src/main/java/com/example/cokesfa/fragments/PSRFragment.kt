package com.example.cokesfa.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cokesfa.R
import com.example.cokesfa.adapters.PSRListAdapter
import com.example.cokesfa.models.PSR
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PSRFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.fragment_psrs,container,false)


        val psrList = arrayListOf<PSR>()






        val psrRecyclerView= v.findViewById<RecyclerView>(R.id.psrRecyclerView)
        val psrListAdapter = PSRListAdapter(context!!,psrList)



        val userReference = FirebaseDatabase.getInstance().getReference("Users")
        userReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }


            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(data in dataSnapshot.children) {
                    val psr= data.getValue(PSR::class.java)
                    psrList.add(psr!!)
                    psrListAdapter.notifyDataSetChanged()
                }
            }
        })

        psrRecyclerView.layoutManager = LinearLayoutManager(context)
        psrRecyclerView.hasFixedSize()
        psrRecyclerView.adapter = psrListAdapter



        return v
    }
}