package com.semba.geodistance.base

/**
 * Created by SeMbA on 2019-10-08.
 */
interface BaseNavigator {

    /**
     * Switch between loading mode and normal mode
     */
    fun toggleLoading(isLoading: Boolean)

    /**
     * pass a error message to the activity to display it on the user interface
     */
    fun showErrorMessage(message: String?)
}