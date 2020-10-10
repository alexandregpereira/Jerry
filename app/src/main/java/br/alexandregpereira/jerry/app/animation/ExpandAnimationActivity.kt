package br.alexandregpereira.jerry.app.animation

import br.alexandregpereira.jerry.collapse
import br.alexandregpereira.jerry.expand
import br.alexandregpereira.jerry.app.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_expand_animation.*

class ExpandAnimationActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ExpandAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expand_animation)

        expandTextButton.setOnClickListener {
            expandTextView.expand()
        }

        expandCollapseTextButton.setOnClickListener {
            expandTextView.collapse()
        }

        expandWidthTextButton.setOnClickListener {
            expandWidthTextView.expand(isHeight = false)
        }

        expandCollapseWidthTextButton.setOnClickListener {
            expandWidthTextView.collapse(isHeight = false)
        }

        expandFixedTextButton.setOnClickListener {
            expandFixedTextView.expand()
        }

        expandCollapseFixedTextButton.setOnClickListener {
            expandFixedTextView.collapse()
        }

        expandWidthTextButton.setOnClickListener {
            expandWidthTextView.expand(isHeight = false)
        }

        expandCollapseWidthTextButton.setOnClickListener {
            expandWidthTextView.collapse(isHeight = false)
        }

        expandMatchWidthButton.setOnClickListener {
            expandMatchWidthView.expand(isHeight = false)
        }

        expandCollapseMatchWidthButton.setOnClickListener {
            expandMatchWidthView.collapse(isHeight = false)
        }

        expandFixedWidthButton.setOnClickListener {
            expandFixedWidthView.expand(isHeight = false)
        }

        expandCollapseFixedWidthButton.setOnClickListener {
            expandFixedWidthView.collapse(isHeight = false)
        }
    }
}
