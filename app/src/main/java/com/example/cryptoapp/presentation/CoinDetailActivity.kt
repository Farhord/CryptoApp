package com.example.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.data.network.ApiFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_detail.*

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        fromSymbol?.let {
            viewModel.getDetailInfo(fromSymbol).observe(this, Observer {
                Picasso.get().load(it.imageUrl).into(imageViewCoinDetail)
                textViewSymbolDetail.text =
                    String.format(resources.getString(R.string.symbols_template),
                        it.fromSymbol,
                        it.toSymbol)
                textViewPriceDetail.text = String.format(resources.getString(R.string.detail_price),
                    it.price)
                textViewMin.text = String.format(resources.getString(R.string.detail_min), it.lowDay)
                textViewMax.text = String.format(resources.getString(R.string.detail_max), it.highDay)
                textViewLastMarket.text = String.format(resources.getString(R.string.last_market), it.lastMarket)
                textViewUpdate.text = String.format(
                    resources.getString(R.string.detail_last_update),it.lastUpdate
                )
            })
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fSymbol)
            return intent
        }
    }
}