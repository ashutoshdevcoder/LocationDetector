# LocationDetector
A simple lifecycle aware library to detect current latitude and longitude
 if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                locationPermission,
                101)
        }
        lifecycle.addObserver(locationLifecycleObserver)
        locationLifecycleObserver.locationLiveData.observe(this){
            if(it!=null)
            {
                latitude = it.latitude

                longitude = it.longitude
                Log.d("latitude",latitude.toString())
                Log.d("longitude",longitude.toString())
                findViewById<TextView>(R.id.tv_text).setText("$latitude $longitude")
            }

        }
