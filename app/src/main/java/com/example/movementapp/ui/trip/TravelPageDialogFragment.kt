package com.example.movementapp.ui.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.movementapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TravelPageDialogFragment : BottomSheetDialogFragment() {

    private lateinit var homeViewModel: BottomTravelPageViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(BottomTravelPageViewModel::class.java)
        val root = inflater.inflate(R.layout.bottom_sheet_travel_page, container, false)
        return root
    }

}
