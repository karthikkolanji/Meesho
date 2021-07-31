package com.meesho.jakewarton.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.meesho.base.extensions.viewLifecycleScoped
import com.meesho.jakewarton.R
import com.meesho.jakewarton.databinding.FragmentBookSeatBinding

class BookSeatFragment:Fragment(R.layout.fragment_book_seat) {

    private val binding: FragmentBookSeatBinding by viewLifecycleScoped(FragmentBookSeatBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btBookSeat.setOnClickListener {
        }
    }
}