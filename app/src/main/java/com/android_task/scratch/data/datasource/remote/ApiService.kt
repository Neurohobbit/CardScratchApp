package com.android_task.scratch.data.datasource.remote

import com.android_task.scratch.data.model.ActivationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("version")
    suspend fun activateCode(
        @Query("code") code: String
    ): Response<ActivationResponse>
}