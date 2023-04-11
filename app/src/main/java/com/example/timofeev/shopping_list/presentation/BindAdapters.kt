package com.example.timofeev.shopping_list.presentation

import androidx.databinding.BindingAdapter
import com.example.timofeev.shopping_list.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, errorInputName: Boolean) {
  val message = if (errorInputName) {
    textInputLayout.context.getString(R.string.error_input_name)
  } else {
    null
  }
  textInputLayout.error = message
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, errorInputCount: Boolean) {
  val message = if (errorInputCount) {
    textInputLayout.context.getString(R.string.error_input_count)
  } else {
    null
  }
  textInputLayout.error = message
}