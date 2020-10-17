package br.alexandregpereira.jerry.app.recyclerview

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import br.alexandregpereira.jerry.animator.ElevationSpringItemAnimator
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.dpToPx
import kotlinx.android.synthetic.main.activity_recycler_view.*
import java.util.*

class GridRecyclerViewSpringActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, GridRecyclerViewSpringActivity::class.java)
        }
    }

    private val list = (0..14).map {
        Item(id = it, value = it.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val adapter = ExampleAdapter(
            80.dpToPx(resources),
            80.dpToPx(resources)
        )
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        recyclerView.adapter = adapter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            recyclerView.itemAnimator = ElevationSpringItemAnimator(
                elevationStiffness = 400f,
                alphaStiffness = 380f,
                translationStiffness = 120f
            )
        }

        button.setOnClickListener {
            adapter.setData(listOf(list[0]) + list.subList(2, list.size))
        }

        button2.setOnClickListener {
            adapter.setData(list.map {
                it.copy(value = "${UUID.randomUUID().toString().substring(0..4)} ${it.value}")
            })
        }

        button3.setOnClickListener {
            adapter.setData(listOf())
        }

        button4.setOnClickListener {
            adapter.setData(list)
        }

        button5.setOnClickListener {
            adapter.setData(adapter.currentList.shuffled())
        }

        button6.setOnClickListener {
            adapter.setData(list.subList(0, 3) + list.subList(5, list.size))
        }
    }
}


