package com.jaewoo.rx.util

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class CustomSubscriber(
    private val isDebug: Boolean = false
) : Subscriber<Any> {
    override fun onSubscribe(s: Subscription) {
        s.request(Long.MAX_VALUE)
    }

    override fun onNext(t: Any) {
        if (isDebug) {
            println("${ThreadUtil.getThreadName()} : " + t)
        } else {
            println(t)
        }
    }

    override fun onError(t: Throwable) {
        println("onError")
    }

    override fun onComplete() {
        println("onComplete")
    }
}