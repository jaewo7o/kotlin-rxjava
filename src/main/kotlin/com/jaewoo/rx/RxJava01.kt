package com.jaewoo.rx

import com.jaewoo.rx.util.ThreadUtil
import io.reactivex.Flowable
import io.reactivex.Observable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit

class RxJava01 {
    fun basicConcept01() {
        // 1. 배압처리 가능
        // 데이터의 발행 속도가 구독자의 처리속도보다 크게 빠를 때 사용 (BackPressure Issue)
        println("==========> Flowable CASE 1")
        val integerFlowable = Flowable.just(1, 2, 3, 4, 5, 6)
        integerFlowable.subscribe(::println)
        println()

        println("==========> Flowable CASE 2")
        integerFlowable.subscribe(
            { println("2 : $it") },
            { it.printStackTrace() },
            { println("Complete") }
        )
        println()

        // 2. 배압처리 안됨
        println("==========> Observable")
        val integerObservable = Observable.just(1, 2, 3, 4, 5, 6)
        integerObservable.subscribe(::println)
    }

    fun coldHot02(isCold: Boolean) {
        if (isCold) {
            println("### COLD FLOWABLE ###")
            val flowable = Flowable.interval(1, TimeUnit.SECONDS)
            flowable.subscribe({ println("1 : $it") })
            ThreadUtil.sleep(3)

            flowable.subscribe({ println("2 : $it") })
            ThreadUtil.sleep(3)
        } else {
            println("### HOT FLOWABLE ###")
            val connectFlowable = Flowable.interval(1, TimeUnit.SECONDS)
                .doOnNext({ ThreadUtil.sleep(1) })
                .publish()
            connectFlowable.connect()

            connectFlowable.subscribe({ println("1 : $it") })
            ThreadUtil.sleep(3)

            connectFlowable.subscribe({ println("2 : $it") })
            ThreadUtil.sleep(3)
        }
    }

    fun disposable03() {
        val flowable = Flowable.interval(1, TimeUnit.SECONDS)
        val disposable = flowable.subscribe { println("subscription : $it") }

        ThreadUtil.sleep(3)
        disposable.dispose()
        ThreadUtil.sleep(1)
    }

    fun newSubscriber04() {
        val subscriber = object : Subscriber<Int> {
            override fun onSubscribe(s: Subscription) {
                println("onSubscribe : $s")
                s.request(Long.MAX_VALUE)
            }

            override fun onNext(t: Int?) {
                println("onNext : $t")
            }

            override fun onError(t: Throwable?) {
                println("onError : $t")
            }

            override fun onComplete() {
                println("onComplete")
            }
        }
        Flowable.just(1, 2, 3, 4, 5)
            .doOnNext {
                println(Thread.currentThread().name)
            }
            .subscribe(subscriber)
    }
}

fun main() {
    val rxJava01 = RxJava01()
    //rxJava01.basicConcept01()

    //rxJava01.coldHot02(true)
    //rxJava01.coldHot02(false)
    //rxJava01.disposable03()
    rxJava01.newSubscriber04()
}