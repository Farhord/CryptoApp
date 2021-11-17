package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter
import com.example.cryptoapp.domain.CoinInfo
import com.example.cryptoapp.presentation.adapters.CoinInfoViewHolder
import kotlinx.coroutines.delay

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    private var fragmentOnScreen = false

    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                if (isOnePaneMode()) {
                    launchDetailActivity(coinPriceInfo.fromSymbol)
                } else {
                    launchDetailFragment(coinPriceInfo.fromSymbol)
                }
            }
        }
        binding.recyclerViewCoinPriceList.adapter = adapter
        binding.recyclerViewCoinPriceList.itemAnimator = null
        viewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
            if (!isOnePaneMode() && fragmentOnScreen == false) {
                launchDetailFragment(adapter.currentList.get(0).fromSymbol)
            }
        }


    }

    private fun isOnePaneMode() = binding.fragmentContainer == null

    private fun launchDetailActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(this@CoinPriceListActivity,
            fromSymbol)
        startActivity(intent)
    }

    private fun launchDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .commit()
        fragmentOnScreen = true
    }

    companion object {
        private const val DEFAULT_DETAIL_COIN = "BTC"
    }
}