package template.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import template.domain.model.Place


@Dao
interface PlaceDao {

    @Query("SELECT * FROM Place")
    fun getPlaces(): List<Place>

    @Query("SELECT * FROM Place WHERE id = :id")
    fun getPlaceById(id: Int): Place?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlace(place: Place)

    @Delete
    fun deletePlace(place: Place)

}
