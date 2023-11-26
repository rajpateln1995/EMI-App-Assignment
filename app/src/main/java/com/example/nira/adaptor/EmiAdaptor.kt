package com.example.nira.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.nira.R
import com.example.nira.database.AppDatabase
import com.example.nira.database.EMIEntity
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Date

class EmiAdaptor(private var data: List<EMIEntity>):
    RecyclerView.Adapter<EmiAdaptor.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val installment = view.findViewById<TextView>(R.id.installment_tv)
        val date = view.findViewById<TextView>(R.id.date_tv)
        val pay_btn = view.findViewById<TextView>(R.id.pay_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.emi_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val emiData = data[position]
        holder.installment.text = holder.installment.context.getString(R.string.installment_text, emiData.emiAmount.toString())
        holder.date.text = formatDate(emiData.Date)
        if (emiData.paid) {
            holder.pay_btn.visibility = View.GONE
            holder.itemView.alpha = 0.4f
            holder.pay_btn.isClickable = false
        } else {
            holder.pay_btn.visibility = View.VISIBLE
            holder.itemView.alpha = 1f
            holder.pay_btn.isClickable = true
            holder.pay_btn.setOnClickListener {
                it.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                    AppDatabase.getInstance(it.context).getEMIDao().updateEmiStatus(emiData.id, true)
                }
                Toast.makeText(it.context, "EMI paid Successfully!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setData(newData: List<EMIEntity>){
        data = newData
        notifyDataSetChanged()
    }


    fun formatDate(time: Long): String {
        return DateFormat.getDateTimeInstance().format(Date(time))
    }

}