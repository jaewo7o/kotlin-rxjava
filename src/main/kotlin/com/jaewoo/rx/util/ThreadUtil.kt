package com.jaewoo.rx.util

class ThreadUtil {
    companion object {
        fun sleep(sleepSecond: Int, isDebug: Boolean = true) {
            if (isDebug) {
                println("$sleepSecond second time sleep, ${Thread.currentThread().name}")
            }

            Thread.sleep(sleepSecond * 1000L)
        }
    }
}