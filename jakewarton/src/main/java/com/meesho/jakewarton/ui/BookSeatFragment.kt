package com.meesho.jakewarton.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.zxing.integration.android.IntentIntegrator
import com.meesho.base.extensions.*
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
    private val testQr="\"{\\\"location_id\\\":\\\"ButterKnifeLib-1234\\\",\\\"location_details\\\":\\\"ButterKnife Lib, 80 Feet Rd, Koramangala 1A Block, Bangalore\\\",\\\"price_per_min\\\":5.50}\""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btScanNow.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.bookSeat(testQr)
                }
                //IntentIntegrator.forSupportFragment(this).initiateScan()
            }

            btEndScan.setOnClickListener {
                lifecycleScope.launch {
                    observeSubmitSession()
                }

            }
        }
        observerData()
    }

    private fun observerData() {
        lifecycleScope.launchWhenResumed {
            // updating elapsed time
            observeElapsedTime()

            // updating session status (btScanNow/btEndScan button visibility)
            observeSessionStatus()
        }
    }

    private suspend fun observeSessionStatus() {
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

    private suspend fun observeElapsedTime() {
        viewModel.getElapsedTime().observe(viewLifecycleOwner, {
            when (it) {
                is State.Success<*> -> {
                    val session = it.data as QRScanResult

                    binding.apply {
                        tvLocationId.text = String.format(
                            getString(R.string.location_id),
                            session.location_id
                        )

                        tvAddress.text = String.format(
                            getString(R.string.address),
                            session.location_details
                        )

                        tvPrice.text = String.format(
                            getString(R.string.price),
                            session.price_per_min
                        )

                        tvDuration.text = String.format(
                            getString(R.string.duration),
                            session.hour,
                            session.minute,
                            session.seconds,
                        )

                        tvTotalCharges.text = String.format(
                            getString(R.string.total_charges),
                            session.total_price
                        )

                    }
                }
                is State.ErrorState -> {
                    longToast(it.exception.message)
                }
            }
        })
    }

    private suspend fun observeSubmitSession() {
        viewModel.submit(testQr,System.currentTimeMillis()).observe(viewLifecycleOwner, {
            when (it) {
                is State.LoadingState -> {
                    disableButton()
                    showProgress()
                }

                is State.Success<*> -> {
                    enableButton()
                    hideProgress()
                    shortToast(R.string.submission_success)

                }
                is State.ErrorState -> {
                    enableButton()
                    hideProgress()
                    longToast(it.exception.message)
                }
            }
        })
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

    //####################   UI ##############################//
    private fun enableButton() {
        binding.apply {
            btScanNow.enable()
            btEndScan.enable()
        }
    }

    private fun disableButton() {
        binding.apply {
            btScanNow.disable()
            btEndScan.disable()
        }
    }

    private fun showProgress() {
        binding.groupLoading.visible()
    }

    private fun hideProgress() {
        binding.groupLoading.gone()
    }
}