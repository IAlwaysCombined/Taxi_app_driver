package com.example.taxi_app_driver.ui.fragment.driver.end_rides

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi_app_driver.R
import com.example.taxi_app_driver.database.*
import com.example.taxi_app_driver.databinding.FragmentEndRidesBinding
import com.example.taxi_app_driver.models.CommonModel
import com.example.taxi_app_driver.ui.BaseFragment
import com.example.taxi_app_driver.ui.fragment.driver.my_rides.MyRidersAdapter
import com.example.taxi_app_driver.uitlities.AppValueEventListener
import com.google.firebase.database.DatabaseReference


class EndRidesFragment : BaseFragment(R.layout.fragment_end_rides) {

    private lateinit var binding: FragmentEndRidesBinding
    private lateinit var adapter: EndRidesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var refEndRides: DatabaseReference
    private lateinit var endRidesRequestListener: AppValueEventListener
    private var endRidesList = mutableListOf<CommonModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEndRidesBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView = binding.listEndRideRecyclerView
        adapter = EndRidesAdapter(mutableListOf())
        refEndRides = REF_DATABASE_ROOT.child(NODE_RIDES_END_DRIVER).child(UID)
        recyclerView.adapter = adapter
        endRidesRequestListener = AppValueEventListener { dataSnapshot ->
            endRidesList = dataSnapshot.children.map { it.getCommonModel() } as MutableList<CommonModel>
            adapter.setList(endRidesList)
            recyclerView.smoothScrollToPosition(adapter.itemCount)
        }
        refEndRides.addValueEventListener(endRidesRequestListener)
    }


}