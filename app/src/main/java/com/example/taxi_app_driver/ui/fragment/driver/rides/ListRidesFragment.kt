package com.example.taxi_app_driver.ui.fragment.driver.rides

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi_app_driver.R
import com.example.taxi_app_driver.database.NODE_PRE_RIDES
import com.example.taxi_app_driver.database.REF_DATABASE_ROOT
import com.example.taxi_app_driver.database.getCommonModel
import com.example.taxi_app_driver.databinding.FragmentListRidesBinding
import com.example.taxi_app_driver.models.CommonModel
import com.example.taxi_app_driver.uitlities.APP_ACTIVITY
import com.example.taxi_app_driver.uitlities.AppValueEventListener
import com.example.taxi_app_driver.uitlities.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DatabaseReference
import com.mikepenz.iconics.Iconics.applicationContext

@Suppress("DEPRECATION")
class ListRidesFragment : Fragment(R.layout.fragment_list_rides) {

    private lateinit var binding: FragmentListRidesBinding
    private lateinit var adapter: RidesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var refRides: DatabaseReference
    private lateinit var ridesRequestListener: AppValueEventListener
    private var ridesList = mutableListOf<CommonModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListRidesBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    //Initialize recycler view element
    private fun initRecyclerView() {
        recyclerView = binding.listRideRecyclerView
        adapter = RidesAdapter(mutableListOf())
        refRides = REF_DATABASE_ROOT.child(NODE_PRE_RIDES)
        recyclerView.adapter = adapter
        ridesRequestListener = AppValueEventListener { dataSnapshot ->
            ridesList = dataSnapshot.children.map { it.getCommonModel() }.toMutableList()
            adapter.setList(ridesList)
            recyclerView.smoothScrollToPosition(adapter.itemCount)
        }
        refRides.addValueEventListener(ridesRequestListener)
    }
}