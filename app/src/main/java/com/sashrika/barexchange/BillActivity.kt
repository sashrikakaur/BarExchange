package com.sashrika.barexchange

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_bill.*
import kotlinx.android.synthetic.main.bill_item_row.*


class BillActivity : AppCompatActivity() {
    val billdata = ArrayList<BillType>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        val mLinearLayout = LinearLayoutManager(this)
        val adapter = BillAdapter(this,billdata)

        fab.setImageResource(R.drawable.ic_add_black_24dp)
        fab.setOnClickListener{
            var intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
        }

        val i = intent
        var ItemName = i.getStringExtra("Item Name")
        var ItemPrice =i.getIntExtra("Item Price",0)
        var ItemType =i.getStringExtra("Type")
//        ItemCount++;

        val dbRef = FirebaseDatabase.getInstance().reference

        dbRef.child("Table").child("1").child(ItemName).child("Current").setValue(ItemPrice)
        dbRef.child("Table").child("1").child(ItemName).child("Name").setValue(ItemName)
        dbRef.child("Table").child("1").child(ItemName).child("Type").setValue(ItemType)

        dbRef.child("Table").child("1").addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //when operation failed
                Log.e("T", "onChildAdded : Name : $p0")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                //position of subnode changes
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                //existing data updated
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                //new data added
                var data : BillType?  = dataSnapshot.getValue(BillType::class.java)
//                dbRef.child("Table").child("1").child(ItemName).child("Count").setValue(data!!.Count)

                    billdata.add(data!!)
                    adapter.notifyDataSetChanged()
                Log.e("count","${data?.Count}")
                Log.e("TAG1", "onChildAdded : Name : ${data?.Name}")
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                //when removed
            }

        })


        rvbill.setLayoutManager(mLinearLayout)

        rvbill.adapter = adapter

    }
}
