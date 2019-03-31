package com.sashrika.barexchange

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.table_item_row.view.*

class TableAdapter(private val context: Context, private val tableno: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentUser = tableno[position]
        with(holder?.itemView) {
            this!!.tvTitle.setText(currentUser)
            this!!.ivImage.setImageResource(com.sashrika.barexchange.R.drawable.ic_personal_video_black_24dp)
        }
        val inflatedView = LayoutInflater.from(context).inflate(R.layout.alert_custom, null, false)

        val customAlert: AlertDialog = AlertDialog.Builder(context)
            .setView(inflatedView)
            .setPositiveButton("Order") { dialog, which ->
                //Save this to the DB
                val intent = Intent(context,MenuActivity::class.java)
                intent.putExtra("Table No",currentUser)

                context.startActivity(intent)

                dialog.dismiss()
            }
            .create()
        holder?.itemView.setOnClickListener {
            customAlert.show()
        }
    }


    inner class holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): TableAdapter.holder {
        val layoutInflater = LayoutInflater.from(container.context)
        val view = layoutInflater.inflate(R.layout.table_item_row, container, false)
        return holder(view)
    }

    override fun getItemCount() = tableno.size
}

