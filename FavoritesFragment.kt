// FavoritesFragment.kt
class FavoritesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataAdapter
    private lateinit var dataItemDao: DataItemDao
    private var favoriteDataItems: MutableList<DataItem> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
