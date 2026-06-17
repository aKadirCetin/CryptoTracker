package com.kadircetin.cryptotracker.view
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.kadircetin.cryptotracker.R
import com.kadircetin.cryptotracker.adapter.RecyclerViewAdapter
import com.kadircetin.cryptotracker.databinding.ActivityMainBinding
import com.kadircetin.cryptotracker.model.CoinMarketCapResponse
import com.kadircetin.cryptotracker.model.CryptoModel
import com.kadircetin.cryptotracker.service.CryptoAPI

import kotlinx.coroutines.launch

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {
    private lateinit var binding: ActivityMainBinding
    private val BASE_URL="https://pro-api.coinmarketcap.com/"
    private val API_KEY = "31e05670233541aa9d76ef9a8afc6b20"
    private var cryptoModels: ArrayList<CryptoModel> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settings()
        val layoutManager:RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadData();
    }
    private fun settings(){
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loadData(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)

        lifecycleScope.launch {
            try {
                val response = api.getData(API_KEY)
                handleResponse(response)
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(this@MainActivity,e.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun handleResponse(response: CoinMarketCapResponse){
        cryptoModels.clear()
        cryptoModels.addAll(response.data)
        binding.recyclerView.adapter = RecyclerViewAdapter(cryptoModels,this)
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked: ${cryptoModel.name}",Toast.LENGTH_LONG).show()
    }

}