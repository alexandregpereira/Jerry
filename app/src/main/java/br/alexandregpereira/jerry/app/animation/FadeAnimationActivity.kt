package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.invisibleFadeOutSpring
import br.alexandregpereira.jerry.setTextFade
import br.alexandregpereira.jerry.visibleFadeInSpring
import kotlinx.android.synthetic.main.activity_fade_animation.*

class FadeAnimationActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, FadeAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fade_animation)

        val list = listOf(
            "Fade text change 2",
            "Fade text change 3",
            "Fade text change 4",
            "Fade text change 5",
            "Fade text change 1",
        ).circularIterator()
        fadeTextButton.setOnClickListener {
            fadeTextView.setTextFade(list.next())
        }

        goneTextButton.setOnClickListener {
            goneTextView.invisibleFadeOutSpring()
        }

        goneVisibleTextButton.setOnClickListener {
            goneTextView.visibleFadeInSpring()
        }

        visibleTextButton.setOnClickListener {
            visibleTextView.visibleFadeInSpring()
        }

        visibleInvisibleTextButton.setOnClickListener {
            visibleTextView.invisibleFadeOutSpring()
        }
    }

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
}
