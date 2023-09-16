package com.kheloyar.livegame.di

import android.media.MediaPlayer
import com.google.gson.GsonBuilder
import com.kheloyar.livegame.R
import com.kheloyar.livegame.data.network.KheloyarNullOnEmptyConverterFactory
import com.kheloyar.livegame.data.network.KheloyarRemoteApi
import com.kheloyar.livegame.data.network.KheloyarRepository
import com.kheloyar.livegame.domain.KheloyarUseCase
import com.kheloyar.livegame.presentation.game.GameKheloyarViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val kheloyarModule = module {

    single {
        val interseptorKheloyar = HttpLoggingInterceptor()
        interseptorKheloyar.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(interseptorKheloyar).build()
    }

    single {
        val gsonKheloyar = GsonBuilder().setLenient().create()
        Retrofit.Builder()
            .baseUrl("https://site.com")
            .addConverterFactory(KheloyarNullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gsonKheloyar))
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(KheloyarRemoteApi::class.java)
    }
    single {
        MediaPlayer.create(androidContext(), R.raw.kheloyar_music)
    }
    single { KheloyarUseCase(get()) }
    single { KheloyarRepository(get()) }
    viewModel { GameKheloyarViewModel() }
}