package t00593238.dave.yetanotherunitconversionapp

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.KeyEvent
import android.view.View
import android.widget.*


class MainActivity : Activity(), AdapterView.OnItemSelectedListener {
    // TODO Degree symbol and unit on output - Done
    // TODO unit on input - Done
    // TODO "to <unit>" on spinner
    // TODO onclick function to change spinner value
    // TODO confine edittext to only number values? constrain degree symbol and unit
    // TODO fragment and tab management for output

    fun String.removeTempUnit(): Double = reversed().substring(2).reversed().toDouble()
    var inputNumber: EditText? = null
    var outputNumber: TextView? = null
    var spinner: Spinner? = null
    var cSelected: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputNumber = findViewById(R.id.mainTextInput)

        inputNumber?.text = SpannableStringBuilder(getString(R.string.mainTextDefaultFormat, 0.0, 'C'))
        // lambda that controls a listener to the enter key on the keyboard
        inputNumber?.setOnKeyListener(View.OnKeyListener { p0, p1, p2 ->
            if (p2?.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_ENTER) {
                submitInput(p0)
                return@OnKeyListener true
            }
            false
        })

        outputNumber = findViewById(R.id.output)
        convertToC(0.0)

        spinner = findViewById(R.id.spinner)
        spinner?.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
                this,
                R.array.tempChange,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_gallery_item)
            // Apply the adapter to the spinner
            spinner?.adapter = adapter
        }
    }

    private fun getFormat(input: Double): Int {
        return if (input.toInt().toDouble() == input) R.string.mainTextDefaultFormat else R.string.mainTextFormat
    }

    private fun convertToF(input: Double): Double {
        val f = input * (9.0 / 5.0) + 32.0
        val format = getFormat(f)
        outputNumber?.text = getString(format, f, 'F')
        return f
    }

    private fun convertToC(input: Double): Double {
        val c = (input - 32.0) * (5.0 / 9.0)
        val format = getFormat(c)
        outputNumber?.text = getString(format, c, 'C')
        return c
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        spinner?.setSelection(if (cSelected) 1 else 0, true)
        //spinner?.performItemClick(findViewById(R.id.spinner), if (cSelected) 1 else 0)
    }

    // Determines if using C temp or F temp to convert from (cSelected = true -> using C temp)
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val input: Double = inputNumber?.text.toString().removeTempUnit()
        val format = getFormat(input)
        if (p2 == 0) {
            cSelected = true
            inputNumber?.text = SpannableStringBuilder(getString(format, input, 'F'))
        }
        else {
            cSelected = false
            inputNumber?.text = SpannableStringBuilder(getString(format, input, 'C'))
        }
    }

    fun submitInput(view: View?) {
        val input: Double = this.findViewById<EditText>(R.id.mainTextInput).text.toString().removeTempUnit()
        println("Input: $input")
        println("Output: ${if (cSelected) convertToC(input) else convertToF(input)}")
//        button.setBackgroundResource(R.drawable.btn)
//        input.toString()
//        String.format("%s°")
    }
}
