package com.example.cryptoapp.api

import com.example.cryptoapp.pojo.CoinPriceRaw
import com.example.cryptoapp.pojo.DatumList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "cf7fdb1a2fed4decdd60ac7b942871a5682985a97313adfb0a0393dfb188c426",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 20,
        @Query(QUERY_PARAM_SYMBOL) tSym: String = CURRENCY
    ): Single<DatumList>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "cf7fdb1a2fed4decdd60ac7b942871a5682985a97313adfb0a0393dfb188c426",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fromSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) toSyms: String = CURRENCY,
    ): Single<CoinPriceRaw>

    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_SYMBOL = "tsym"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"

        private const val CURRENCY = "USD"
    }
}