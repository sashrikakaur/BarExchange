package com.sashrika.barexchange

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.table_item_row.*


class MainActivity : AppCompatActivity() {

    val tableno = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var statuslist : String = ""
        tableno.add("1")
        tableno.add("2")
        tableno.add("3")
        tableno.add("4")
        tableno.add("5")
        tableno.add("6")
        tableno.add("7")
        tableno.add("8")
        tableno.add("9")
        tableno.add("10")
        tableno.add("11")
        tableno.add("12")

        val mGridLayoutManager = GridLayoutManager(this@MainActivity, 3)
        val adapter = TableAdapter(this,tableno)
        recyclerView.adapter = adapter
        tvStats.isSelected = true


        val dbRef = FirebaseDatabase.getInstance().reference

        dbRef.child("Status").addChildEventListener(object : ChildEventListener {
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
                var name : String? = dataSnapshot.key
                var value : Long? = dataSnapshot.getValue(Long::class.java)

                statuslist += name +" "+ value +"  "
                Log.e("TAG", "onChildAdded : Name : ${statuslist}")
                tvStats.setText(statuslist)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                //when removed
            }

        })




        recyclerView.setLayoutManager(mGridLayoutManager)

    }
}
