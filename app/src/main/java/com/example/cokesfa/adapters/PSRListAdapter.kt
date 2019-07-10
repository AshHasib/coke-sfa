package com.example.cokesfa.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.cokesfa.R
import com.example.cokesfa.models.PSR

class PSRListAdapter (val context:Context, val psrList:List<PSR>?) : RecyclerView.Adapter<PSRListAdapter.ViewHolder>() {


    class ViewHolder(view:View) : RecyclerView.ViewHolder(view) {

        val cardSinglePsr = view.findViewById<CardView>(R.id.cardSinglePsr)
        val txtPsrName = view.findViewById<TextView>(R.id.txtPsrName)
        val txtPsrRegion = view.findViewById<TextView>(R.id.txtPsrRegion)

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PSRListAdapter.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.single_psr_item, p0,false)


        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return psrList!!.size
    }

    override fun onBindViewHolder(holder: PSRListAdapter.ViewHolder, i: Int) {
        holder.txtPsrName.setText(psrList!!.get(i).name)
        holder.txtPsrRegion.setText(psrList!!.get(i).region)
    }

}