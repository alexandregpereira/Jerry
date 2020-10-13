package br.alexandregpereira.jerry.app

import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.alexandregpereira.jerry.BaseItemAnimator
import br.alexandregpereira.jerry.dpToPx
import br.alexandregpereira.jerry.startElevationSpringAnimation
import br.alexandregpereira.jerry.startFadeSpringAnimation

private const val ANIMATION_STIFFNESS = 500f

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
        holder: RecyclerView.ViewHolder?,
        onAnimationEndListener: OnAnimationEndListener
    ): Boolean {
        holder?.itemView?.startFadeSpringAnimation(
            targetValue = alphaFinalValue,
            stiffness = alphaStiffness
        ) {
            holder.itemView.startElevationSpringAnimation(
                targetValue = elevationFinalValue.dpToPx(holder.itemView.resources),
                stiffness = elevationStiffness
            ) {
                onAnimationEndListener.onAnimationEnd()
            }
        }
        return true
    }

    override fun startRemoveAnimation(
        holder: RecyclerView.ViewHolder?,
        onAnimationEndListener: OnAnimationEndListener
    ): Boolean {
        holder?.itemView?.startElevationSpringAnimation(
            targetValue = elevationInitialValue.dpToPx(holder.itemView.resources),
            stiffness = elevationStiffness
        ) {
            holder.itemView.startFadeSpringAnimation(
                targetValue = alphaInitialValue,
                stiffness = alphaStiffness
            ) {
                onAnimationEndListener.onAnimationEnd()
            }
        }
        return true
    }
}