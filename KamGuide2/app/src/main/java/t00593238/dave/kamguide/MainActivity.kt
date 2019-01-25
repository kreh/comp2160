package t00593238.dave.kamguide

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toolbar

class MainActivity : Activity() {

    lateinit var toolbar: Toolbar
    private lateinit var rectangle: Rect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(R.layout.fragment_top)

        toolbar = findViewById(R.id.toolbar)

        val statusBarHeightFormula = Math.ceil(25.0 * this.resources.displayMetrics.density)

        // this will only work with notchless screens
        toolbar.setPadding(0, (statusBarHeightFormula.toInt() * 1.25).toInt(), 0, (statusBarHeightFormula.toInt() * 0.75).toInt())
        toolbar.setTitleMargin(0, 0,  0, 0)

    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}
