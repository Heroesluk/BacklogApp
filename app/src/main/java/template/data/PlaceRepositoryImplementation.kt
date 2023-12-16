package template.data

import kotlinx.coroutines.flow.Flow
import template.domain.model.Place
import template.domain.repository.PlaceRepository

class PlaceRepositoryImplementation(
    private val dao: PlaceDao,
) : PlaceRepository {

    override fun getPlaces(): Flow<List<Place>> {
        return dao.getPlaces()
    }

    override suspend fun getPlaceById(id: Long): Place? {
        return dao.getPlaceById(id)
    }

    override suspend fun insertPlace(place: Place): Long {
        return dao.insertPlace(place)
    }

    override suspend fun deletePlace(id: Long) {
        dao.deletePlace(id)
    }
}
