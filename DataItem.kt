// DataItem.kt
data class DataItem(
    @PrimaryKey val imdbId: String,
    val title: String,
    val year: String,
    val runtime: String,
    val cast: String,
    var isFavorite: Boolean = false
)
