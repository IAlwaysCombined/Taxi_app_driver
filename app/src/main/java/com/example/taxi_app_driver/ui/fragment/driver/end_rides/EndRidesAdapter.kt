package com.example.taxi_app_driver.ui.fragment.driver.end_rides

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi_app_driver.R
import com.example.taxi_app_driver.models.CommonModel

class EndRidesAdapter(private var endRideList: MutableList<CommonModel>):
    RecyclerView.Adapter<EndRidesAdapter.EndRidesViewHolder>() {

    class EndRidesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameRider: TextView? = itemView.findViewById(R.id.name_rider_end_ride_item)
        val phoneRider: TextView? = itemView.findViewById(R.id.phone_number_end_rider_item)
        val payMethod: TextView? = itemView.findViewById(R.id.pay_method_end_ride_item)
        val coastRide: TextView? = itemView.findViewById(R.id.coast_end_ride_item)
        val startLocation: TextView? = itemView.findViewById(R.id.start_location_end_ride_item)
        val endLocation: TextView? = itemView.findViewById(R.id.end_location_end_ride_item)
        val stopLocation: TextView? = itemView.findViewById(R.id.stop_location_end_ride_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EndRidesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_end_ride, parent, false)
        return EndRidesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EndRidesViewHolder, position: Int) {
        holder.nameRider?.text = endRideList[position].name_user
        holder.phoneRider?.text = endRideList[position].phone_user
        holder.payMethod?.text = endRideList[position].pay_method
        holder.coastRide?.text = endRideList[position].coast_ride
        holder.startLocation?.text = endRideList[position].start_ride
        holder.endLocation?.text = endRideList[position].end_ride
        holder.stopLocation?.text = endRideList[position].point_ride
    }

    override fun getItemCount() = endRideList.size

    fun setList(list: List<CommonModel>) {
        endRideList = list as MutableList<CommonModel>
        notifyDataSetChanged()
    }

}