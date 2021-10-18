package com.jaewoo.rx

import io.reactivex.Flowable
import io.reactivex.Observable

class RxJava01 {
    fun basicConcept01() {
        // 1. 배압처리 가능
        // 데이터의 발행 속도가 구독자의 처리속도보다 크게 빠를 때 사용 (BackPressure Issue)
        println("==========> Flowable")
        val integerFlowable = Flowable.just(1, 2, 3, 4, 5, 6)
        integerFlowable.subscribe(::println)

        integerFlowable.subscribe(
            { println("2 : $it") }
        )

        // 2. 배압처리 안됨
        println("==========> Observable")
        val integerObservable = Observable.just(1, 2, 3, 4, 5, 6)
        integerObservable.subscribe(::println)
    }
}

fun main() {
    val rxJava01 = RxJava01()
    rxJava01.basicConcept01()
}