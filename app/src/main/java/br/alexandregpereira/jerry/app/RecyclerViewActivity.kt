package br.alexandregpereira.jerry.app

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.alexandregpereira.jerry.app.widgets.setMaterialShapeDrawable
import br.alexandregpereira.jerry.dpToPx
import kotlinx.android.synthetic.main.activity_recycler_view.*
import java.util.*

class RecyclerViewActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, RecyclerViewActivity::class.java)
        }
    }

    private val list = (0..3).map {
        Item(id = it, value = it.toString())
    }

    private val list2 = listOf(list[0], list[2], list[3])

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val adapter = ExampleAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            recyclerView.itemAnimator = SpringItemAnimator()
        }
        recyclerView.itemAnimator?.addDuration = 2000
        recyclerView.itemAnimator?.removeDuration = 2000
        recyclerView.itemAnimator?.changeDuration = 2000
        recyclerView.itemAnimator?.moveDuration = 2000

        button.setOnClickListener {
            adapter.setData(list2)
//            adapter.notifyItemRangeInserted(0, list2.size)
        }

        button2.setOnClickListener {
            adapter.setData(list2.map {
                it.copy(value = "${UUID.randomUUID().toString().substring(0..10)} ${it.value}")
            })
        }

        button3.setOnClickListener {
            adapter.setData(listOf())
        }

        button4.setOnClickListener {
            adapter.setData(list)
//            adapter.notifyItemInserted(1)
        }

        button5.setOnClickListener {
            adapter.setData(adapter.currentList.reversed())
        }

        button6.setOnClickListener {
            adapter.setData(list2)
        }
    }
}

class ExampleAdapter : ListAdapter<Item, ExampleAdapter.ExampleViewHolder>(DiffUtil) {

//    var currentList: List<Item> = listOf()
//        private set

    fun setData(list: List<Item>) {
//        currentList = list
        submitList(list)
    }

//    override fun getItemCount(): Int = currentList.size
//
//    private fun getItem(position: Int): Item {
//        return currentList[position]
//    }

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
                    60.dpToPx(resources)
                ).apply {
                    setMargins(padding / 2, padding / 2, padding / 2, padding / 2)
                }
                setPadding(padding, padding, padding, padding)
                gravity = Gravity.CENTER_VERTICAL
                setTextColor(ContextCompat.getColor(context, R.color.textSecondaryColor))
                setMaterialShapeDrawable(ContextCompat.getColor(context, R.color.backgroundHelperColor))
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
