package com.easyswitch.weatherapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easyswitch.weatherapp.R
import com.easyswitch.weatherapp.adapter.DayAdapter.DayHolder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import java.util.*

class DayAdapter(private val context: Context, private val hourList: ArrayList<String>, private val tempList: ArrayList<Double>) :
    RecyclerView.Adapter<DayHolder>() {
    @SuppressLint("SimpleDateFormat")
    private val oldFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH)
    @SuppressLint("SimpleDateFormat")
    private val newFormat = SimpleDateFormat("HH", Locale.ENGLISH)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        return DayHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_day, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        val time = getDayStringFromDate(hourList.get(position))

        holder.tvTime.text = time
        holder.tvTemperature.text =  String.format("%.0f", tempList[position]).plus("°")
    }

    override fun getItemCount(): Int {
        return 24
    }

    inner class DayHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTime: TextView
        val tvTemperature: TextView
        val ivIcon: ImageView

        init {
            tvTime = itemView.findViewById(R.id.tvTime)
            tvTemperature = itemView.findViewById(R.id.tvTemperature)
            ivIcon = itemView.findViewById(R.id.ivIcon)
        }
    }

    private fun getDayStringFromDate(date: String): String? {
        try {
            val d = oldFormat.parse(date)
            return newFormat.format(d)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}