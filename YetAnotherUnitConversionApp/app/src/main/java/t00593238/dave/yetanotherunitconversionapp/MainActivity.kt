package t00593238.dave.yetanotherunitconversionapp

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.*


class MainActivity : Activity(), AdapterView.OnItemSelectedListener {

    fun String.removeTempUnit(): Double = reversed().substring(1).reversed().toDouble()
    var inputNumber: EditText? = null
    var cSelected: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputNumber = findViewById(R.id.mainTextInput)

        // lambda that controls a listener to the enter key on the keyboard
        inputNumber?.setOnKeyListener(View.OnKeyListener { p0, p1, p2 ->
            if (p2?.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_ENTER) {
                submitInput(p0)
                return@OnKeyListener true
            }
            false
        })

        val spinner: Spinner = findViewById(R.id.spinner)
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

    private fun convertToF(input: Double) {
        val f = input * (9 / 5) + 32
        val end: TextView? = findViewById(R.id.output)
        end?.text = f.toString()
    }

    private fun convertToC(input: Double) {
        val c = (input - 32) * (5 / 9)
        val end: TextView? = findViewById(R.id.output)
        end?.text = c.toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // Determines if using C temp or F temp to convert from (cSelected = true -> using C temp)
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p2 == 0)
            cSelected = true
    }

    fun submitInput(view: View?) {
        val input: Double = this.findViewById<EditText>(R.id.mainTextInput).text.toString().removeTempUnit()
        if (cSelected) convertToF(input) else convertToC(input)
//        button.setBackgroundResource(R.drawable.btn)
//        input.toString()
//        String.format("%s°")
    }
}
