package template.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import template.domain.model.Place
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var placeDao: PlaceDao
    private lateinit var db: PlaceDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, PlaceDatabase::class.java,
        ).build()
        placeDao = db.placeDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertPlace_createPlace() {
        placeDao.insertPlace(Place("ok", "ok", "ok", 1, 1, "aaa"))
        placeDao.insertPlace(Place("new", "ok", "ok", 1, 1, "aaa"))

        Assert.assertEquals(2, placeDao.getPlaces().size)

    }

    @Test
    @Throws(Exception::class)
    fun deletePlace_deletePlace() {
        Assert.assertEquals(0, placeDao.getPlaces().size)


        val placeId = placeDao.insertPlace(Place("sex", "ok", "ok", 1, 1, "aaa"))

        placeDao.deletePlace(placeId)

        Assert.assertEquals(0, placeDao.getPlaces().size)

    }


    @Test
    @Throws(Exception::class)
    fun updatePlace_returnOneOnSuccessfulUpdate() {
        placeDao.insertPlace(Place("ok", "ok", "ok", 1, 1, "aaa"))
        val p1 = placeDao.updatePlace(Place(1, "newName", "ok", "ok", 1, 1, "aaa"))
        Assert.assertEquals(p1, 1)

    }

    @Test
    @Throws(Exception::class)
    fun updatePlace_returnZeroOnUnsuccessfulUpdate() {
        val p1 = placeDao.updatePlace(Place(1, "newName", "ok", "ok", 1, 1, "aaa"))
        Assert.assertEquals(p1, 0)
    }
}
