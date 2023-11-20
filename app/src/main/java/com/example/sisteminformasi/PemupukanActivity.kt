package com.example.sisteminformasi

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.sisteminformasi.databinding.ActivityPemupukanBinding
import java.text.SimpleDateFormat
import java.util.*

class PemupukanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPemupukanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPemupukanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayDropDownAsalPupuk()
        displayDate()
    }

    private fun displayDate() {
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            updateLabel(myCalendar)
        }

        binding.tanggalPempupukanET.setOnClickListener {
            val dPicker = DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(
                Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
            dPicker.datePicker.minDate = System.currentTimeMillis()
            dPicker.show()
        }
    }
    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(myFormat, Locale.UK)
        binding.tanggalPempupukanET.setText(simpleDateFormat.format(myCalendar.time))
    }

    private fun displayDropDownAsalPupuk() {
        val itemsAsalBenih = listOf("Mandiri", "Bantuan Pemerintah")
        val adapterAsalBenih = ArrayAdapter(this, R.layout.item_list_dropdown, itemsAsalBenih)
        binding.asalPupukDropDown.setAdapter(adapterAsalBenih)
    }
}