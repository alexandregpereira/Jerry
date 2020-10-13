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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val adapter = ExampleAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            recyclerView.itemAnimator = SpringItemAnimator()
        }

        val list = (0..2).map {
            Item(id = it, value = it.toString())
        }
        button.setOnClickListener {
            adapter.submitList(list.shuffled())
        }

        button2.setOnClickListener {
            adapter.submitList(list.map {
                it.copy(value = "${UUID.randomUUID().toString().substring(0..10)} ${it.value}")
            })
        }

        button3.setOnClickListener {
            adapter.submitList(listOf())
        }
    }
}

class ExampleAdapter : ListAdapter<Item, ExampleAdapter.ExampleViewHolder>(DiffUtil) {

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
        return ExampleViewHolder(
            AppCompatTextView(parent.context).apply {
                val padding = resources.getDimensionPixelOffset(R.dimen.text_padding)
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    100.dpToPx(resources)
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
