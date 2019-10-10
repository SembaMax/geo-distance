package com.semba.geodistance.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by SeMbA on 2019-10-08.
 */
data class UserData(
    @SerializedName("user_id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double,
    var distanceInKM : Int
    )