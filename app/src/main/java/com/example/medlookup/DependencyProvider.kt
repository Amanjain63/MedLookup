package com.example.medlookup

import android.content.Context
import com.example.medlookup.data.local.db.AppDatabase
import com.example.medlookup.data.remote.RetrofitInstance
import com.example.medlookup.data.repository.MedicineRepositoryImpl
import com.example.medlookup.domain.repository.MedicineRepository

object DependencyProvider {
    private var repository: MedicineRepository? = null

    fun provideMedicineRepository(context: Context): MedicineRepository {
        return repository ?: synchronized(this) {
            val database = AppDatabase.getDatabase(context)
            val newRepo = MedicineRepositoryImpl(
                apiService = RetrofitInstance.apiService,
                medicineDao = database.medicineDao()
            )
            repository = newRepo
            newRepo
        }
    }
}
