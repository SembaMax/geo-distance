package com.semba.geodistance.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Inject
import java.util.Arrays.asList
import android.content.res.AssetManager
import com.semba.geodistance.data.models.UserData
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by SeMbA on 2019-10-08.
 */
class FileManager @Inject constructor(private val context: Context) {

    private val fileName = "customers.txt"
    private val gson: Gson

    init {
        //handle Gson options if have any.
        val gsonBuilder = GsonBuilder()
        gson = gsonBuilder.create()
    }

    /**
     * Check if the file was available
     */
    fun checkFileExists(): Boolean
    {
        val am = context.assets
        val mapList = am.list("")
        return mapList?.contains(fileName) ?: false
    }

    /**
     * Read the file from asset folder and convert it into array of data
     */
    fun readFile(): String
    {
        val jsonString = context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }

        val validatedJson = jsonString.replace("\n",",")
        return "[$validatedJson]"
    }

    /**
     * Gets out users' data array out of a json string
     */
    fun deserializeJsonArray(jsonArray: String): ArrayList<UserData>
    {
        val users = gson.fromJson(jsonArray, Array<UserData>::class.java).toList()
        return ArrayList(users)
    }

}