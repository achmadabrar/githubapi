package com.achmadabrar.githubapi.presentation.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*


/**
 * Abrar
 */
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var listener: Listener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener?
    }

    override fun onDetach() {
        super.onDetach()
        if (listener != null) {
            listener = null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DATE)

        return DatePickerDialog(activity as Context, this, year, month, date)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMont: Int) {
        listener?.onDialogSet(tag, year, month, dayOfMont)
    }

    interface Listener {
        fun onDialogSet(tag: String?, year: Int, month: Int, dayOfMont: Int)
    }


}