package template.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.jupiter.api.Assertions.*

class PlaceRepositoryImplementationTest {
    private lateinit var placeDao: PlaceDao
    private lateinit var db: PlaceDatabase

//    @Before
//    fun createDb() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        db = Room.inMemoryDatabaseBuilder(
//            context, PlaceDatabase::class.java).build()
//        placeDao = db.placeDao
//    }


    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
    }

    @org.junit.jupiter.api.Test
    fun getPlaces() {
    }

    @org.junit.jupiter.api.Test
    fun getPlaceById() {
    }

    @org.junit.jupiter.api.Test
    fun insertPlace() {
    }

    @org.junit.jupiter.api.Test
    fun deletePlace() {
    }
}
