package template.util


data class Place(val text: String)

data class Contents(val role: String, val parts: List<Place>)

data class Output(val contents: Contents)
