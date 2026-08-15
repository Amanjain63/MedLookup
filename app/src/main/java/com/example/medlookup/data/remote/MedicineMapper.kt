package com.example.medlookup.data.remote

import com.example.medlookup.domain.model.Medicine

fun DrugLabelDto.toDomainModel(): Medicine {
    return Medicine(
        id = id.orEmpty(),

        brandName = openfda?.brand_name?.firstOrNull().orEmpty().ifBlank { "Unknown brand" },

        genericName = openfda?.generic_name?.firstOrNull().orEmpty()
            .ifBlank { "Generic name unavailable" },

        manufacturer = openfda?.manufacturer_name?.firstOrNull().orEmpty()
            .ifBlank { "Manufacturer unavailable" },

        route = openfda?.route?.firstOrNull().orEmpty().ifBlank { "Route unavailable" },

        productType = openfda?.product_type?.firstOrNull().orEmpty()
            .ifBlank { "Product type unavailable" },

        purpose = purpose?.firstOrNull(),
        indicationsAndUsage = indications_and_usage?.firstOrNull(),
        dosageAndAdministration = dosage_and_administration?.firstOrNull(),
        warnings = warnings?.firstOrNull(),
        doNotUse = do_not_use?.firstOrNull(),
        askDoctor = ask_doctor?.firstOrNull(),
        askDoctorOrPharmacist = ask_doctor_or_pharmacist?.firstOrNull(),
        stopUse = stop_use?.firstOrNull(),
        pregnancyOrBreastFeeding = pregnancy_or_breast_feeding?.firstOrNull(),
        keepOutOfReachOfChildren = keep_out_of_reach_of_children?.firstOrNull(),
        activeIngredient = active_ingredient?.firstOrNull(),
        inactiveIngredient = inactive_ingredient?.firstOrNull(),
        storageAndHandling = storage_and_handling?.firstOrNull()
    )
}
