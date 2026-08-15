package com.example.medlookup.data.remote

data class DrugLabelResponse(
    val meta: MetaDto?,
    val results: List<DrugLabelDto>?
)

data class MetaDto(
    val results: MetaResultsDto?
)

data class MetaResultsDto(
    val skip: Int?,
    val limit: Int?,
    val total: Int?
)

data class DrugLabelDto(
    val id: String?,
    val set_id: String?,
    val effective_time: String?,
    val version: String?,

    val openfda: OpenFdaDto?,

    val spl_product_data_elements: List<String>?,

    val active_ingredient: List<String>?,
    val purpose: List<String>?,
    val indications_and_usage: List<String>?,
    val warnings: List<String>?,
    val do_not_use: List<String>?,
    val ask_doctor: List<String>?,
    val ask_doctor_or_pharmacist: List<String>?,
    val stop_use: List<String>?,
    val pregnancy_or_breast_feeding: List<String>?,
    val keep_out_of_reach_of_children: List<String>?,
    val dosage_and_administration: List<String>?,
    val storage_and_handling: List<String>?,
    val inactive_ingredient: List<String>?,
    val questions: List<String>?,
    val package_label_principal_display_panel: List<String>?
)

data class OpenFdaDto(
    val application_number: List<String>?,
    val brand_name: List<String>?,
    val generic_name: List<String>?,
    val manufacturer_name: List<String>?,
    val product_ndc: List<String>?,
    val product_type: List<String>?,
    val route: List<String>?,
    val substance_name: List<String>?,
    val rxcui: List<String>?,
    val spl_id: List<String>?,
    val spl_set_id: List<String>?,
    val package_ndc: List<String>?,
    val is_original_packager: List<Boolean>?,
    val upc: List<String>?,
    val nui: List<String>?,
    val pharm_class_moa: List<String>?,
    val pharm_class_pe: List<String>?,
    val pharm_class_cs: List<String>?,
    val pharm_class_epc: List<String>?,
    val unii: List<String>?
)