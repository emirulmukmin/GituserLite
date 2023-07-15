package com.dicoding.gituserlite.ui.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.gituserlite.R
import com.dicoding.gituserlite.utils.EspressoIdlingResource
import com.dicoding.gituserlite.adapter.ListUsersAdapter
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testComponentShowCorrectly() {
        onView(withId(R.id.rvUsers)).check(matches(isDisplayed()))
    }

    @Test
    fun testSelectFirstUser() {
        onView(withId(R.id.rvUsers)).check(matches(isDisplayed()))
        onView(withId(R.id.rvUsers)).perform(
            actionOnItemAtPosition<ListUsersAdapter.ListViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.user_detail_container)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add)).check(matches(isDisplayed()))
    }

    /*@Test
    fun testAddUserToFavorite() {
        onView(withId(R.id.rvUsers)).check(matches(isDisplayed()))
        onView(withId(R.id.rvUsers)).perform(
            actionOnItemAtPosition<ListUsersAdapter.ListViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.user_detail_container)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add)).perform(click())

        onView(isRoot()).perform(pressBack())

        onView(withId(R.id.favorite_page)).perform(click())
        onView(withId(R.id.rv_favorite)).check(matches(isDisplayed()))
    }

    @Test
    fun testDeleteUserFromFavorite() {
        onView(withId(R.id.favorite_page)).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_page)).perform(click())

        onView(withId(R.id.rv_favorite)).perform(
            actionOnItemAtPosition<ListUsersAdapter.ListViewHolder>(0, click())
        )

        onView(withId(R.id.user_detail_container)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add)).perform(click())

        onView(isRoot()).perform(pressBack())
    }*/
}