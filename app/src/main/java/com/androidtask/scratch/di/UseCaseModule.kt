package com.androidtask.scratch.di

import com.androidtask.scratch.domain.CardsRepository
import com.androidtask.scratch.domain.usecase.ActivateCardUseCase
import com.androidtask.scratch.domain.usecase.GetCardUseCase
import com.androidtask.scratch.domain.usecase.ScratchCardUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetCardUseCase(
        cardsRepository: CardsRepository
    ): GetCardUseCase {
        return GetCardUseCase(
            cardsRepository
        )
    }

    @Singleton
    @Provides
    fun provideScratchCardUseCase(
        cardsRepository: CardsRepository
    ): ScratchCardUseCase {
        return ScratchCardUseCase(
            cardsRepository
        )
    }

    @Singleton
    @Provides
    fun provideActivateCardUseCase(
        cardsRepository: CardsRepository
    ): ActivateCardUseCase {
        return ActivateCardUseCase(
            cardsRepository
        )
    }
}
