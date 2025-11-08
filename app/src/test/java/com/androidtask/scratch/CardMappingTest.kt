package com.androidtask.scratch

import com.androidtask.scratch.data.model.CardModel
import com.androidtask.scratch.ui.model.Card
import com.androidtask.scratch.ui.model.toCard
import java.util.UUID
import org.junit.Assert.assertEquals
import org.junit.Test

class CardMappingTest {

    @Test
    fun `toCard maps CardModel fields correctly`() {
        val uuid = UUID.randomUUID()
        val model = CardModel(
            id = uuid,
            isActivated = true,
            isScratchingNow = true
        )

        val card: Card = model.toCard()

        assertEquals(uuid, card.id)
        assertEquals(true, card.isActivated)
        assertEquals(true, card.isScratchingNow)
    }

    @Test
    fun `toCard handles null ID`() {
        val model = CardModel(
            id = null,
            isActivated = false,
            isScratchingNow = false
        )

        val card: Card = model.toCard()

        assertEquals(null, card.id)
        assertEquals(false, card.isActivated)
        assertEquals(false, card.isScratchingNow)
    }
}
