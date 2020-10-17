package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.goneFadeOut
import br.alexandregpereira.jerry.textview.setTextFadeSpring
import br.alexandregpereira.jerry.visibleFadeIn
import kotlinx.android.synthetic.main.activity_fade_animation.*

class FadeSpringAnimationActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, FadeSpringAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fade_animation)

        fadeLabel.setText(R.string.fade_spring)

        val list = listOf(
            "Fade text changeasaaaaa 2",
            "Fade text change asda sdas 3",
            "asdasd asdasdasd 4",
            "Fade asdasdasddas change 5",
            "Fade text change 1",
        ).circularIterator()
        fadeTextButton.setOnClickListener {
            fadeTextView.setTextFadeSpring(list.next())
        }

        goneTextButton.setOnClickListener {
            goneTextView.goneFadeOut()
        }

        goneVisibleTextButton.setOnClickListener {
            goneTextView.visibleFadeIn()
        }

        visibleTextButton.setOnClickListener {
            visibleTextView.visibleFadeIn()
        }

        visibleInvisibleTextButton.setOnClickListener {
            visibleTextView.goneFadeOut()
        }
    }
}


