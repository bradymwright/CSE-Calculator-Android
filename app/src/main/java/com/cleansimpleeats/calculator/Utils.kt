package com.cleansimpleeats.calculator

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.get

fun <T> Spinner.setItemAdapter(items: List<T>, hint: String) {
    fun getTextColor(position: Int) =
        if (position == 0) Color.GRAY
        else ContextCompat.getColor(context, R.color.cse_black)

    val spinnerItems = items.map { it.toString() }
    val spinnerItemsWithHint = spinnerItems.toMutableList().apply { add(0, hint) }
    adapter = object : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerItemsWithHint) {
        init {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        override fun isEnabled(position: Int) = position != 0

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View =
            (super.getDropDownView(position, convertView, parent) as TextView).apply {
                setTextColor(getTextColor(position))
            }
    }
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            setTextColor(Color.GRAY)
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            setTextColor(getTextColor(position))
        }

    }

}

private fun Spinner.setTextColor(color: Int) {
    (this[0] as TextView).setTextColor(color)
}