package br.alexandregpereira.jerry.app.recyclerview

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.alexandregpereira.jerry.app.R
import kotlinx.android.synthetic.main.activity_recycler_view.*
import java.util.*

class RecyclerViewSpringActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, RecyclerViewSpringActivity::class.java)
        }
    }

    private val list = (0..4).map {
        Item(id = it, value = it.toString())
    }

    private val list2 = listOf(list[0]) + list.subList(2, list.size)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val adapter = ExampleAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            recyclerView.itemAnimator = SpringItemAnimator()
        }

        button.setOnClickListener {
            adapter.setData(list2)
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
        }

        button5.setOnClickListener {
            adapter.setData(adapter.currentList.reversed())
        }

        button6.setOnClickListener {
            adapter.setData(list.subList(0, 3) + list[4])
        }
    }
}


