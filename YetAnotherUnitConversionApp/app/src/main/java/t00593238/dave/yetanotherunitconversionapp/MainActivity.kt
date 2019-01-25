package t00593238.dave.yetanotherunitconversionapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.text.SpannableStringBuilder
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner

const val OUTPUT_STRING = "t00593238.dave.yetanotherunitconversionapp.MainActivity.OUTPUT_STRING"
const val OUTPUT_TEMPERATURE = "t00593238.dave.yetanotherunitconversionapp.MainActivity.OUTPUT_TEMPERATURE"


class MainActivity : Activity(), AdapterView.OnItemSelectedListener, View.OnTouchListener {
    // TODO confine edittext to only number values? constrain degree symbol and unit
    // TODO fragment and tab management for output

    private fun String.removeTempUnit(): Double =
        if (equals("")) 0.0
        else replace(Regex("([°CF]{0,2})$"), "").toDouble()


    private lateinit var inputNumber: EditText
    private lateinit var spinner: Spinner
    private var cSelected: Boolean = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputNumber = findViewById(R.id.mainTextInput)
        inputNumber.text = SpannableStringBuilder(getString(R.string.mainTextDefaultFormat, 0.0, 'F'))
        // lambda that controls a listener to the enter key on the keyboard
        inputNumber.setOnKeyListener(View.OnKeyListener { p0, p1, p2 ->
            if (p2?.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_ENTER) {
                submitInput(p0)
                return@OnKeyListener true
            }
            false
        })
        val constraint = findViewById<ConstraintLayout>(R.id.constraintLayout_Main)
        constraint.setOnTouchListener(object : OnSwipeTouchListener(this) {

            override fun onSwipeLeft() {
                submitInput(constraint)
            }
        })

        spinner = findViewById(R.id.spinner)
        spinner.setOnTouchListener(this)
        spinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            this,
            R.array.tempChange,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_gallery_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        if (p1?.action == MotionEvent.ACTION_UP) {
            spinner.setSelection(if (cSelected) 1 else 0)
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
        val input: Double = inputNumber.text.toString().removeTempUnit()
        val format = getFormat(input)
        if (p2 == 0) {
            cSelected = true
            inputNumber.text = SpannableStringBuilder(getString(format, input, 'F'))
        } else {
            cSelected = false
            inputNumber.text = SpannableStringBuilder(getString(format, input, 'C'))
        }
    }


    @Suppress("UNUSED_PARAMETER")
    fun submitInput( view: View?) {
        val input: Double = findViewById<EditText>(R.id.mainTextInput).text.toString().removeTempUnit()
        if (cSelected) convertToC(input) else convertToF(input)
    }


    private fun getFormat(input: Double): Int {
        return if (input.toInt().toDouble() == input) R.string.mainTextDefaultFormat else R.string.mainTextFormat
    }

    private fun convertToF(input: Double) {
        val f = input * (9.0 / 5.0) + 32.0
        tempChange(f, false)
    }

    private fun convertToC(input: Double) {
        val c = (input - 32.0) * (5.0 / 9.0)
        tempChange(c, true)
    }

    private fun tempChange(output: Double, toC: Boolean) {
        val temperatureString = getString(getFormat(output), output, if (toC) 'C' else 'F')
        val hot = if (toC) 35.0 else 95.0
        val n = if (toC) 10.0 else 50.0

        val intent = Intent(this, OutputActivity::class.java).apply {
            putExtra(OUTPUT_STRING, temperatureString)
            putExtra(OUTPUT_TEMPERATURE, if (output > hot) 1 else if (output > n) 0 else -1)
        }
        startActivity(intent)
    }
}