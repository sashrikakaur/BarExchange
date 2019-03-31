package com.sashrika.barexchange

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*


class MenuActivity : AppCompatActivity() {
    val cocktails : ArrayList<Menu> = ArrayList()
    val mocktails : ArrayList<Menu> = ArrayList()
//    val food : ArrayList<Menu> = ArrayList()


    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_cocktails -> {
//                    message.setText("Cocktails")
                    val mGridLayoutManager = GridLayoutManager(this, 2)
                    val adapter = MenuAdapter(this,cocktails)
                    recyclerViewmenu.adapter = adapter
                    recyclerViewmenu.setLayoutManager(mGridLayoutManager)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_mocktails -> {
//                    message.setText("Mocktails")
                    val mGridLayoutManager = GridLayoutManager(this, 2)
                    val adapter = MenuAdapter(this,mocktails)
                    recyclerViewmenu.adapter = adapter
                    recyclerViewmenu.setLayoutManager(mGridLayoutManager)

                    return@OnNavigationItemSelectedListener true
                }
//                R.id.navigation_food -> {
////                    message.setText("Food")
//                    val mGridLayoutManager = GridLayoutManager(this, 2)
//                    val adapter = MenuAdapter(this,food)
//                    recyclerViewmenu.adapter = adapter
//                    recyclerViewmenu.setLayoutManager(mGridLayoutManager)
//
//                    return@OnNavigationItemSelectedListener true
//                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        var statuslist : String =""
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val mGridLayoutManager = GridLayoutManager(this, 2)

        cocktails.add(Menu("Beer", "https://52brews.com/wp-content/uploads/2018/08/the-finished-product-beer-view-300x251.jpg"))
        cocktails.add(Menu("Rum","https://www.ocado.com/productImages/165/16545011_1_640x640.jpg?identifier=b073cdeea1e526a49c66ad3a34e46e6f"))
        cocktails.add(Menu("Vodka","https://media-cdn.tripadvisor.com/media/photo-s/06/09/a5/dc/il-capo-bar.jpg"))

//        cocktails.add(Menu("d",R.drawable.ic_personal_video_black_24dp))
//        cocktails.add(Menu("g",R.drawable.ic_personal_video_black_24dp))
//        cocktails.add(Menu("h",R.drawable.ic_personal_video_black_24dp))


        mocktails.add(Menu("Coolers", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRhXwBx3N52dsFZ_LdYn8byZzpLJtIRh60kSg5B_cfFiKPTKSQm"))
        mocktails.add(Menu("Shakes and Smoothies","https://ratlamee.com/wp-content/uploads/2018/10/Kitkat-Shake.jpg"))

//        food.add(Menu("i", 1))
//        food.add(Menu("j",R.drawable.ic_personal_video_black_24dp))
//        food.add(Menu("k",R.drawable.ic_personal_video_black_24dp))
//        food.add(Menu("l",R.drawable.ic_personal_video_black_24dp))
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
                tvStats2.setText(statuslist)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                //when removed
            }

        })

        var menuitem = Menu("Choose Type of Drink from below","https://previews.123rf.com/images/vectortatu/vectortatu1608/vectortatu160800094/61333605-hand-drawn-cocktails-set-on-chalkboard-and-text-choose-your-drink-bar-cafe-or-restaurant-vector-illu.jpg")


        var defaullist = ArrayList<Menu>()
        defaullist.add(menuitem)

        var adapter = MenuAdapter(this,defaullist)
        recyclerViewmenu.adapter = adapter
        recyclerViewmenu.setLayoutManager(mGridLayoutManager)
        tvStats2.isSelected = true
    }
}