package com.mionix.baseapp.di

import com.mionix.newsapp.api.ApiWebService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

//        Headers headers = new Headers.Builder()
//            .add("Authorization", "auth-value")
//            .add("User-Agent", "you-app-name")
//            .build();

        val originalRequest = chain.request()
        val url = chain.request().url.newBuilder()
            .addQueryParameter("apiKey", "0483ebe735d044778a85fff2dddb9706")
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(url)
            //thêm một header với name và value.
//            .addHeader("x-rapidapi-host", "love-calculator.p.rapidapi.com")
//            .addHeader("x-rapidapi-key", "8bf24d885dmsh361c7b9d5edac3ep126851jsn02d9830d7abf")

//            .cacheControl(CacheControl.FORCE_CACHE) //  Đặt kiểm soát header là của request này, replace lên mọi header đã có.
//            .headers(headers) //Removes all headers on this builder and adds headers.
//            .method(originalRequest.method) // Adds request method and request body
//            .removeHeader("Authorization") // Removes all the headers with this name
            .build()

//        val newRequest = chain.request()
//            .newBuilder()
//            .url(newUrl)
//            .build()


        return chain.proceed(newRequest)
    }
}


val retrofitModule = module {
    single { provideHttpClient() }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
}


fun provideHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = if (BuildConfig.DEBUG)
        HttpLoggingInterceptor.Level.BODY
    else
        HttpLoggingInterceptor.Level.NONE
    return OkHttpClient().newBuilder()
        .addInterceptor(logging)
        .addInterceptor(AuthInterceptor())
        .build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}

fun provideApiService(retrofit: Retrofit): ApiWebService {
    return retrofit.create(ApiWebService::class.java)
}