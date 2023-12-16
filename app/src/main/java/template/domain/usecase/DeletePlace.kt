package template.domain.usecase

import template.domain.repository.PlaceRepository


class DeletePlace(private val repository: PlaceRepository) {
    suspend operator fun invoke(id: Long) {
        repository.deletePlace(id)
    }

}
