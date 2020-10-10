package br.alexandregpereira.jerry

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addOnScrollListenerWithScrolledPixels(block: (dx: Int, dy: Int) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            block(dx, dy)
        }
    })
}

fun RecyclerView.addOnScrollListener(block: () -> Unit) {
    addOnScrollListenerWithScrolledPixels { _, _ -> block() }
}
