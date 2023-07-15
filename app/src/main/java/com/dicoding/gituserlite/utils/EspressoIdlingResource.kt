package com.dicoding.gituserlite.utils

import androidx.test.espresso.idling.CountingIdlingResource


object EspressoIdlingResource {
    val countingIdlingResource = CountingIdlingResource("GLOBAL")

    fun increment() = countingIdlingResource.increment()

    fun decrement() = countingIdlingResource.decrement()
}