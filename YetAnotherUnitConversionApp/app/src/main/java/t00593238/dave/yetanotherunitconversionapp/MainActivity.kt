package t00593238.dave.yetanotherunitconversionapp

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

class MainActivity : Activity() {

    var inputNumber: EditText? = null
    val spinner: Spinner = findViewById(R.id.tempChange)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputNumber = findViewById(R.id.mainTextInput)
    }

    fun convertToF(input: View?): TextView {
        val input = inputNumber?.text.toString().toDouble()
        val f = input * (9 / 5) + 32
        var end: TextView? = null

        end = findViewById(R.id.output)
        end.text = f.toString()
        return end
    }
}
