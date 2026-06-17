package com.kadircetin.cryptotracker.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kadircetin.cryptotracker.databinding.RecyclerRowBinding
import com.kadircetin.cryptotracker.model.CryptoModel

class RecyclerViewAdapter( private val cryptoList: ArrayList<CryptoModel>, private val listener: Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {
    private val colors: Array<String> = arrayOf("#008000","#0000FF","#FF00FF","#808000","#800000","#FFBF00","#8B8000","#F4BB44")

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): RecyclerViewAdapter.RowHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.RowHolder, position: Int) {
        holder.bind(cryptoList[position],colors,position,listener)
    }

    override fun getItemCount(): Int {
        return cryptoList.size;
    }

    interface Listener { fun onItemClick(cryptoModel: CryptoModel) }

    class RowHolder(private val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cryptoModel: CryptoModel, colors: Array<String>, position: Int, listener: Listener ) {
            binding.root.setBackgroundColor(Color.parseColor(colors[position%colors.size]))
            binding.textName.text = "${cryptoModel.name} (${cryptoModel.symbol})"
            binding.textPrice.text ="$ %.2f".format(cryptoModel.quote.USD.price)

            binding.root.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }

        }

    }
}