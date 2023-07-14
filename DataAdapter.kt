// DataAdapter.kt
class DataAdapter(
    private val dataItems: MutableList<DataItem>,
    private val onDeleteClickListener: (DataItem) -> Unit,
    private val onFavoriteClickListener: (DataItem) -> Unit
) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val yearTextView: TextView = itemView.findViewById(R.id.yearTextView)
        private val runtimeTextView: TextView = itemView.findViewById(R.id.runtimeTextView)
        private val castTextView: TextView = itemView.findViewById(R.id.castTextView)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        private val favoriteButton: Button = itemView.findViewById(R.id.favoriteButton)

        fun bind(dataItem: DataItem) {
            titleTextView.text = dataItem.title
            yearTextView.text = dataItem.year
            runtimeTextView.text = dataItem.runtime
            castTextView.text = dataItem.cast

            deleteButton.setOnClickListener { onDeleteClickListener(dataItem) }

            favoriteButton.apply {
                text = if (dataItem.isFavorite) {
                    context.getString(R.string.remove_favorite)
                } else {
                    context.getString(R.string.add_favorite)
                }
                setOnClickListener { onFavoriteClickListener(dataItem) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_data, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataItems[position])
    }

    override fun getItemCount(): Int = dataItems.size
}
