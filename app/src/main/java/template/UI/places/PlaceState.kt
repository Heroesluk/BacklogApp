package template.UI.places

import template.domain.model.Place
import template.domain.util.SortBy
import template.domain.util.SortDirection

data class PlaceState(
    val places: List<Place> = emptyList(),
    val orderBy: SortBy = SortBy.DEFAULT,
    val orderDirection: SortDirection = SortDirection.DESC,

    )


