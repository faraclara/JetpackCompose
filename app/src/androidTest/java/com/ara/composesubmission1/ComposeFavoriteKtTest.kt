package com.ara.composesubmission1

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.ara.composesubmission1.modeldata.ArtistData
import com.ara.composesubmission1.navigation.Screen
import com.ara.composesubmission1.ui.theme.ComposeSubmission1Theme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ComposeFavoriteKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            ComposeSubmission1Theme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                ComposeFavorite(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("lazy_list").performScrollToIndex(5)
        composeTestRule.onNodeWithText(ArtistData.dummyArtist[5].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailArtist.route)
        composeTestRule.onNodeWithText(ArtistData.dummyArtist[5].name).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }


    @Test
    fun navigateTo_AboutPage() {
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.profile_name).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.profile_email).assertIsDisplayed()
    }

    @Test
    fun searchShowEmptyListArtist() {
        val incorrectSearch = "zz9000"
        composeTestRule.onNodeWithStringId(R.string.search_text).performTextInput(incorrectSearch)
        composeTestRule.onNodeWithTag("emptyList").assertIsDisplayed()
    }


    @Test
    fun searchShowListArtist() {
        val rightSearch = "adele"
        composeTestRule.onNodeWithStringId(R.string.search_text).performTextInput(rightSearch)
        composeTestRule.onNodeWithText("adele").assertIsDisplayed()
    }



    @Test
    fun favoriteClickAndDeleteFavoriteInHome_NotShowInFavoriteScreen() {
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithTag("lazy_list").performScrollToIndex(0)
        composeTestRule.onNodeWithText(ArtistData.dummyArtist[0].name).assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("item_favorite_button").onFirst().performClick()
        composeTestRule.onAllNodesWithTag("item_favorite_button").onFirst().performClick()
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.empty_favorite).assertIsDisplayed()
    }

}