package com.achmadabrar.githubapi.presentation.activity

import android.os.Bundle
import android.view.View
import com.achmadabrar.gitgubapi.core.base.BaseActivity
import com.achmadabrar.githubapi.R
import com.achmadabrar.githubapi.core.base.AlarmReceiver
import com.achmadabrar.githubapi.presentation.fragment.DatePickerFragment
import com.achmadabrar.githubapi.presentation.fragment.TimePicker
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.toolbar_settings
import kotlinx.android.synthetic.main.activity_settings.tv_once_date
import java.text.SimpleDateFormat
import java.util.*

class SettingsActivity : BaseActivity(), View.OnClickListener, DatePickerFragment.Listener,
    TimePicker.Listener {

    private lateinit var alarmRecevier: AlarmReceiver

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePikcerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(toolbar_settings)
        supportActionBar?.title = "Set Alarm"
        btn_once_date.setOnClickListener(this)
        btn_once_time.setOnClickListener(this)
        btn_set_once_alarm.setOnClickListener(this)

        btn_repeating_time.setOnClickListener(this)
        btn_set_repeating_alarm.setOnClickListener(this)

        btn_cancel_repeating_alarm.setOnClickListener(this)

        alarmRecevier = AlarmReceiver()
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btn_once_date -> {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
            }

            R.id.btn_once_time -> {
                val timePickerFragment = TimePicker()
                timePickerFragment.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
            }
            R.id.btn_set_once_alarm -> {
                val onceDate = tv_once_date.text.toString()
                val onceTime = tv_once_time.text.toString()
                val onceMessage = edt_once_message.text.toString()

                alarmRecevier.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME, onceDate, onceTime, onceMessage)
            }
            R.id.btn_repeating_time -> {
                val timePickerRepeat = TimePicker()
                timePickerRepeat.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)
            }

            R.id.btn_set_repeating_alarm -> {
                val repeatTime = tv_repeating_time.text.toString()
                val repeatMessage = edt_repeating_message.text.toString()
                alarmRecevier.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING, repeatTime, repeatMessage)
            }

            R.id.btn_cancel_repeating_alarm -> {
                alarmRecevier.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)
            }
        }
    }

    override fun onDialogSet(tag: String?, year: Int, month: Int, dayOfMont: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMont)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        tv_once_date.text = dateFormat.format(calendar.time)
    }

    override fun onDialogtimeSet(tag: String, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        when(tag) {
            TIME_PICKER_ONCE_TAG -> { tv_once_time.text = dateFormat.format(calendar.time) }
            TIME_PICKER_REPEAT_TAG -> { tv_repeating_time.text = dateFormat.format(calendar.time) }
            else -> {}
        }
    }
}