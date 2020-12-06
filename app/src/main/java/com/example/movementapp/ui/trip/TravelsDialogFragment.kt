package com.example.movementapp.ui.trip

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.movementapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TravelsDialogFragment : BottomSheetDialogFragment(), DialogRecyclerViewAdapter.ItemClickListener {

    private lateinit var homeViewModel: BottomTravelsViewModel
    private var adapter: DialogRecyclerViewAdapter? = null
    private lateinit var navController: NavController


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(BottomTravelsViewModel::class.java)
        val root = inflater.inflate(R.layout.bottom_sheet_travels, container, false)
        navController = Navigation.findNavController(context as Activity, R.id.bottom_sheet_travel_fragment)

        // data to populate the RecyclerView with

        // data to populate the RecyclerView with
        val viewColors: ArrayList<Int> = ArrayList()
        viewColors.add(Color.BLUE)
        viewColors.add(Color.YELLOW)
        viewColors.add(Color.MAGENTA)
        viewColors.add(Color.RED)
        viewColors.add(Color.BLACK)

        val animalNames: ArrayList<String> = ArrayList()
        animalNames.add("Horse")
        animalNames.add("Cow")
        animalNames.add("Camel")
        animalNames.add("Sheep")
        animalNames.add("Goat")
        // set up the RecyclerView

        // set up the RecyclerView
        val recyclerView: ViewPager2 = root.findViewById(R.id.travelsView)
        val horizontalLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        adapter = DialogRecyclerViewAdapter(requireContext(), viewColors, animalNames)
        adapter!!.setClickListener(this)
        recyclerView.adapter = adapter

        return root
    }

    override fun onItemClick(view: View?, position: Int) {
        Toast.makeText(
            requireContext(),
            "You clicked " + adapter!!.getItem(position) + " on item position " + position,
            Toast.LENGTH_SHORT
        ).show()
        navController.navigate(R.id.action_bottom_sheet_travels_to_bottom_sheet_travel_page)
    }

}
