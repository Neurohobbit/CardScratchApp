package com.android_task.scratch

import com.android_task.scratch.domain.usecase.GetCardUseCase
import com.android_task.scratch.ui.model.Card
import com.android_task.scratch.ui.screens.card_root.CardRootViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CardRootViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getCardUseCase: GetCardUseCase
    private lateinit var viewModel: CardRootViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCardUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState updates when getCardUseCase emits a card`() = runTest {
        val card = Card(id = UUID.randomUUID(), isActivated = true)
        coEvery { getCardUseCase() } returns flow { emit(card) }

        viewModel = CardRootViewModel(getCardUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(card.id, uiState.cardUUID)
        assertEquals(card.isActivated, uiState.activated)
    }
}