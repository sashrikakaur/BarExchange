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
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row.view.*


class ItemAdapter(private val context: Context, private val orderitem: ArrayList<Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentUser = orderitem[position]
        with(holder?.itemView) {
            if(currentUser.Current>212){
//                Picasso.get().load().into(this!!.ivImageItem);
                this!!.ivImageItem.setImageResource(R.drawable.ic_trending_up_black_24dp)
            }
            else{
                this!!.ivImageItem.setImageResource(R.drawable.ic_trending_down_black_24dp)
            }
            this!!.tvItemTitle.setText(currentUser.Name)
            this!!.tvItemPrice.setText(currentUser.Current.toString())

        }

        holder?.itemView.setOnClickListener {
            val intent = Intent(context,BillActivity::class.java)
            intent.putExtra("Item Name",currentUser.Name)
            intent.putExtra("Type",currentUser.Type)
            intent.putExtra("Item Price",currentUser.Current)
//            intent.putExtra("Item Count",currentUser.Count)
            currentUser.Count++;


                val dbRef = FirebaseDatabase.getInstance().reference
            dbRef.child(currentUser.Type).child(currentUser.Name).child("Count").setValue(currentUser.Count)
            if(currentUser.Count>5) {
                dbRef.child(currentUser.Type).child(currentUser.Name).child("Current").setValue(currentUser.Current+0.02*currentUser.Current)
                dbRef.child("Status").child(currentUser.Name).setValue(currentUser.Current+0.02*currentUser.Current)
                dbRef.child(currentUser.Type).child(currentUser.Name).child("Count").setValue(0)
            }
//                dbRef.child(currentUser.Name).child("Count").setValue(currentUser.Count)
//                dbRef.child(currentUser.Name).child("Current").setValue(currentUser.Current+12.5*(currentUser.Count/5))

            context.startActivity(intent)
        }

    }

    inner class holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): ItemAdapter.holder {
        val layoutInflater = LayoutInflater.from(container.context)
        val view = layoutInflater.inflate(R.layout.item_row, container, false)
        return holder(view)
    }

    override fun getItemCount() = orderitem.size
}

