package com.semba.geodistance.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.semba.geodistance.R

/**
 * Created by SeMbA on 2019-10-09.
 */
class MarginItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private var margin = 0

    init {
        margin = context.resources.getDimensionPixelSize(R.dimen.user_item_margin)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(margin, margin, margin, margin)
    }
}