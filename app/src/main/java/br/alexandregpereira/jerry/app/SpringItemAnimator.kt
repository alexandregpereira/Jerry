package br.alexandregpereira.jerry.app

import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.alexandregpereira.jerry.ANIMATION_STIFFNESS
import br.alexandregpereira.jerry.BaseItemAnimator
import br.alexandregpereira.jerry.dpToPx
import br.alexandregpereira.jerry.elevationSpring
import br.alexandregpereira.jerry.fadeSpring
import br.alexandregpereira.jerry.startElevationSpringAnimation
import br.alexandregpereira.jerry.startFadeSpringAnimation

@RequiresApi(21)
class SpringItemAnimator : BaseItemAnimator() {

    private val elevationStiffness = ANIMATION_STIFFNESS * 2.5f
    private val alphaStiffness = ANIMATION_STIFFNESS * 2f
    private val elevationInitialValue = 0f
    private val alphaInitialValue = 0f
    private val elevationFinalValue = 4f
    private val alphaFinalValue = 1f

    override fun preAnimateAdd(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.alpha = 0f
        holder.itemView.elevation = 0f
        return true
    }

    override fun startAddAnimation(
        holder: RecyclerView.ViewHolder,
        onAnimationEndListener: OnAnimationEndListener
    ): Boolean {
        val view = holder.itemView

        holder.itemView.fadeSpring(stiffness = alphaStiffness).cancel()
        holder.itemView.startFadeSpringAnimation(targetValue = alphaFinalValue) { canceled ->

            if (canceled) {
                onAnimationEndListener.onAnimationEnd()
                return@startFadeSpringAnimation
            }

            holder.itemView.elevationSpring(stiffness = elevationStiffness).cancel()
            view.startElevationSpringAnimation(
                targetValue = elevationFinalValue.dpToPx(view.resources)
            ) {
                onAnimationEndListener.onAnimationEnd()
            }
        }
        return true
    }

    override fun startRemoveAnimation(
        holder: RecyclerView.ViewHolder,
        onAnimationEndListener: OnAnimationEndListener
    ): Boolean {
        val view = holder.itemView

        holder.itemView.elevationSpring(stiffness = elevationStiffness).cancel()
        holder.itemView.startElevationSpringAnimation(
            targetValue = elevationInitialValue.dpToPx(view.resources)
        ) { canceled ->

            if (canceled) {
                onAnimationEndListener.onAnimationEnd()
                return@startElevationSpringAnimation
            }

            holder.itemView.fadeSpring(stiffness = alphaStiffness).cancel()
            view.startFadeSpringAnimation(targetValue = alphaInitialValue) {
                view.alpha = alphaFinalValue
                view.elevation = elevationFinalValue
                onAnimationEndListener.onAnimationEnd()
            }
        }
        return true
    }
}