package com.androidtask.scratch

import com.androidtask.scratch.domain.usecase.GetCardUseCase
import com.androidtask.scratch.ui.model.Card
import com.androidtask.scratch.ui.screens.cardroot.CardRootViewModel
import io.mockk.coEvery
import io.mockk.mockk
import java.util.UUID
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
