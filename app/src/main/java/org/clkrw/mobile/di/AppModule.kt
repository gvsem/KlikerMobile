package org.clkrw.mobile.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.clkrw.mobile.data.api.ClickerApi
import org.clkrw.mobile.data.auth.AuthServiceImpl
import org.clkrw.mobile.data.repository.ShowRepositoryImpl
import org.clkrw.mobile.domain.auth.AuthService
import org.clkrw.mobile.domain.repository.ShowRepository
import retrofit2.Retrofit
import javax.inject.Singleton

private val json = Json {
    ignoreUnknownKeys = true
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesBaseUrl(): String = "https://clkr.me"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideClickerApi(retrofit: Retrofit): ClickerApi = retrofit.create(ClickerApi::class.java)

    @Provides
    fun provideAuthService(@ApplicationContext appContext: Context): AuthService = AuthServiceImpl(appContext)

    @Provides
    @Singleton
    fun provideNoteDatabase(clickerApi: ClickerApi, authService: AuthService): ShowRepository =
        ShowRepositoryImpl(clickerApi, authService)
}
