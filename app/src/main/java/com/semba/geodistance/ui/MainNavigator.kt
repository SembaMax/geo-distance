package com.semba.geodistance.ui

import com.semba.geodistance.base.BaseNavigator
import com.semba.geodistance.data.models.UserData

/**
 * Created by SeMbA on 2019-10-08.
 */
interface MainNavigator: BaseNavigator
{
    fun validateRecyclerView(users: ArrayList<UserData>)
}