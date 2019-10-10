package com.semba.geodistance.utils.rx

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Created by SeMbA on 2019-10-10.
 */
class TestSchedulerProvider: ISchedulerProvider {

    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun newThread(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun single(): Scheduler {
        return Schedulers.trampoline()
    }
}