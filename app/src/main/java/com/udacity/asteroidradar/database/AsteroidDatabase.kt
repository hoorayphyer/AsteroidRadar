package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDatabaseDao {
    @Query("SELECT * FROM asteroids_data_table ORDER BY close_approach_date") // TODO double check order
    fun getAllAsteroids() : LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateDatabase( asteroids : List<Asteroid> )
}

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val dao : AsteroidDatabaseDao

    companion object {
        // a singleton instance of the database
        @Volatile
        private var INSTANCE : AsteroidDatabase? = null

        fun getInstance(context: Context) : AsteroidDatabase {
            synchronized(this) {
                var instance = INSTANCE
                // if there is not an existing instance of database, create one
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "sleep_history_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }


}