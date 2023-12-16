package template.domain.usecase

import kotlinx.coroutines.flow.Flow
import template.domain.model.Place
import template.domain.repository.PlaceRepository

class GetPlaces(
    private val repository: PlaceRepository,
) {

    operator fun invoke(): Flow<List<Place>> {
        return repository.getPlaces()
    }

}
