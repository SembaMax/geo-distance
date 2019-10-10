package com.semba.geodistance.utils.rx

import io.reactivex.Scheduler

/**
 * Created by SeMbA on 2019-10-08.
 */
interface ISchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler

    fun newThread(): Scheduler

    fun single(): Scheduler

}