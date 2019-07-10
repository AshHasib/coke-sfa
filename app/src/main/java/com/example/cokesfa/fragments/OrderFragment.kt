package com.example.cokesfa.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cokesfa.OrderActivity
import com.example.cokesfa.R



class OrderFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v:View = inflater.inflate(R.layout.fragment_order,container,false)

        val cardOrder=v.findViewById<CardView>(R.id.cardOrder)

        cardOrder.setOnClickListener {
            startActivity((Intent(context, OrderActivity::class.java)))
        }


        return v
    }
}