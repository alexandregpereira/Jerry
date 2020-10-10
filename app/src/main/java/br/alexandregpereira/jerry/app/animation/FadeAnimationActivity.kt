package br.alexandregpereira.jerry.app.animation

import br.alexandregpereira.jerry.invisibleFadeOut
import br.alexandregpereira.jerry.setTextFade
import br.alexandregpereira.jerry.visibleFadeIn
import br.alexandregpereira.jerry.app.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        fadeTextButton.setOnClickListener {
            fadeTextView.setTextFade("${System.currentTimeMillis()}")
        }

        goneTextButton.setOnClickListener {
            goneTextView.invisibleFadeOut()
        }

        goneVisibleTextButton.setOnClickListener {
            goneTextView.visibleFadeIn()
        }

        visibleTextButton.setOnClickListener {
            visibleTextView.visibleFadeIn()
        }

        visibleInvisibleTextButton.setOnClickListener {
            visibleTextView.invisibleFadeOut()
        }
    }
}
