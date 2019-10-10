package com.semba.geodistance.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.semba.geodistance.R
import com.semba.geodistance.base.BaseViewHolder
import com.semba.geodistance.data.models.UserData
import kotlinx.android.synthetic.main.user_data_item.view.*

/**
 * Created by SeMbA on 2019-10-09.
 */
class UsersAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private var users = ArrayList<UserData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_data_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun updateItems(items: ArrayList<UserData>)
    {
        this.users = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : BaseViewHolder(view)
    {
        private val id: TextView = view.userId
        private val name: TextView = view.name
        private val distance: TextView = view.distance

        override fun onBind(position: Int) {
            val item = users[position]
            id.text = item.id.toString()
            name.text = item.name
            distance.text = "${item.distanceInKM} KM"
        }
    }
}