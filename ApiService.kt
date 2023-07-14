
interface ApiService {
    @GET("1.json")
    suspend fun getData(): List<DataItem>

    @GET("2.json")
    suspend fun getAdditionalData(): List<DataItem>
}
