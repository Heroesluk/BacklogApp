package template.data

import template.domain.model.Place
import template.domain.repository.PlaceRepository

class PlaceRepositoryImplementation(
    private val dao: PlaceDao,
) : PlaceRepository {

    override fun getPlaces(): List<Place> {
        return dao.getPlaces()
    }

    override  fun getPlaceById(id: Int): Place? {
        return dao.getPlaceById(id)
    }
    override  fun insertPlace(place: Place) {
        dao.insertPlace(place)
    }
    override  fun deletePlace(place: Place) {
        dao.deletePlace(place)
    }
}
