package template.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import template.domain.model.Place


@Dao
interface PlaceDao {

    @Query("SELECT * FROM Place")
    fun getPlaces(): Flow<List<Place>>

    @Query("SELECT * FROM Place WHERE id = :id")
    suspend fun getPlaceById(id: Long): Place?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPlace(place: Place): Long

    @Update()
    suspend fun updatePlace(place: Place): Int

    @Query("DELETE FROM PLACE WHERE id = :id")
    suspend fun deletePlace(id: Long)

}
