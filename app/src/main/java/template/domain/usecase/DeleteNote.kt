package template.domain.usecase

import template.domain.model.Place
import template.domain.repository.PlaceRepository


class DeleteNote(private val repository: PlaceRepository) {
    operator fun invoke(place: Place) {
        repository.insertPlace(place)
    }

}
