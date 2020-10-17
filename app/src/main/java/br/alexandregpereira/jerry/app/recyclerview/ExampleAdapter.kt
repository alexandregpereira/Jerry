package br.alexandregpereira.jerry.app.recyclerview

import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.dpToPx

class ExampleAdapter(
    private val itemWidth: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    private val itemHeight: Int? = null
) : ListAdapter<Item, ExampleAdapter.ExampleViewHolder>(DiffUtil) {

    fun setData(list: List<Item>) {
        submitList(list)
    }

    inner class ExampleViewHolder(
        private val viewGroup: ViewGroup
    ) : RecyclerView.ViewHolder(viewGroup) {

        fun bind(item: Item) = viewGroup.runCatching { getChildAt(0) }.getOrNull()?.run {
            this as? TextView
        }?.let { textView ->
            textView.text = item.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        return ExampleViewHolder(
            CardView(parent.context).apply {
                val padding = resources.getDimensionPixelOffset(R.dimen.text_padding)
                layoutParams = ViewGroup.MarginLayoutParams(
                    itemWidth,
                    itemHeight ?: 60.dpToPx(parent.resources)
                ).apply {
                    setMargins(padding / 2, padding / 2, padding / 2, padding / 2)
                }
//                setCardBackgroundColor(ContextCompat.getColor(context, R.color.backgroundHelperColor))
                radius = resources.getDimension(R.dimen.corner_size)
                addView(
                    AppCompatTextView(parent.context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setPadding(padding, padding, padding, padding)
                        gravity = Gravity.CENTER_VERTICAL
                        setTextColor(ContextCompat.getColor(context, R.color.textSecondaryColor))
                        maxLines = 1
                    }
                )
            }
        )
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object DiffUtil : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}

data class Item(
    val id: Int,
    val value: String
)