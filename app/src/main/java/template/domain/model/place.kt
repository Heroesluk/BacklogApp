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
    val imageFileName: String,
    val latitude: Double,
    val longtitude: Double,
) {
    constructor(name: String, description: String, date: String, score: Int, imageFileName: String, latitude: Double,longtitude: Double)
        : this(0, name, description, date, score, imageFileName, latitude, longtitude)

    constructor(name: String, description: String, date: String, score: Int, imageFileName: String)
        : this(0, name, description, date, score, imageFileName, -1.0, -1.0)

}
