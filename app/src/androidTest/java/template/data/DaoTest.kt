package template.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import template.domain.model.Place
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var placeDao: PlaceDao
    private lateinit var db: PlaceDatabase
    private val testDispatcher = StandardTestDispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, PlaceDatabase::class.java,
        ).build()
        placeDao = db.placeDao

        Dispatchers.setMain(testDispatcher)

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }


    @Test
    @Throws(Exception::class)
    fun insertPlace_createPlace() = runTest {
        placeDao.insertPlace(Place("ok", "ok", "ok", 1, 1, "aaa"))
        placeDao.insertPlace(Place("new", "ok", "ok", 1, 1, "aaa"))
        val afterSize = placeDao.getPlaces().toList().size
        Assert.assertEquals(2, afterSize)

    }

    @Test
    @Throws(Exception::class)
    fun deletePlace_deletePlace() = runTest {


        val placeId = placeDao.insertPlace(Place("sex", "ok", "ok", 1, 1, "aaa"))

        placeDao.deletePlace(placeId)
        val afterSize = placeDao.getPlaces().toList().size
        Assert.assertEquals(0, afterSize)

    }


    @Test
    @Throws(Exception::class)
    fun updatePlace_returnOneOnSuccessfulUpdate() = runTest {
        placeDao.insertPlace(Place("ok", "ok", "ok", 1, 1, "aaa"))
        val p1 = placeDao.updatePlace(Place(1, "newName", "ok", "ok", 1, 1, "aaa"))
        Assert.assertEquals(p1, 1)

    }

    @Test
    @Throws(Exception::class)
    fun updatePlace_returnZeroOnUnsuccessfulUpdate() = runTest {
        val p1 = placeDao.updatePlace(Place(1, "newName", "ok", "ok", 1, 1, "aaa"))
        Assert.assertEquals(p1, 0)
    }
}
