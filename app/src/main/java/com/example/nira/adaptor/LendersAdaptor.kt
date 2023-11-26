package com.example.nira.adaptor

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nira.R
import com.example.nira.database.LenderEntity
import com.example.nira.utils.constants
import com.example.nira.views.EMICalculateActivity
import com.example.nira.views.SelectLendersActivity

class LendersAdaptor(private var data: List<LenderEntity>):
    RecyclerView.Adapter<LendersAdaptor.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lenderName = view.findViewById<TextView>(R.id.lender_name_tv)
        val lenderRate = view.findViewById<TextView>(R.id.lender_interest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lender_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.lenderName.text = data[position].lenderName
        holder.lenderRate.text = data[position].interestRate.toString() + "% / Month"

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EMICalculateActivity::class.java)
            intent.putExtra(constants.EXTRA_LENDER_NAME , data[position].lenderName)
            intent.putExtra(constants.EXTRA_INTERESTRATE , data[position].interestRate.toString())
            intent.putExtra(constants.EXTRA_LENDER_ID , data[position].id.toString())
            it.context.startActivity(intent)
        }
    }

    fun setData(newData: List<LenderEntity>){
        data = newData
        notifyDataSetChanged()
    }

}