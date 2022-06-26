package com.losandroides.learn.presentation

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.losandroides.learn.domain.model.Item
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setUp(
        viewModelState: MainViewModel.ViewState
    ) {
        composeTestRule.setContent {
            ItemListScreen(viewModelState = viewModelState)
        }
    }

    @Test
    fun givenLoadingStateThenLoaderIsDisplayed() {
        setUp(MainViewModel.ViewState.Loading)

        composeTestRule.onNodeWithTag("loader").assertIsDisplayed()
    }

    @Test
    fun givenErrorStateThenSnackBarIsDisplayedAndWithTheCorrectText() {
        setUp(MainViewModel.ViewState.Error)
        val errorMessage =
            "There was an error fetching data.\n" +
                    "Please, try again later"

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun givenEmptyItemListThenItemListHasZeroItems() {
        val itemList = emptyList<Item>()
        setUp(MainViewModel.ViewState.Content(itemList))

        composeTestRule.onNodeWithTag("itemList").assertIsDisplayed()
        with(composeTestRule.onNodeWithTag("itemList")) {
            assertIsDisplayed()
            onChildren().assertCountEquals(0)
        }
    }

    @Test
    fun givenItemListWithItemsThenTheSizeIsTheSameOfTheListAndTheDataIsCorrectlyDisplayed() {
        val itemList = listOf(
            Item(
                title = "title1"
            ),
            Item(
                title = "title2"
            ),
            Item(
                title = "title3"
            ),
            Item(
                title = "title4"
            ),
        )
        setUp(MainViewModel.ViewState.Content(itemList))

        composeTestRule.onNodeWithTag("itemList").assertIsDisplayed()
        with(composeTestRule.onNodeWithTag("itemList")) {
            assertIsDisplayed()
            onChildren().assertCountEquals(4)
            for (i in itemList.indices) {
                onChildAt(i).assertTextEquals(
                    itemList[i].title
                )
            }
        }
    }
}