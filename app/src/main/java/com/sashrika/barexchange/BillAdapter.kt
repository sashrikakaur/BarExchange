package com.sashrika.barexchange

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.bill_item_row.view.*
import kotlinx.android.synthetic.main.item_row.view.*

class BillAdapter(private val context: Context, private val billitem: ArrayList<BillType>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val dbRef = FirebaseDatabase.getInstance().reference
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentBill = billitem[position]
        with(holder?.itemView) {
            this!!.tvBillName.setText(currentBill.Name)
            this!!.tvBillPrice.setText(currentBill.Current.toString())
//            this!!.ivImage.setImageResource(currentUser.Image)
            buttonadd.setOnClickListener{
//                billitem.add(currentBill)
//                var num :Int = Integer.parseInt(this!!.tvCount.text.toString())
//                num = num++;
//                this!!.tvCount.setText(num.toString())
                currentBill.Count++
                currentBill.CountCheck++
                tvCount.text = currentBill.Count.toString()
                Log.e("g",currentBill.Count.toString())
                FirebaseDatabase.getInstance().reference.child(currentBill.Type).child(currentBill.Name).child("Count").setValue(currentBill.CountCheck)

                FirebaseDatabase.getInstance().reference.child("Table").child("1").child(currentBill.Name).child("Count").setValue(currentBill.Count)
                FirebaseDatabase.getInstance().reference.child(currentBill.Type).child(currentBill.Name).child("Count").setValue(currentBill.Count)
                if(currentBill.CountCheck>5){
                    currentBill.CountCheck = 0
                    FirebaseDatabase.getInstance().reference.child(currentBill.Type).child(currentBill.Name).child("Count").setValue(0)
                    FirebaseDatabase.getInstance().reference.child("Status").child(currentBill.Name).setValue(currentBill.Current+0.02*currentBill.Current)
                    FirebaseDatabase.getInstance().reference.child(currentBill.Type).child(currentBill.Name).child("Current").setValue(currentBill.Current+0.02*currentBill.Current)
                }
            }
            buttondelete.setOnClickListener{
                currentBill.Count--
                if(currentBill.Count<1){
                    Log.e("t","del : ${currentBill.Count}")
                    tvCount.text = currentBill.Count.toString()
                    dbRef.child("Table").child("1").child(currentBill.Name).removeValue()
                    dbRef.child("Table").child("1").child(currentBill.Name).child("Count").removeValue()
                    billitem.removeAt(position)
                    notifyDataSetChanged()
                }else {
                    FirebaseDatabase.getInstance().reference.child("Table").child("1").child(currentBill.Name)
                        .child("Count").setValue(currentBill.Count)
                    tvCount.text = currentBill.Count.toString()
                    notifyDataSetChanged()
                }
            }
        }

    }

    inner class holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): BillAdapter.holder {
        val layoutInflater = LayoutInflater.from(container.context)
        val view = layoutInflater.inflate(R.layout.bill_item_row, container, false)
        return holder(view)
    }

    override fun getItemCount() = billitem.size
}

