package com.example.taxi_app_driver.ui.fragment.driver.rides

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi_app_driver.R
import com.example.taxi_app_driver.database.*
import com.example.taxi_app_driver.models.CommonModel
import com.example.taxi_app_driver.ui.fragment.driver.my_rides.MyRidesFragment
import com.example.taxi_app_driver.uitlities.APP_ACTIVITY
import com.example.taxi_app_driver.uitlities.replaceFragment
import com.example.taxi_app_driver.uitlities.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RidesAdapter(private var rideList: MutableList<CommonModel>) :
    RecyclerView.Adapter<RidesAdapter.RideViewHolder>() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location
    var locationUser = ""

    class RideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Ride data
        val nameRider: TextView? = itemView.findViewById(R.id.name_rider_item)
        val phoneRider: TextView? = itemView.findViewById(R.id.phone_number_rider_item)
        val payMethod: TextView? = itemView.findViewById(R.id.pay_method_item)
        val coastRide: TextView? = itemView.findViewById(R.id.coast_ride_item)
        val startLocation: TextView? = itemView.findViewById(R.id.start_location_item)
        val endLocation: TextView? = itemView.findViewById(R.id.end_location_item)
        val stopLocation: TextView? = itemView.findViewById(R.id.stop_location_item)
        val acceptRide: Button? = itemView.findViewById(R.id.accept_ride)
        val routeRiderRide: Button? = itemView.findViewById(R.id.route_ride)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_rides, parent, false)
        return RideViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(APP_ACTIVITY)
        getLocationUser()
        holder.nameRider?.text = rideList[position].name_user
        holder.phoneRider?.text = rideList[position].phone_user
        holder.payMethod?.text = rideList[position].pay_method
        holder.coastRide?.text = rideList[position].coast_ride
        holder.startLocation?.text = rideList[position].start_ride
        holder.endLocation?.text = rideList[position].end_ride
        holder.stopLocation?.text = rideList[position].point_ride
        holder.acceptRide?.setOnClickListener {

            val dateMap = mutableMapOf<String, Any>()
            //User data
            dateMap[CHILD_START] = rideList[position].start_ride
            dateMap[CHILD_END] = rideList[position].end_ride
            if (rideList[position].point_ride.isNotEmpty()) {
                dateMap[CHILD_STOP] = rideList[position].point_ride
            }
            dateMap[CHILD_COAST] = rideList[position].coast_ride
            dateMap[CHILD_PHONE] = rideList[position].phone_user
            dateMap[CHILD_NAME_USER] = rideList[position].name_user
            dateMap[CHILD_PAY_METHOD] = rideList[position].pay_method

            //Driver data
            dateMap[CHILD_NAME_DRIVER] = DRIVER.name_driver
            dateMap[CHILD_SURNAME_DRIVER] = DRIVER.surname_driver
            dateMap[CHILD_LASTNAME_DRIVER] = DRIVER.last_name_driver
            dateMap[CHILD_PHONE_DRIVER] = DRIVER.phone_number_driver
            dateMap[CHILD_PHOTO_DRIVER] = DRIVER.photo_driver

            val keyRidesDriver = REF_DATABASE_ROOT.child(NODE_RIDES).child(UID).push().key.toString()
            REF_DATABASE_ROOT.child(NODE_RIDES_DRIVER).child(UID).child(keyRidesDriver)
                .updateChildren(dateMap)
                .addOnCompleteListener {
                    val keyRidesRider =
                        REF_DATABASE_ROOT.child(NODE_RIDES).child(rideList[position].uid)
                            .push().key.toString()
                    REF_DATABASE_ROOT.child(NODE_RIDES).child(rideList[position].uid)
                        .child(keyRidesRider)
                        .updateChildren(dateMap)
                        .addOnCompleteListener {
                            showToast("Заказ принят")
                            REF_DATABASE_ROOT.child(NODE_PRE_RIDES).child(rideList[position].uid).removeValue()
                            APP_ACTIVITY.replaceFragment(MyRidesFragment())
                        }
                }
        }
        holder.routeRiderRide?.setOnClickListener {
            try {
                val uri = Uri.parse("https://www.google.co.in/maps/dir/" + locationUser + "/" + rideList[position].start_ride)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                APP_ACTIVITY.startActivity(intent)

            } catch (e: ActivityNotFoundException) {
                val uri = Uri.parse("https://play.google.com/store/apps/detalis?id=com.google.android.apps.maps")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                APP_ACTIVITY.startActivity(intent)
            }
        }
    }

    //Get user location
    private fun getLocationUser(){
        if (ActivityCompat.checkSelfPermission(
                APP_ACTIVITY,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                APP_ACTIVITY,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if(location != null){
                    currentLocation = location
                    locationUser = currentLocation.latitude.toString() + ", " + currentLocation.longitude.toString()
                }
            }
    }

    override fun getItemCount() = rideList.size

    fun setList(list: List<CommonModel>) {
        rideList = list as MutableList<CommonModel>
        notifyDataSetChanged()
    }

}