package com.kadircetin.cryptotracker.service

import com.kadircetin.cryptotracker.model.CoinMarketCapResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface CryptoAPI {
    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getData( @Header("X-CMC_PRO_API_KEY")apiKey: String): CoinMarketCapResponse
}