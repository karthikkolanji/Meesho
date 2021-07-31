package com.meesho.jakewarton.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator
import com.meesho.base.extensions.shortToast
import com.meesho.base.extensions.viewLifecycleScoped
import com.meesho.jakewarton.R
import com.meesho.jakewarton.databinding.FragmentBookSeatBinding

class BookSeatFragment : Fragment(R.layout.fragment_book_seat) {

    private val binding: FragmentBookSeatBinding by viewLifecycleScoped(FragmentBookSeatBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btBookSeat.setOnClickListener {
            IntentIntegrator.forSupportFragment(this).initiateScan()
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
                shortToast(result.contents)
            }
        }
    }
}