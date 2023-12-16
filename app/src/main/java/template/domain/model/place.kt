package template.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Place(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val date: String,
    val score: Int,
    val locationId: Int,
    val imageFileName: String,
) {
    constructor(name: String, description: String, date: String, score: Int, locationId: Int, imageFileName: String)
        : this(0, name, description, date, score, locationId, imageFileName)

}
