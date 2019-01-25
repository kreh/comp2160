package t00593238.dave.yetanotherunitconversionapp

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.TextView
import kotlin.random.Random

class OutputActivity : Activity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_output)

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)

        val outputTemp = intent.getStringExtra(OUTPUT_STRING)
        val temperatureSwitch = intent.getIntExtra(OUTPUT_TEMPERATURE, 0)

        val constraintLayout_Output = findViewById<ConstraintLayout>(R.id.constraintLayout_Output)
        val textView_Temperature = findViewById<TextView>(R.id.temp_output)
        val textView_Tagline = findViewById<TextView>(R.id.temp_tagline)

        constraintLayout_Output.setOnTouchListener(object: OnSwipeTouchListener(this) {
            override fun onSwipeRight() {
                onBackPressed()
            }
        })

        val (backgroundGradient, textColor, tagline) = when {
            temperatureSwitch > 0 -> {
                val hotOutside = resources.getStringArray(R.array.hotOutside)
                Triple(R.drawable.hot_gradient, R.color.hotLight, hotOutside[Random.nextInt(hotOutside.size)])
            }
            temperatureSwitch < 0 -> {
                val coldOutside = resources.getStringArray(R.array.coldOutside)
                Triple(R.drawable.cold_gradient, R.color.coldLight, coldOutside[Random.nextInt(coldOutside.size)])
            }
            else -> {
                val neutralOutside = resources.getStringArray(R.array.neutralOutside)
                Triple(R.drawable.n_gradient, R.color.neutralLight, neutralOutside[Random.nextInt(neutralOutside.size)])
            }
        }

        constraintLayout_Output.background = resources.getDrawable(backgroundGradient, this.theme)
        textView_Temperature.text = outputTemp
        textView_Temperature.setTextColor(textColor)
        textView_Tagline.text = tagline
        textView_Tagline.setTextColor(textColor)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }
}
