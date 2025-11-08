package com.androidtask.scratch.di

import com.androidtask.scratch.data.datasource.remote.ApiService
import com.androidtask.scratch.data.repository.CardsRepositoryImpl
import com.androidtask.scratch.domain.CardsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideCardsRepository(
        apiService: ApiService
    ): CardsRepository {
        return CardsRepositoryImpl(
            apiService
        )
    }
}
