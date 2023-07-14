// DataFragment.kt
class DataFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataAdapter
    private lateinit var apiService: ApiService
    private lateinit var dataItemDao: DataItemDao
    private var dataItems: MutableList<DataItem> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_data, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = DataAdapter(dataItems, ::onDeleteClicked, ::onFavoriteClicked)
        recyclerView.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("http://task.auditflo.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)

        val database = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "app-database")
            .build()
        dataItemDao = database.dataItemDao()

        fetchDataFromApi()

        return view
    }

    private fun fetchDataFromApi() {
        lifecycleScope.launch {
            try {
                val data = apiService.getData()
                saveDataToDatabase(data)
                dataItems.addAll(data)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private suspend fun saveDataToDatabase(data: List<DataItem>) {
        withContext(Dispatchers.IO) {
            dataItemDao.insertDataItems(data)
        }
    }

    private fun onDeleteClicked(dataItem: DataItem) {
        lifecycleScope.launch {
            try {
                dataItemDao.deleteDataItem(dataItem)
                dataItems.remove(dataItem)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun onFavoriteClicked(dataItem: DataItem) {
        lifecycleScope.launch {
            try {
                val isFavorite = !dataItem.isFavorite
                dataItemDao.updateFavoriteStatus(dataItem.imdbId, isFavorite)
                dataItem.isFavorite = isFavorite
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
