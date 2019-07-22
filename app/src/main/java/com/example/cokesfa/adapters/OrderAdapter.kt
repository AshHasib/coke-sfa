package com.example.cokesfa.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.cokesfa.R
import com.example.cokesfa.models.Order

class OrderAdapter(val context: Context, val listOrder: List<Order>) : RecyclerView.Adapter<OrderAdapter.ViewHolder> () {


    class ViewHolder(view:View) : RecyclerView.ViewHolder(view) {

        val txtProductName = view.findViewById<TextView>(R.id.txtPName)
        val txtQuantity = view.findViewById<TextView>(R.id.txtPQuantity)
        val txtDeadline = view.findViewById<TextView>(R.id.txtPDeadline)



    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OrderAdapter.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.single_order_item,p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

    override fun onBindViewHolder(holder: OrderAdapter.ViewHolder, p1: Int) {
        val order = listOrder.get(p1)

        holder.txtProductName.setText(order.productName)
        holder.txtDeadline.setText("Deadline: $order.deadline")
        holder.txtQuantity.setText("Quantity: ${order.productQuantity}")
    }

}