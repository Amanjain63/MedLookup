package com.example.medlookup.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("drug/label.json")
    suspend fun searchMedicine(
        @Query("search") search:String,
        @Query("limit") limit:Int=20,
        @Query("skip") skip:Int=20
    ): DrugLabelResponse

    @GET("drug/label.json")
    suspend fun getMedicineById(
        @Query("search") search: String,
        @Query("limit") limit: Int = 1
    ): DrugLabelResponse

}