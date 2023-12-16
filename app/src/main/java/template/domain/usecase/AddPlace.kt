package template.domain.usecase

import template.domain.model.Place
import template.domain.repository.PlaceRepository

class AddPlace(
    private val repository: PlaceRepository,
) {

    operator fun invoke(place: Place): Long {
        return repository.insertPlace(place)
    }

}
