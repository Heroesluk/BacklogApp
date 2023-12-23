package template.domain.repository

import kotlinx.coroutines.flow.Flow
import template.domain.model.Place

interface PlaceRepository {

    fun getPlaces(): Flow<List<Place>>
    fun getPlacesSorted(column: String, order: String): Flow<List<Place>>

    suspend fun getPlaceById(id: Long): Place?

    suspend fun insertPlace(place: Place): Long

    suspend fun deletePlace(id: Long)

}
