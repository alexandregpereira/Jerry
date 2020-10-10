package br.alexandregpereira.jerry.app

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.alexandregpereira.jerry.app.animation.*
import br.alexandregpereira.jerry.app.widgets.setMaterialShapeDrawable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        componentsRecycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainAdapter(getAnimationComponentsName()) {
                when (it) {
                    AnimationComponent.FADE.ordinal -> startActivity(
                        FadeAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.COLLAPSE_FADING.ordinal -> startActivity(
                        CollapseFadingAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.EXPAND_FADING.ordinal -> startActivity(
                        ExpandFadingAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.COLLAPSE.ordinal -> startActivity(
                        CollapseAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.EXPAND.ordinal -> startActivity(
                        ExpandAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.TEXT_EXPANDABLE.ordinal -> startActivity(
                        TextExpandableAnimationActivity.getStartIntent(this@MainActivity)
                    )
                }
            }
        }
    }
}

fun getAnimationComponentsName(): List<String> {
    return AnimationComponent.values().map { it.name }
}

enum class AnimationComponent {
    COLLAPSE, COLLAPSE_FADING, EXPAND, EXPAND_FADING, FADE, TEXT_EXPANDABLE
}

class MainAdapter(
    private val texts: List<String>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

    inner class MainViewHolder(
        private val textView: TextView
    ) : RecyclerView.ViewHolder(textView) {

        fun bind(text: String) {
            textView.text = text
            textView.setOnClickListener { onClick(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
       return MainViewHolder(TextView(parent.context).apply {
           val padding = resources.getDimensionPixelOffset(R.dimen.text_padding)
           layoutParams = ViewGroup.MarginLayoutParams(
               ViewGroup.LayoutParams.MATCH_PARENT,
               ViewGroup.LayoutParams.WRAP_CONTENT
           ).apply {
               setMargins(padding / 2, padding / 2, padding / 2, padding / 2)
           }

           setTextColor(ContextCompat.getColor(context, R.color.textSecondaryColor))
           setMaterialShapeDrawable()
       })
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(texts[position])
    }

    override fun getItemCount(): Int = texts.size
}