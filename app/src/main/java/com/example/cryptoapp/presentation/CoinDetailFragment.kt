package com.example.cryptoapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailFragment : Fragment() {

    private lateinit var viewModel: CoinViewModel

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fromSymbol = getSymbol()
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner, Observer {
            with(binding) {
                Picasso.get().load(it.imageUrl).into(imageViewCoinDetail)
                textViewSymbolDetail.text =
                    String.format(resources.getString(R.string.symbols_template),
                        it.fromSymbol,
                        it.toSymbol)
                textViewPriceDetail.text = String.format(resources.getString(R.string.detail_price),
                    it.price)
                textViewMin.text =
                    String.format(resources.getString(R.string.detail_min), it.lowDay)
                textViewMax.text =
                    String.format(resources.getString(R.string.detail_max), it.highDay)
                textViewLastMarket.text =
                    String.format(resources.getString(R.string.last_market), it.lastMarket)
                textViewUpdate.text = String.format(
                    resources.getString(R.string.detail_last_update), it.lastUpdate
                )
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getSymbol(): String {
        return requireArguments().getString(EXTRA_FROM_SYMBOL, EMPTY_SYMBOL)
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        fun newInstance(fSymbol: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_FROM_SYMBOL, fSymbol)
                }
            }
        }
    }
}