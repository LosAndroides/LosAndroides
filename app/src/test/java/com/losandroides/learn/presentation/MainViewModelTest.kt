package com.losandroides.learn.presentation

import arrow.core.right
import com.losandroides.learn.domain.ItemsUseCase
import com.losandroides.learn.domain.model.Item
import com.losandroides.learn.framework.coVerifyOnce
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName

@ExperimentalCoroutinesApi
@DisplayName("MainViewModel test")
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val itemsUseCase = mockk<ItemsUseCase>()

    @Test
    @DisplayName("""
        GIVEN some items
        WHEN MainViewModel is initialized
        THEN ItemsUseCase is called
    """)
    fun test() =
        runTest(testDispatcher) {
            val items = buildItems()
            coEvery { itemsUseCase() } returns items.right()

            val viewModel = buildMainViewModel()
            runCurrent()

            coVerifyOnce { itemsUseCase() }
            viewModel.viewState.value shouldBeEqualTo MainViewModel.ViewState.Content(items)
        }

    private fun buildMainViewModel() = MainViewModel(
        itemsUseCase = itemsUseCase,
        dispatcher = testDispatcher,
    )

    private fun buildItems() = listOf(
        Item("Item 1"),
        Item("Item 2"),
        Item("Item 3"),
    )
}
