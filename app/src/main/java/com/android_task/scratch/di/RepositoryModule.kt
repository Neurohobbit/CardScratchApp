package com.android_task.scratch.di

import com.android_task.scratch.data.datasource.remote.ApiService
import com.android_task.scratch.data.repository.CardsRepositoryImpl
import com.android_task.scratch.domain.CardsRepository
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
        apiService: ApiService,
    ): CardsRepository {
        return CardsRepositoryImpl(
            apiService
        )
    }
}