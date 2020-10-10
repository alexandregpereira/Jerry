package br.alexandregpereira.jerry

import androidx.recyclerview.widget.RecyclerView

/**
 * Function helper to add a [RecyclerView.OnScrollListener] using the Kotlin extension.
 */
fun RecyclerView.addOnScrollListenerWithScrolledPixels(block: (dx: Int, dy: Int) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            block(dx, dy)
        }
    })
}

/**
 * Function helper to add a [RecyclerView.OnScrollListener] using the Kotlin extension. This is the
 * same as [addOnScrollListenerWithScrolledPixels], except the [block] function do not receive the
 * scrolled pixels.
 */
fun RecyclerView.addOnScrollListener(block: () -> Unit) {
    addOnScrollListenerWithScrolledPixels { _, _ -> block() }
}
