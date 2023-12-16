package template.domain.usecase

import template.domain.model.Place
import template.domain.repository.PlaceRepository


class DeletePlace(private val repository: PlaceRepository) {
    operator fun invoke(id: Long) {
        repository.deletePlace(id)
    }

}
