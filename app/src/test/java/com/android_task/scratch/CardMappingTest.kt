package com.android_task.scratch

import com.android_task.scratch.data.model.CardModel
import com.android_task.scratch.ui.model.Card
import com.android_task.scratch.ui.model.toCard
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

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