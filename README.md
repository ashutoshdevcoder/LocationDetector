# LocationDetector
A simple lifecycle aware library to detect current latitude and longitude
 class MainActivity : AppCompatActivity() {
    val locationLifecycleObserver: LocationLifecycleObserver by lazy {
        LocationLifecycleObserver(this,10000)
    }
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private val locationPermission = arrayOf(
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.ACCESS_COARSE_LOCATION"
    )
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
    }
}
