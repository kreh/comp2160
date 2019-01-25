package t00593238.dave.yetanotherunitconversionapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

// Inspired by https://stackoverflow.com/questions/24254722/android-how-to-swipe-between-activities-not-fragments-master-detail-best-set
open class OnSwipeTouchListener(context: Context) : View.OnTouchListener {
    private val gestureDetector: GestureDetector = GestureDetector(context, GestureListener())

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    fun getGestureDetector(): GestureDetector {
        return gestureDetector
    }

    open fun onSwipeRight() {}

    open fun onSwipeLeft() {}

    open fun onSwipeUp() {}

    open fun onSwipeDown() {}

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        private val _swipeThreshold = 100
        private val _swipeVelocityThreshold = 100

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            e1!!
            e2!!
            val diffY = e2.y - e1.y
            val diffX = e2.x - e1.x
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > _swipeThreshold && Math.abs(velocityX) > _swipeVelocityThreshold) {
                    if (diffX > 0) onSwipeRight() else onSwipeLeft()
                    result = true
                }
            } else if (Math.abs(diffY) > _swipeThreshold && Math.abs(velocityY) > _swipeVelocityThreshold) {
                if (diffY > 0) onSwipeDown() else onSwipeUp()
                result = true
            }
            return result
        }
    }
}