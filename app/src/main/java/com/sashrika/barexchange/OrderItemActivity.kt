package com.sashrika.barexchange

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_item.*


class OrderItemActivity : AppCompatActivity() {

    val itemdata = ArrayList<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val mLinearLayout = LinearLayoutManager(this)
        val adapter = ItemAdapter(this,itemdata)

        val intent = intent
        val name = intent.getStringExtra("Item")
        Log.e("TAG",name)


        val dbRef = FirebaseDatabase.getInstance().reference
        dbRef.child(name).addChildEventListener(object : ChildEventListener{
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
                var dataItem : Item?  = dataSnapshot.getValue(Item::class.java)
                itemdata.add(dataItem!!)
                adapter.notifyDataSetChanged()
                Log.e("TAG", "onChildAdded : Name : ${dataItem?.Name}")
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                //when removed
            }

        })

        recyclerViewitem.setLayoutManager(mLinearLayout)

        recyclerViewitem.adapter = adapter
    }
}