package com.cleansimpleeats.calculator

import android.R
import android.widget.ArrayAdapter
import android.widget.Spinner

fun <T> Spinner.setItemAdapter(items: List<T>) {
    val spinnerItems = items.map { it.toString() }
    adapter = ArrayAdapter(context, R.layout.simple_spinner_item, spinnerItems).apply {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
}