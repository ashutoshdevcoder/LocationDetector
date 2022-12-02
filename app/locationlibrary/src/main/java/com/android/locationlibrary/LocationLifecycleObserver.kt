package com.android.locationlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener

class LocationLifecycleObserver(
    private val mContext: Context) : DefaultLifecycleObserver {
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationRequest: LocationRequest
    private val DEFAULT_INTERVAL_IN_MILLISECONDS = 10000L   // default 1 sec/1000 msec - 1000L
    val locationLiveData = MutableLiveData<Location>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext)
        mLocationRequest =
            LocationRequest.create().setInterval(DEFAULT_INTERVAL_IN_MILLISECONDS).setFastestInterval(DEFAULT_INTERVAL_IN_MILLISECONDS).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    }


    @SuppressLint("MissingPermission")
    override fun onResume(owner: LifecycleOwner) {
        if (successListener != null)
            mFusedLocationClient.lastLocation
                .addOnSuccessListener(
                    successListener
                )
        Looper.myLooper()?.let {
            mFusedLocationClient.requestLocationUpdates(
                mLocationRequest,
                mCallback,
                it
            )
        }

    }


    override fun onStop(owner: LifecycleOwner) {
        mFusedLocationClient.removeLocationUpdates(mCallback)
    }


    var successListener: OnSuccessListener<Location> =
        OnSuccessListener { location ->
            locationLiveData.postValue(location)
        }

    var mCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            if (locationResult.locations.size > 0) {
                locationLiveData.postValue(locationResult.locations[0])

            }
            super.onLocationResult(locationResult)
        }
    }
}