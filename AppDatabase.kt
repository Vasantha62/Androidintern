// AppDatabase.kt
@Database(entities = [DataItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataItemDao(): DataItemDao
}

// DataItemDao.kt
@Dao
interface DataItemDao {
    @Query("SELECT * FROM dataitem")
    suspend fun getAllDataItems(): List<DataItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataItems(dataItems: List<DataItem>)

    @Delete
    suspend fun deleteDataItem(dataItem: DataItem)

    @Query("UPDATE dataitem SET isFavorite = :isFavorite WHERE imdbId = :imdbId")
    suspend fun updateFavoriteStatus(imdbId: String, isFavorite: Boolean)
}
