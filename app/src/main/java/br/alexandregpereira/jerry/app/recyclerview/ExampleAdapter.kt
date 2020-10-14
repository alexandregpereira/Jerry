package br.alexandregpereira.jerry.app.recyclerview

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.app.widgets.setMaterialShapeDrawable
import br.alexandregpereira.jerry.dpToPx
import androidx.recyclerview.widget.DiffUtil

class ExampleAdapter : ListAdapter<Item, ExampleAdapter.ExampleViewHolder>(DiffUtil) {

    fun setData(list: List<Item>) {
        submitList(list)
    }

    inner class ExampleViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: Item) {
            if (view is TextView) {
                view.text = item.value
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val height = when (viewType) {
            ViewType.TYPE_2.ordinal -> ViewType.TYPE_2.height
            else -> ViewType.TYPE_1.height
        }
        return ExampleViewHolder(
            AppCompatTextView(parent.context).apply {
                val padding = resources.getDimensionPixelOffset(R.dimen.text_padding)
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    height.dpToPx(resources)
                ).apply {
                    setMargins(padding / 2, padding / 2, padding / 2, padding / 2)
                }
                setPadding(padding, padding, padding, padding)
                gravity = Gravity.CENTER_VERTICAL
                setTextColor(ContextCompat.getColor(context, R.color.textSecondaryColor))
                setMaterialShapeDrawable(ContextCompat.getColor(context,
                    R.color.backgroundHelperColor
                ))
            }
        )
    }

    override fun getItemViewType(position: Int): Int {
        return (if (position == 1) ViewType.TYPE_2 else ViewType.TYPE_1).ordinal
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    enum class ViewType(val height: Int) {
        TYPE_1(60), TYPE_2(100)
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