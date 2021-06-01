package com.example.taxi_app_driver.ui.fragment.driver.my_rides

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi_app_driver.R
import com.example.taxi_app_driver.database.NODE_RIDES_DRIVER
import com.example.taxi_app_driver.database.REF_DATABASE_ROOT
import com.example.taxi_app_driver.database.UID
import com.example.taxi_app_driver.database.getCommonModel
import com.example.taxi_app_driver.databinding.FragmentMyRidesBinding
import com.example.taxi_app_driver.models.CommonModel
import com.example.taxi_app_driver.ui.BaseFragment
import com.example.taxi_app_driver.uitlities.AppValueEventListener
import com.google.firebase.database.DatabaseReference

class MyRidesFragment : BaseFragment(R.layout.fragment_my_rides) {

    private lateinit var binding: FragmentMyRidesBinding
    private lateinit var adapter: MyRidersAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var refMyRides: DatabaseReference
    private lateinit var myRidesRequestListener: AppValueEventListener
    private var myRidesList = mutableListOf<CommonModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyRidesBinding.bind(view)
        initRecyclerView()
    }

    //Initialize recycler view element
    private fun initRecyclerView(){
        recyclerView = binding.listMyRideRecyclerView
        adapter = MyRidersAdapter(mutableListOf())
        refMyRides = REF_DATABASE_ROOT.child(NODE_RIDES_DRIVER).child(UID)
        recyclerView.adapter = adapter
        myRidesRequestListener = AppValueEventListener { dataSnapshot ->
            myRidesList = dataSnapshot.children.map { it.getCommonModel() } as MutableList<CommonModel>
            adapter.setList(myRidesList)
            recyclerView.smoothScrollToPosition(adapter.itemCount)
        }
        refMyRides.addValueEventListener(myRidesRequestListener)
    }
}