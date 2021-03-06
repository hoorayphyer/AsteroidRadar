package com.udacity.asteroidradar.network

import com.squareup.moshi.Json


data class NasaJson(
    val links : Map<String, String>,
    val element_count : Int,
    val near_earth_objects : Map<String, List<IndividualAsteroidInfo>>
) {
    data class IndividualAsteroidInfo (
        val links : Map<String, String>,
        val id : Long,
        val neo_reference_id : Long,
        val name : String,
        val nasa_jpl_url : String,
        val absolute_magnitude_h : Double,
        val estimated_diameter : Map<String, EstimatedDiameter>,
        val is_potentially_hazardous_asteroid : Boolean,
        val close_approach_data : List<CloseApproachDatum>,
        val is_sentry_object : Boolean
    ) {
        data class EstimatedDiameter (
            @Json(name="estimated_diameter_min")val min : Double,
            @Json(name="estimated_diameter_max")val max : Double)

        data class CloseApproachDatum (
            @Json(name="close_approach_date") val date : String,
            @Json(name="close_approach_date_full") val date_full : String,
            @Json(name="epoch_date_close_approach") val epoch : Long,
            val relative_velocity : Map<String, Double>,
            val miss_distance : Map<String, Double>,
            val orbiting_body : String
        )
    }
}

data class PictureOfDay (
    val date : String,
    val explanation : String,
    val hdurl : String,
    val media_type : String,
    val service_version : String,
    val title : String,
    val url : String
)