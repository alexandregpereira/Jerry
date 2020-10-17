package br.alexandregpereira.jerry.app.animation

fun <T> List<T>.circularIterator(): Iterator<T> {
    val size = this.size
    return object : MutableIterator<T> {
        var i = 0
        override fun hasNext(): Boolean {
            return i < size
        }

        override fun next(): T {
            return this@circularIterator[i++ % size]
        }

        override fun remove() {}
    }
}
