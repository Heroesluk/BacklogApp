package template.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import template.domain.model.Place


@Dao
interface PlaceDao {

    @Query("SELECT * FROM Place")
    fun getPlaces(): List<Place>

    @Query("SELECT * FROM Place WHERE id = :id")
    fun getPlaceById(id: Int): Place?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertPlace(place: Place)

    @Update()
    fun updatePlace(place: Place)

    @Delete
    fun deletePlace(place: Place)

}
