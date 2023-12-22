package template.domain.repository

import kotlinx.coroutines.flow.Flow
import template.domain.model.Place

interface PlaceRepository {

    fun getPlaces(): Flow<List<Place>>

    fun getPlacesList(): List<Place>

    suspend fun getPlaceById(id: Long): Place?

    suspend fun insertPlace(place: Place): Long

    suspend fun deletePlace(id: Long)

}
