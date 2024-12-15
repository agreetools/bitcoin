package com.agreetools.shared

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BitcoinApi {
    @GET("simple/price?ids=bitcoin&vs_currencies=usd")
    suspend fun getBitcoinPrice(): Map<String, Map<String, Double>>
}

object BitcoinPriceFetcher {
    private const val BASE_URL = "https://api.coingecko.com/api/v3/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(BitcoinApi::class.java)

    suspend fun fetchCurrentPrice(): Double {
        return try {
            val response = api.getBitcoinPrice()
            response["bitcoin"]?.get("usd") ?: 0.0
        } catch (e: Exception) {
            e.printStackTrace()
            0.0 // Return 0.0 if there is an error
        }
    }
}

object ExampleUtils {
    fun greet(): String {
        return "Hello from the Shared Module!"
    }
}
