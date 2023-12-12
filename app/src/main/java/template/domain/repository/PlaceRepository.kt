package template.domain.repository

import template.domain.model.Place

interface PlaceRepository {

    fun getPlaces(): List<Place>

    fun getPlaceById(id: Int): Place?

    fun insertPlace(place: Place)

    fun deletePlace(place: Place)

}
