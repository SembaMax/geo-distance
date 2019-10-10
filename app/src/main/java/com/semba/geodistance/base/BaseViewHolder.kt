package com.semba.geodistance.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by SeMbA on 2019-10-09.
 */
abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /**
     * Base bind function, which gets the item's position.
     */
    abstract fun onBind(position: Int)
}