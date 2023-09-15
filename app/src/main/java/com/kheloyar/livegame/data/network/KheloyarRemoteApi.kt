package com.kheloyar.livegame.data.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url


interface KheloyarRemoteApi {

    @GET
    suspend fun getLinkForAppKheloyar(
        @Url url: String
    ): ResponseBody
}