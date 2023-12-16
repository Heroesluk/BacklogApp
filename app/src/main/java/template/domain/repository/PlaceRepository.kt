package template.domain.repository

import template.domain.model.Place

interface PlaceRepository {

    fun getPlaces(): List<Place>

    fun getPlaceById(id: Long): Place?

    fun insertPlace(place: Place): Long

    fun deletePlace(id: Long)

}
