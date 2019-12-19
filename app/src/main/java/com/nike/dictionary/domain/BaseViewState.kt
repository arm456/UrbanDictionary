package com.nike.dictionary.domain

open class BaseViewState<T> {
    var data: T
        get() {
            TODO()
        }
        set(value) {}

    lateinit var error: Throwable
    var currentState: Int = 0

    enum class State constructor(var value: Int) {
        LOADING(0), SUCCESS(1), FAILED(-1)
    }
}