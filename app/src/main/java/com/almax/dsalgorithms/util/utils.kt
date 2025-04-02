package com.almax.dsalgorithms.util

import android.view.View

fun View.updateVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}