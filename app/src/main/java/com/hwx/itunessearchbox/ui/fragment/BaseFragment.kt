package com.hwx.itunessearchbox.ui.fragment

import android.text.Html
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.hwx.itunessearchbox.R
import dagger.android.support.DaggerFragment

/**
 * Базовый фрагмент
 */
open class BaseFragment: DaggerFragment() {

    private var snackbar: Snackbar? = null

    fun showErrorWithAction(view: View, message: String, action: () -> Unit) {
        val htmlText = Html.fromHtml("<font color=\"#ffffff\">$message</font>")
        snackbar = Snackbar
            .make(view, htmlText, Snackbar.LENGTH_INDEFINITE)
            .apply {
                setAction(view.context.getString(R.string.retry_value)) { action() }
                show()
            }
    }

    fun hideErrorIfShowed() {
        snackbar?.dismiss()
    }
}