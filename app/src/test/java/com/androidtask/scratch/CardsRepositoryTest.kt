package com.androidtask.scratch

import com.androidtask.scratch.data.datasource.remote.ApiService
import com.androidtask.scratch.data.model.ActivationResponse
import com.androidtask.scratch.data.model.CardModel
import com.androidtask.scratch.data.repository.CardsRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CardsRepositoryTest {
    private val apiService: ApiService = mockk()
    private lateinit var repository: CardsRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = CardsRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `scratchCard updates id and resets isScratchingNow`() = runTest {
        val initialId = repository.card.value.id

        repository.scratchCard()
        advanceUntilIdle()

        val newCard = repository.card.value

        assertNotEquals(initialId, newCard.id)
        assertFalse(newCard.isScratchingNow)
    }

    @Test
    fun `scratchCard sets isScratchingNow to true during process`() = runTest {
        val job = launch {
            repository.scratchCard()
        }

        advanceTimeBy(1000L)
        assertTrue(repository.card.value.isScratchingNow)

        advanceUntilIdle()
        job.join()
        assertFalse(repository.card.value.isScratchingNow)
    }

    @Test
    fun `activateCode emits failure when card id is null`() = runTest {
        val result = repository.activateCode().first()
        assertTrue(result.isFailure)
        assertEquals("Card ID is null", result.exceptionOrNull()?.message)
    }

    @Test
    fun `activateCode emits success when response is valid`() = runTest {
        val cardWithId = CardModel(id = UUID.randomUUID())
        setCardValue(cardWithId)

        val fakeResponse = Response.success(ActivationResponse(android = "100"))
        coEvery { apiService.activateCode(any()) } returns fakeResponse

        val result = repository.activateCode().first()

        assertTrue(result.isSuccess)
        assertTrue(repository.card.value.isActivated)
        coVerify { apiService.activateCode(cardWithId.id.toString()) }
    }

    @Test
    fun `activateCode emits failure when response body is null`() = runTest {
        val cardWithId = CardModel(id = UUID.randomUUID())
        setCardValue(cardWithId)

        val response = mockk<Response<ActivationResponse>> {
            every { isSuccessful } returns true
            every { body() } returns null
            every { code() } returns 200
        }
        coEvery { apiService.activateCode(any()) } returns response

        val result = repository.activateCode().first()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()!!.message!!.contains("Empty response body"))
    }

    @Test
    fun `activateCode emits failure when response is unsuccessful`() = runTest {
        val cardWithId = CardModel(id = UUID.randomUUID())
        setCardValue(cardWithId)

        val response = mockk<Response<ActivationResponse>> {
            every { isSuccessful } returns false
            every { body() } returns ActivationResponse(android = "100")
            every { code() } returns 500
        }
        coEvery { apiService.activateCode(any()) } returns response

        val result = repository.activateCode().first()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()!!.message!!.contains("Server error"))
    }

    @Test
    fun `activateCode emits failure when android code greater than ERROR_BORDER`() = runTest {
        val cardWithId = CardModel(id = UUID.randomUUID())
        setCardValue(cardWithId)

        val response =
            Response.success(
                ActivationResponse(android = (CardsRepositoryImpl.ERROR_BORDER + 1).toString())
            )
        coEvery { apiService.activateCode(any()) } returns response

        val result = repository.activateCode().first()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()!!.message!!.contains("more than"))
        assertFalse(repository.card.value.isActivated)
    }

    private fun setCardValue(value: CardModel) {
        val field = repository.javaClass.getDeclaredField("_card")
        field.isAccessible = true
        val stateFlow = field.get(repository) as MutableStateFlow<CardModel>
        stateFlow.value = value
    }
}
