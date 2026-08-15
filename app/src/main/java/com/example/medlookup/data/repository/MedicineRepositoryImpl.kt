package com.example.medlookup.data.repository

import com.example.medlookup.data.local.dao.MedicineDao
import com.example.medlookup.data.local.entity.MedicineEntity
import com.example.medlookup.data.remote.ApiService
import com.example.medlookup.domain.model.Medicine
import com.example.medlookup.domain.repository.MedicineRepository

import com.example.medlookup.data.remote.toDomainModel
import com.example.medlookup.domain.repository.SearchResult

class MedicineRepositoryImpl(
    private val apiService: ApiService,
    private val medicineDao: MedicineDao
) : MedicineRepository {

    override suspend fun searchMedicines(
        query: String, limit: Int, skip: Int
    ): Result<SearchResult> {

        return try {

            val response = apiService.searchMedicine(
                search = """openfda.brand_name:"$query"""", limit = limit, skip = skip
            )

            val medicines = response.results.orEmpty().map { it.toDomainModel() }

            // Cache results
            medicineDao.insertMedicines(medicines.map { MedicineEntity.fromDomainModel(it) })

            Result.success(SearchResult(medicines, isFromCache = false))

        } catch (exception: Exception) {
            // Fallback to cache
            val cachedMedicines = medicineDao.searchMedicines(query).map { it.toDomainModel() }
            if (cachedMedicines.isNotEmpty()) {
                Result.success(SearchResult(cachedMedicines, isFromCache = true))
            } else {
                Result.failure(exception)
            }
        }
    }

    override suspend fun getMedicineById(id: String): Result<Medicine> {
        return try {
            val response = apiService.getMedicineById(search = """id:"$id"""")
            val medicine = response.results
                .orEmpty()
                .firstOrNull()
                ?.toDomainModel()

            if (medicine != null) {
                medicineDao.insertMedicine(MedicineEntity.fromDomainModel(medicine))
                Result.success(medicine)
            } else {
                // Try cache if network returns nothing
                val cached = medicineDao.getMedicineById(id)
                cached?.let { Result.success(it.toDomainModel()) }
                    ?: Result.failure(Exception("Medicine not found"))
            }
        } catch (exception: Exception) {
            // Fallback to cache
            val cached = medicineDao.getMedicineById(id)
            cached?.let { Result.success(it.toDomainModel()) }
                ?: Result.failure(exception)
        }
    }
}
