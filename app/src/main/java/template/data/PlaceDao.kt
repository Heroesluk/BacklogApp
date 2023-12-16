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
    fun getPlaceById(id: Long): Place?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertPlace(place: Place): Long

    @Update()
    fun updatePlace(place: Place): Int

    @Query("DELETE FROM PLACE WHERE id = :id")
    fun deletePlace(id: Long)

}
