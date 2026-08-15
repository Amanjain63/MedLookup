package com.example.medlookup.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medlookup.data.local.entity.MedicineEntity

@Dao
interface MedicineDao {
    @Query("SELECT * FROM medicines WHERE brandName LIKE '%' || :query || '%' OR genericName LIKE '%' || :query || '%'")
    suspend fun searchMedicines(query: String): List<MedicineEntity>

    @Query("SELECT * FROM medicines WHERE id = :id")
    suspend fun getMedicineById(id: String): MedicineEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicines(medicines: List<MedicineEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: MedicineEntity)
}
