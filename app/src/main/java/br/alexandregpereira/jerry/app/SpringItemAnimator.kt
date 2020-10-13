package br.alexandregpereira.jerry.app

import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.alexandregpereira.jerry.BaseItemAnimator
import br.alexandregpereira.jerry.dpToPx
import br.alexandregpereira.jerry.startFadeSpringAnimation
import br.alexandregpereira.jerry.startSpringElevation

@RequiresApi(21)
class SpringItemAnimator : BaseItemAnimator() {

    override fun preAnimateAdd(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.alpha = 0f
        holder.itemView.elevation = 0f
        return true
    }

    override fun startAddAnimation(
        holder: RecyclerView.ViewHolder?,
        onAnimationEndListener: OnAnimationEndListener
    ): Boolean {
        holder?.itemView?.startFadeSpringAnimation(1f, stiffness = 1200f) {
            holder.itemView.startSpringElevation(4f.dpToPx(holder.itemView.resources), stiffness = 1200f) {
                onAnimationEndListener.onAnimationEnd()
            }
        }
        return true
    }

    override fun startRemoveAnimation(
        holder: RecyclerView.ViewHolder?,
        onAnimationEndListener: OnAnimationEndListener
    ): Boolean {
        holder?.itemView?.startSpringElevation(0f.dpToPx(holder.itemView.resources), stiffness = 1200f) {
            holder.itemView.startFadeSpringAnimation(0f, stiffness = 1200f) {
                holder.itemView.alpha = 1f
                holder.itemView.elevation = 4f
                onAnimationEndListener.onAnimationEnd()
            }
        }
        return true
    }
}