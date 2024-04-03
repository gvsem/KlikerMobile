package org.clkrw.mobile.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
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

class AuthAdder(
    private val authService: AuthService,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
            .newBuilder()
            .header("X-Auth", authService.getAuthToken() ?: error("Expect auth token"))
            .build()
        return chain.proceed(newRequest)
    }

}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesBaseUrl(): String = "https://clkr.me"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, authService: AuthService): Retrofit {
        val httpClient = OkHttpClient().newBuilder().addInterceptor(AuthAdder(authService)).build()
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideClickerApi(retrofit: Retrofit): ClickerApi = retrofit.create(ClickerApi::class.java)

    @Provides
    fun provideAuthService(@ApplicationContext appContext: Context): AuthService =
        AuthServiceImpl(appContext)

    @Provides
    @Singleton
    fun provideShowRepository(clickerApi: ClickerApi): ShowRepository =
        ShowRepositoryImpl(clickerApi)
}
