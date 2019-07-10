package com.example.cokesfa.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.cokesfa.R
import com.google.firebase.database.collection.LLRBNode


class HomeFragment : Fragment() {


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v:View= inflater.inflate(R.layout.fragment_home,container,false)

        val yourProgress = v.findViewById<ProgressBar>(R.id.yourProgress)
        yourProgress.progressTintList= ColorStateList.valueOf(Color.RED)


        return v
    }
}