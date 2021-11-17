package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.domain.CoinInfo
import kotlinx.android.synthetic.main.activity_coin_price_list.*

private lateinit var viewModel: CoinViewModel

class CoinPriceListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                val intent = CoinDetailActivity.newIntent(this@CoinPriceListActivity,
                    coinPriceInfo.fromSymbol)
                startActivity(intent)
            }
        }
        recyclerViewCoinPriceList.adapter = adapter
        viewModel.coinInfoList.observe(this, Observer {
            adapter.coinInfoList = it
        })
    }

}