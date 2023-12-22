package template

import android.app.Application
import android.content.Context
import androidx.navigation.NavHostController
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import template.data.PlaceDatabase
import template.data.PlaceRepositoryImplementation
import template.domain.repository.PlaceRepository
import template.domain.usecase.AddPlace
import template.domain.usecase.DeletePlace
import template.domain.usecase.GetPlace
import template.domain.usecase.GetPlaces
import template.domain.usecase.GetPlacesList
import template.domain.usecase.PlaceUseCases
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlaceDatabase(app: Application): PlaceDatabase {
        return Room.databaseBuilder(
            app,
            PlaceDatabase::class.java,
            PlaceDatabase.DATABASE_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun providePlaceRepository(db: PlaceDatabase): PlaceRepository {
        return PlaceRepositoryImplementation(db.placeDao)
    }

    @Provides
    @Singleton
    fun providePlaceUseCases(repository: PlaceRepository): PlaceUseCases {
        return PlaceUseCases(
            deletePlace = DeletePlace(repository),
            addPlace = AddPlace(repository),
            getPlace = GetPlace(repository),
            getPlaces = GetPlaces(repository),
            getPlacesList = GetPlacesList(repository)
        )
    }



}
