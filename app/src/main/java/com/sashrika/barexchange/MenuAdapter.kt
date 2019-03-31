package com.sashrika.barexchange

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.menu_item_row.view.*

class MenuAdapter(private val context: Context, private val orderitem: ArrayList<Menu>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentUser = orderitem[position]
        with(holder?.itemView) {
            this!!.tvTitlemenu.setText(currentUser.Name)
            Picasso.get().load(currentUser.Image.toString()).into(this!!.ivImagemenu);
//            this!!.ivImage.setImageResource(currentUser.Image)
        }
        holder?.itemView.setOnClickListener {
            val intent = Intent(context,OrderItemActivity::class.java)
            intent.putExtra("Item",currentUser.Name)
            Log.e("Tag",currentUser.Name)
            context.startActivity(intent)
        }

    }

    inner class holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): MenuAdapter.holder {
        val layoutInflater = LayoutInflater.from(container.context)
        val view = layoutInflater.inflate(R.layout.menu_item_row, container, false)
        return holder(view)
    }

    override fun getItemCount() = orderitem.size
}

