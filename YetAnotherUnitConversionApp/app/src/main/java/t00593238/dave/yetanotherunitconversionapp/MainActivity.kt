package t00593238.dave.yetanotherunitconversionapp

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.*


class MainActivity : Activity(), AdapterView.OnItemSelectedListener, View.OnTouchListener {
    // TODO Degree symbol and unit on output - Done
    // TODO unit on input - Done
    // TODO "to <unit>" on spinner - Done
    // TODO onclick function to change spinner value - Done
    // TODO confine edittext to only number values? constrain degree symbol and unit
    // TODO fragment and tab management for output

    fun String.removeTempUnit(): Double = replace(Regex("([°CF]{0,2})$"), "").toDouble()
    var inputNumber: EditText? = null
    var outputNumber: TextView? = null
    var spinner: Spinner? = null
    var cSelected: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputNumber = findViewById(R.id.mainTextInput)

        inputNumber?.text = SpannableStringBuilder(getString(R.string.mainTextDefaultFormat, 0.0, 'F'))
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
        spinner?.setOnTouchListener(this)
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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
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

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        if (p1?.action == MotionEvent.ACTION_UP) {
            spinner?.setSelection(if (cSelected) 1 else 0)
            return true
        } else if (p1?.action == MotionEvent.ACTION_DOWN) {
            return true
        }
        return false
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        return
    }

    // Determines if using C temp or F temp to convert from (cSelected = true -> using C temp)
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val input: Double = inputNumber?.text.toString().removeTempUnit()
        val format = getFormat(input)
        if (p2 == 0) {
            cSelected = true
            inputNumber?.text = SpannableStringBuilder(getString(format, input, 'F'))
            convertToC(input)
        }
        else {
            cSelected = false
            inputNumber?.text = SpannableStringBuilder(getString(format, input, 'C'))
            convertToF(input)
        }
    }

    fun submitInput(view: View?) {
        val input: Double = this.findViewById<EditText>(R.id.mainTextInput).text.toString().removeTempUnit()
        if (cSelected) convertToC(input) else convertToF(input)
//        button.setBackgroundResource(R.drawable.btn)
//        input.toString()
//        String.format("%s°")
    }
}
