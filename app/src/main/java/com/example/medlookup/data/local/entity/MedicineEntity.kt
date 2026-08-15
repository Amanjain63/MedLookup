package com.example.medlookup.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.medlookup.domain.model.Medicine

@Entity(tableName = "medicines")
data class MedicineEntity(
    @PrimaryKey val id: String,
    val brandName: String,
    val genericName: String,
    val manufacturer: String,
    val route: String,
    val productType: String,
    val purpose: String?,
    val indicationsAndUsage: String?,
    val dosageAndAdministration: String?,
    val warnings: String?,
    val doNotUse: String?,
    val askDoctor: String?,
    val askDoctorOrPharmacist: String?,
    val stopUse: String?,
    val pregnancyOrBreastFeeding: String?,
    val keepOutOfReachOfChildren: String?,
    val activeIngredient: String?,
    val inactiveIngredient: String?,
    val storageAndHandling: String?,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun toDomainModel(): Medicine {
        return Medicine(
            id = id,
            brandName = brandName,
            genericName = genericName,
            manufacturer = manufacturer,
            route = route,
            productType = productType,
            purpose = purpose,
            indicationsAndUsage = indicationsAndUsage,
            dosageAndAdministration = dosageAndAdministration,
            warnings = warnings,
            doNotUse = doNotUse,
            askDoctor = askDoctor,
            askDoctorOrPharmacist = askDoctorOrPharmacist,
            stopUse = stopUse,
            pregnancyOrBreastFeeding = pregnancyOrBreastFeeding,
            keepOutOfReachOfChildren = keepOutOfReachOfChildren,
            activeIngredient = activeIngredient,
            inactiveIngredient = inactiveIngredient,
            storageAndHandling = storageAndHandling
        )
    }

    companion object {
        fun fromDomainModel(medicine: Medicine): MedicineEntity {
            return MedicineEntity(
                id = medicine.id,
                brandName = medicine.brandName,
                genericName = medicine.genericName,
                manufacturer = medicine.manufacturer,
                route = medicine.route,
                productType = medicine.productType,
                purpose = medicine.purpose,
                indicationsAndUsage = medicine.indicationsAndUsage,
                dosageAndAdministration = medicine.dosageAndAdministration,
                warnings = medicine.warnings,
                doNotUse = medicine.doNotUse,
                askDoctor = medicine.askDoctor,
                askDoctorOrPharmacist = medicine.askDoctorOrPharmacist,
                stopUse = medicine.stopUse,
                pregnancyOrBreastFeeding = medicine.pregnancyOrBreastFeeding,
                keepOutOfReachOfChildren = medicine.keepOutOfReachOfChildren,
                activeIngredient = medicine.activeIngredient,
                inactiveIngredient = medicine.inactiveIngredient,
                storageAndHandling = medicine.storageAndHandling
            )
        }
    }
}
