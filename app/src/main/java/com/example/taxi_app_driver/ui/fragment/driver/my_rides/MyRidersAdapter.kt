package com.example.taxi_app_driver.ui.fragment.driver.my_rides

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
import com.example.taxi_app_driver.uitlities.APP_ACTIVITY
import com.example.taxi_app_driver.uitlities.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MyRidersAdapter(private var myRideList: MutableList<CommonModel>) :
    RecyclerView.Adapter<MyRidersAdapter.MyRidesViewHolder>() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location
    var locationUser = ""

    class MyRidesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameRider: TextView? = itemView.findViewById(R.id.name_rider_my_ride_item)
        val phoneRider: TextView? = itemView.findViewById(R.id.phone_number_my_rider_item)
        val payMethod: TextView? = itemView.findViewById(R.id.pay_method_my_ride_item)
        val coastRide: TextView? = itemView.findViewById(R.id.coast_my_ride_item)
        val startLocation: TextView? = itemView.findViewById(R.id.start_location_my_ride_item)
        val endLocation: TextView? = itemView.findViewById(R.id.end_location_my_ride_item)
        val stopLocation: TextView? = itemView.findViewById(R.id.stop_location_my_ride_item)
        val endRide: Button? = itemView.findViewById(R.id.end_ride)
        val distanceRide: Button? = itemView.findViewById(R.id.distance_ride)
        val routeRiderRide: Button? = itemView.findViewById(R.id.route_user_ride)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRidesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_my_rides, parent, false)
        return MyRidesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyRidesViewHolder, position: Int) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(APP_ACTIVITY)
        getLocationUser()
        holder.nameRider?.text = myRideList[position].name_user
        holder.phoneRider?.text = myRideList[position].phone_user
        holder.payMethod?.text = myRideList[position].pay_method
        holder.coastRide?.text = myRideList[position].coast_ride
        holder.startLocation?.text = myRideList[position].start_ride
        holder.endLocation?.text = myRideList[position].end_ride
        holder.stopLocation?.text = myRideList[position].point_ride
        holder.endRide?.setOnClickListener {

            val dateMap = mutableMapOf<String, Any>()
            dateMap[CHILD_START] = myRideList[position].start_ride
            dateMap[CHILD_END] = myRideList[position].end_ride
            if (myRideList[position].point_ride.isNotEmpty()) {
                dateMap[CHILD_STOP] = myRideList[position].point_ride
            }
            dateMap[CHILD_COAST] = myRideList[position].coast_ride
            dateMap[CHILD_PHONE] = myRideList[position].phone_user
            dateMap[CHILD_NAME_USER] = myRideList[position].name_user
            dateMap[CHILD_PAY_METHOD] = myRideList[position].pay_method
            dateMap[CHILD_NAME_DRIVER] = DRIVER.name_driver
            dateMap[CHILD_SURNAME_DRIVER] = DRIVER.surname_driver
            dateMap[CHILD_LASTNAME_DRIVER] = DRIVER.last_name_driver
            dateMap[CHILD_PHONE_DRIVER] = DRIVER.phone_number_driver

            val keyEndRides = REF_DATABASE_ROOT.child(NODE_RIDES_END_DRIVER).child(UID).push().key.toString()

            REF_DATABASE_ROOT.child(NODE_RIDES_END_DRIVER).child(UID).child(keyEndRides).updateChildren(dateMap)
            REF_DATABASE_ROOT.child(NODE_RIDES_FOR_ADMIN).child(keyEndRides).updateChildren(dateMap)
            REF_DATABASE_ROOT.child(NODE_RIDES_DRIVER).child(UID).removeValue()
            showToast("Поездка завершена")
        }
        holder.distanceRide?.setOnClickListener {
            try {
                if (myRideList[position].point_ride.isEmpty()){
                    val uri = Uri.parse("https://www.google.co.in/maps/dir/" + myRideList[position].start_ride + "/" + myRideList[position].end_ride)

                    val intent = Intent(Intent.ACTION_VIEW,uri)

                    intent.setPackage("com.google.android.apps.maps")

                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                    APP_ACTIVITY.startActivity(intent)
                }
                //+ "|" + myRideList[position].center_ride +
                else{
                    val uri = Uri.parse("https://www.google.co.in/maps/dir/" + myRideList[position].start_ride + "/" + myRideList[position].end_ride + "/" + myRideList[position].point_ride)

                    val intent = Intent(Intent.ACTION_VIEW,uri)

                    intent.setPackage("com.google.android.apps.maps")

                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                    APP_ACTIVITY.startActivity(intent)
                }


            }catch (e: ActivityNotFoundException){
                val uri = Uri.parse("https://play.google.com/store/apps/detalis?id=com.google.android.apps.maps")

                val intent = Intent(Intent.ACTION_VIEW,uri)

                intent.setPackage("com.google.android.apps.maps")

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                APP_ACTIVITY.startActivity(intent)
            }
        }
        holder.routeRiderRide?.setOnClickListener {
            try {
                val uri =
                    Uri.parse("https://www.google.co.in/maps/dir/" + locationUser + "/" + myRideList[position].start_ride)

                val intent = Intent(Intent.ACTION_VIEW, uri)

                intent.setPackage("com.google.android.apps.maps")

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                APP_ACTIVITY.startActivity(intent)

            } catch (e: ActivityNotFoundException) {
                val uri =
                    Uri.parse("https://play.google.com/store/apps/detalis?id=com.google.android.apps.maps")

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

    override fun getItemCount() = myRideList.size

    fun setList(list: List<CommonModel>) {
        myRideList = list as MutableList<CommonModel>
        notifyDataSetChanged()
    }
}