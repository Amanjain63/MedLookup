package com.example.medlookup.data.repository

import com.example.medlookup.data.local.dao.MedicineDao
import com.example.medlookup.data.local.entity.MedicineEntity
import com.example.medlookup.data.remote.ApiService
import com.example.medlookup.data.remote.DrugLabelResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class MedicineRepositoryTest {

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var medicineDao: MedicineDao

    private lateinit var repository: MedicineRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = MedicineRepositoryImpl(apiService, medicineDao)
    }

    @Test
    fun `searchMedicines returns cached data when network fails`() = runTest {
        // Given: API throws exception
        whenever(apiService.searchMedicine(any(), any(), any())).thenThrow(RuntimeException("Network error"))
        
        // And: DB has cached data
        val cachedEntities = listOf(
            MedicineEntity(
                id = "1", brandName = "Cached Advil", genericName = "Ibuprofen",
                manufacturer = "Pfizer", route = "Oral", productType = "OTC",
                purpose = null, indicationsAndUsage = null, dosageAndAdministration = null,
                warnings = null, doNotUse = null, askDoctor = null, askDoctorOrPharmacist = null,
                stopUse = null, pregnancyOrBreastFeeding = null, keepOutOfReachOfChildren = null,
                activeIngredient = null, inactiveIngredient = null, storageAndHandling = null
            )
        )
        whenever(medicineDao.searchMedicines(any())).thenReturn(cachedEntities)

        // When: Searching
        val result = repository.searchMedicines("Advil")

        // Then: Result is success and contains cached data
        assertTrue(result.isSuccess)
        val searchResult = result.getOrNull()
        assertEquals(1, searchResult?.medicines?.size)
        assertEquals("Cached Advil", searchResult?.medicines?.get(0)?.brandName)
        assertTrue(searchResult?.isFromCache == true)
    }

    @Test
    fun `searchMedicines returns failure when both network and cache fail`() = runTest {
        // Given: API throws exception
        whenever(apiService.searchMedicine(any(), any(), any())).thenThrow(RuntimeException("Network error"))
        
        // And: DB is empty
        whenever(medicineDao.searchMedicines(any())).thenReturn(emptyList())

        // When: Searching
        val result = repository.searchMedicines("Advil")

        // Then: Result is failure
        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }
}
