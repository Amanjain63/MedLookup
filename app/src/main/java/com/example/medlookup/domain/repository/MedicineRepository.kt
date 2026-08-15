package com.example.medlookup.domain.repository
import com.example.medlookup.domain.model.Medicine

data class SearchResult(
    val medicines: List<Medicine>,
    val isFromCache: Boolean
)

interface MedicineRepository {

    suspend fun searchMedicines(
        query: String,
        limit: Int = 20,
        skip: Int = 0
    ): Result<SearchResult>

    suspend fun getMedicineById(
        id: String
    ): Result<Medicine>
}