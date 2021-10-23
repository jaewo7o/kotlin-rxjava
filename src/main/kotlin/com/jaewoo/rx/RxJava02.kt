package com.jaewoo.rx

import com.jaewoo.rx.util.CustomSubscriber
import com.jaewoo.rx.util.ThreadUtil
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class RxJava02 {
    fun fromArrayIterable() {
        val numbers = arrayOf(1, 2, 3, 4, 5, 6)
        //Flowable.fromArray(numbers.toIntArray()).subscribe(CustomSubscriber(true))
        Flowable.fromIterable(numbers.asIterable()).subscribe(CustomSubscriber(true))
    }

    fun fromCallable() {
        Flowable.fromCallable({ "Done" }).subscribe(CustomSubscriber(true))
    }

    fun range() {
        Flowable.range(100, 100).subscribe(CustomSubscriber(true))
    }

    fun interval() {
        Flowable.interval(1, TimeUnit.SECONDS).subscribe(CustomSubscriber(true))
        ThreadUtil.sleep(10)
    }

    fun timer() {
        Flowable.timer(10L, TimeUnit.SECONDS).subscribe(CustomSubscriber(true))
        ThreadUtil.sleep(20)
    }

    fun defer() {
        val list = mutableListOf("1", "2", "3")

        val deferFlowable = Flowable.defer {
            val size = list.size
            Flowable.just(size, size + 1, size + 2)
        }

        deferFlowable.subscribe { println("#1 : $it") }

        list.removeAt(0)
        ThreadUtil.sleep(2, false)

        deferFlowable.subscribe { println("#2 : $it") }

        list.removeAt(0)
        ThreadUtil.sleep(2, false)

        deferFlowable.subscribe { println("#3 : $it") }
    }
}

fun main() {
    val rxJava02 = RxJava02()

    //rxJava02.fromArrayIterable()
    //rxJava02.fromCallable()
    //rxJava02.range()
    //rxJava02.interval()
    //rxJava02.timer()
    rxJava02.defer()
}