package com.semba.geodistance.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.semba.geodistance.base.BaseViewModel
import com.semba.geodistance.data.Constants
import com.semba.geodistance.data.models.UserData
import com.semba.geodistance.utils.FileManager
import com.semba.geodistance.utils.rx.AppSchedulerProvider
import com.semba.geodistance.utils.rx.ISchedulerProvider
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject
import kotlin.math.*

/**
 * Created by SeMbA on 2019-10-08.
 */
class MainViewModel @Inject constructor(var mFileManager: FileManager, var mScheduler: ISchedulerProvider) : BaseViewModel<MainNavigator>() {

    var usersLiveData = MutableLiveData<ArrayList<UserData>>()
    private var usersArray = ArrayList<UserData>()

    /**
     * Observe the changes of the users data, in order to notify and refresh the view.
     */
    private val usersObserver = Observer<ArrayList<UserData>> {
        showResult(it)
    }

    init {
        usersLiveData.observeForever(usersObserver)
    }

    override fun executeLogic() {
        if (! checkUsersFile())
        {
            mNavigator?.get()?.showErrorMessage("Users file does not exist")
            return
        }

        isLoading.value = true
        usersArray = loadUsersData()
        performGCDCalculation(usersArray)
    }

    private fun checkUsersFile(): Boolean
    {
        return mFileManager.checkFileExists()
    }

    fun loadUsersData(): ArrayList<UserData>
    {
        val text = mFileManager.readFile()
        return mFileManager.deserializeJsonArray(text)
    }

    /**
     * Executing "Great Circle Distance" Algorithm over the users array on the background thread
     * Reducing additional steps by filtering the data in "onNext" callback
     * Start Loading
     */
    fun performGCDCalculation(users: ArrayList<UserData>)
    {
        mCompositeDisposable.add(Observable.fromIterable(users).doOnNext {
            it.distanceInKM = calculateDistanceFromOffice(it.latitude, it.longitude)
        }.observeOn(mScheduler.ui()).subscribeOn(mScheduler.io()).subscribeWith(
            object : DisposableObserver<UserData>(){
                override fun onComplete() {
                    onCalculationComplete(users)
                }

                override fun onNext(t: UserData) {
                    checkUserDistance(t, users)
                }

                override fun onError(e: Throwable) {
                    mNavigator?.get()?.showErrorMessage(e.message)
                }
            }
        ))
    }

    /**
     * It's called after getting done with all items of the users array
     * Sorts the result data and assigned to the liveData variable
     * Stop Loading
     */
    fun onCalculationComplete(users: ArrayList<UserData>)
    {
        usersLiveData.value = sortUsersData(users)
        isLoading.value = false
    }

    /**
     * Applying "Great Circle Distance" formula on the office's location against single user's location
     */
    fun calculateDistanceFromOffice(latitude: Double, longitude: Double): Int
    {
        val φ1 = Math.toRadians(Constants.OFFICE_LATITUDE)
        val φ2 = Math.toRadians(latitude)
        val Δφ = Math.toRadians(latitude - Constants.OFFICE_LATITUDE)
        val Δλ = Math.toRadians(longitude - Constants.OFFICE_LONGITUDE)

        val c1 = sin(Δφ/2).pow(2)
        val c2 = cos(φ1) * cos(φ2) * (sin(Δλ/2).pow(2))

        val Δσ = 2 * asin(sqrt(c1 + c2))
        val distance = (Constants.Radius * Δσ) / 1000

        return distance.roundToInt()
    }

    /**
     * Sorting users items ascending
     */
    fun sortUsersData(users: ArrayList<UserData>): ArrayList<UserData>
    {
        users.sortBy { it.id }
        return users
    }

    /**
     * Check for the minimum distance, and filter users in case of exceeding the minimum distance
     */
    fun checkUserDistance(userData: UserData, users: ArrayList<UserData>): Boolean
    {
        if (userData.distanceInKM > Constants.MINIMUM_DISTANCE) {
            users.removeAll { it.id == userData.id }
            return true
        }

        return false
    }

    /**
     * Display the updated data and notify the view
     */
    fun showResult(users: ArrayList<UserData>)
    {
        mNavigator?.get()?.validateRecyclerView(users)
    }

    override fun onCleared() {
        usersLiveData.removeObserver(usersObserver)
        super.onCleared()
    }

}