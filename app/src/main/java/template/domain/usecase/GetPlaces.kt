package template.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import template.domain.model.Place
import template.domain.repository.PlaceRepository
import template.domain.util.SortBy
import template.domain.util.SortDirection
import template.util.FilterSort

class GetPlaces(
    private val repository: PlaceRepository,
) {

    operator fun invoke(filterSort: FilterSort): Flow<List<Place>> {

        val sortBy = filterSort.sortDirection.toString();

        // TODO: filter: score higher then value, lower then value, date higher/lower then, name contain string, description contain string
//        val flowFiltered = repository.getPlaces().map { place -> place.filter { it.score > 3 } }
        val flowFiltered = repository.getPlaces()
        // sort:

        if (filterSort.sortBy == SortBy.SCORE && filterSort.sortDirection == SortDirection.ASC) {
            return flowFiltered.map { places ->
                places.sortedBy { it.score }
            }
        } else if (filterSort.sortBy == SortBy.SCORE && filterSort.sortDirection == SortDirection.DESC) {
            return flowFiltered.map { places ->
                places.sortedByDescending { it.score }
            }
        } else if (filterSort.sortBy == SortBy.NAME && filterSort.sortDirection == SortDirection.ASC) {
            return flowFiltered.map { places ->
                places.sortedBy { it.name.lowercase() }
            }
        } else if (filterSort.sortBy == SortBy.NAME && filterSort.sortDirection == SortDirection.DESC) {
            return flowFiltered.map { places ->
                places.sortedByDescending { it.name.lowercase() }
            }
        } else if (filterSort.sortBy == SortBy.DEFAULT && filterSort.sortDirection == SortDirection.ASC) {
            return flowFiltered.map { places ->
                places.sortedBy { it.id }
            }
        } else if (filterSort.sortBy == SortBy.DEFAULT && filterSort.sortDirection == SortDirection.DESC) {
            return flowFiltered.map { places ->
                places.sortedByDescending { it.id }
            }
        }


        return flowFiltered
    }


}
