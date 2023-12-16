package template.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import template.domain.model.Place
import template.domain.usecase.PlaceUseCases
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class UseCaseTest {


    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var placeUseCases: PlaceUseCases

    @Test
    @Throws(Exception::class)
    fun placeBasicUseCaseTest() {
        hiltRule.inject()

        val place = Place("eiffel Tower", "desc", "2022/12/11", 5, 0, "imgid");
        val placeId = placeUseCases.addPlace(place)
        Assert.assertEquals(1, placeUseCases.getPlaces().size)
        Assert.assertEquals(placeUseCases.getPlace(placeId)!!.name, place.name)

        placeUseCases.deletePlace(placeId)

        Assert.assertEquals(placeUseCases.getPlaces().size, 0)
    }
}
