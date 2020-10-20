package br.alexandregpereira.jerry

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addOnScrolled(block: () -> Unit) {
    this.addOnScrolledWithPixels { _, _ ->
        block()
    }
}

fun RecyclerView.addOnScrolledWithPixels(block: (dx: Int, dy: Int) -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            block(dx, dy)
        }
    })
}
