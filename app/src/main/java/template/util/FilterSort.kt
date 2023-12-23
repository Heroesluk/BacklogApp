package template.util

import template.domain.util.SortBy
import template.domain.util.SortDirection

data class FilterSort(val sortBy: SortBy,
                      val sortDirection: SortDirection
){

}
