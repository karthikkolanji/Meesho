package com.meesho.jakewarton.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.zxing.integration.android.IntentIntegrator
import com.meesho.base.extensions.gone
import com.meesho.base.extensions.shortToast
import com.meesho.base.extensions.viewLifecycleScoped
import com.meesho.base.extensions.visible
import com.meesho.base.utils.State
import com.meesho.jakewarton.R
import com.meesho.jakewarton.data.entity.QRScanResult
import com.meesho.jakewarton.databinding.FragmentBookSeatBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookSeatFragment : Fragment(R.layout.fragment_book_seat) {

    private val binding: FragmentBookSeatBinding by viewLifecycleScoped(FragmentBookSeatBinding::bind)
    private val viewModel: BookSeatViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btScanNow.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.bookSeat("\"{\\\"location_id\\\":\\\"ButterKnifeLib-1234\\\",\\\"location_details\\\":\\\"ButterKnife Lib, 80 Feet Rd, Koramangala 1A Block, Bangalore\\\",\\\"price_per_min\\\":5.50}\"")
                }
                //IntentIntegrator.forSupportFragment(this).initiateScan()
            }

            btEndScan.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.submit()
                }

            }
        }



        observerElapsedTime()

    }

    private fun observerElapsedTime() {

        lifecycleScope.launch {
            // updating elapsed time
            viewModel.getElapsedTime().observe(viewLifecycleOwner, {
                when (it) {
                    is State.Success<*> -> {
                        val time = it.data as QRScanResult
                        binding.tvLocationDetails.text =
                            "h:${time.hour} m:${time.minute} s:${time.seconds}"

                    }
                    is State.ErrorState -> {
                        shortToast(it.exception.message)
                    }
                }
            })

            // updating session status (btScanNow/btEndScan button visibility)
            viewModel.getSessionStatus().observe(viewLifecycleOwner, { active ->
                binding.apply {
                    if (active) {
                        btScanNow.gone()
                        btEndScan.visible()
                    } else {
                        btScanNow.visible()
                        btEndScan.gone()
                    }

                }
            })

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result == null) {
            super.onActivityResult(requestCode, resultCode, data)
        } else {
            if (result.contents == null) {
                shortToast(R.string.qr_read_error)
            } else {
                lifecycleScope.launch {
                    // viewModel.parseScanResult(result.contents)
                    // viewModel.parseScanResult("\"{\\\"location_id\\\":\\\"ButterKnifeLib-1234\\\",\\\"location_details\\\":\\\"ButterKnife Lib, 80 Feet Rd, Koramangala 1A Block, Bangalore\\\",\\\"price_per_min\\\":5.50}\"")
                }
                Log.d("onActivityResult", "${result.contents}")
                shortToast(result.contents)
            }
        }
    }
}