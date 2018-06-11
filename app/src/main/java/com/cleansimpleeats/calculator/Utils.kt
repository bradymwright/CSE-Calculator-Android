package com.cleansimpleeats.calculator

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

fun <T> Spinner.setItemAdapter(items: List<T>, hint: String) {
    val spinnerItems = items.map { it.toString() }
    val spinnerItemsWithHint = spinnerItems.toMutableList().apply { add(0, hint) }
    adapter = object : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerItemsWithHint) {
        init {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        override fun isEnabled(position: Int) = position != 0

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View =
            (super.getDropDownView(position, convertView, parent) as TextView).apply {
                setTextColor(if (position == 0) Color.GRAY else ContextCompat.getColor(context, R.color.cse_black))
            }
    }
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) = Unit

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        }

    }

}