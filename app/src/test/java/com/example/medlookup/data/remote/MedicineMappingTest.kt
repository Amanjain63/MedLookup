package com.example.medlookup.data.remote

import org.junit.Assert.assertEquals
import org.junit.Test

class MedicineMappingTest {

    @Test
    fun `mapping messy payload handles missing fields gracefully`() {
        // Given: A messy DTO with many nulls and empty lists
        val messyDto = DrugLabelDto(
            id = "123",
            set_id = null,
            effective_time = null,
            version = null,
            openfda = OpenFdaDto(
                application_number = null,
                brand_name = listOf(""), // Empty brand name
                generic_name = null,      // Missing generic name
                manufacturer_name = listOf("   "), // Blank manufacturer
                product_ndc = null,
                product_type = null,
                route = null,
                substance_name = null,
                rxcui = null,
                spl_id = null,
                spl_set_id = null,
                package_ndc = null,
                is_original_packager = null,
                upc = null,
                nui = null,
                pharm_class_moa = null,
                pharm_class_pe = null,
                pharm_class_cs = null,
                pharm_class_epc = null,
                unii = null
            ),
            spl_product_data_elements = null,
            active_ingredient = null,
            purpose = null,
            indications_and_usage = null,
            warnings = null,
            do_not_use = null,
            ask_doctor = null,
            ask_doctor_or_pharmacist = null,
            stop_use = null,
            pregnancy_or_breast_feeding = null,
            keep_out_of_reach_of_children = null,
            dosage_and_administration = null,
            storage_and_handling = null,
            inactive_ingredient = null,
            questions = null,
            package_label_principal_display_panel = null
        )

        // When: Mapping to domain model
        val medicine = messyDto.toDomainModel()

        // Then: Fallbacks are applied correctly
        assertEquals("123", medicine.id)
        assertEquals("Unknown brand", medicine.brandName)
        assertEquals("Generic name unavailable", medicine.genericName)
        assertEquals("Manufacturer unavailable", medicine.manufacturer)
        assertEquals("Route unavailable", medicine.route)
        assertEquals("Product type unavailable", medicine.productType)
    }

    @Test
    fun `mapping full payload works correctly`() {
        val fullDto = DrugLabelDto(
            id = "456",
            set_id = "set1",
            effective_time = "20230101",
            version = "1",
            openfda = OpenFdaDto(
                brand_name = listOf("Advil"),
                generic_name = listOf("Ibuprofen"),
                manufacturer_name = listOf("Pfizer"),
                product_type = listOf("HUMAN OTC DRUG"),
                route = listOf("ORAL"),
                application_number = null, product_ndc = null, substance_name = null,
                rxcui = null, spl_id = null, spl_set_id = null, package_ndc = null,
                is_original_packager = null, upc = null, nui = null, pharm_class_moa = null,
                pharm_class_pe = null, pharm_class_cs = null, pharm_class_epc = null, unii = null
            ),
            active_ingredient = listOf("Ibuprofen 200mg"),
            purpose = listOf("Pain reliever"),
            indications_and_usage = null, warnings = null, do_not_use = null,
            ask_doctor = null, ask_doctor_or_pharmacist = null, stop_use = null,
            pregnancy_or_breast_feeding = null, keep_out_of_reach_of_children = null,
            dosage_and_administration = null, storage_and_handling = null,
            inactive_ingredient = null, questions = null, package_label_principal_display_panel = null,
            spl_product_data_elements = null
        )

        val medicine = fullDto.toDomainModel()

        assertEquals("Advil", medicine.brandName)
        assertEquals("Ibuprofen", medicine.genericName)
        assertEquals("Pfizer", medicine.manufacturer)
        assertEquals("Pain reliever", medicine.purpose)
        assertEquals("Ibuprofen 200mg", medicine.activeIngredient)
    }
}
