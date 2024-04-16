package com.example.myapplication.ui

import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.FragmentWeatherBinding
import com.example.myapplication.model.CurrentWeatherResponse
import com.example.myapplication.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.util.Locale

class WeatherFragment : Fragment(), LocationListener {
    lateinit var searchView: SearchView

    private var _binding: FragmentWeatherBinding? = null

    private val binding get() = _binding!!

    private val weatherViewModel: WeatherViewModel by activityViewModels()

    private lateinit var geocoder: Geocoder
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        geocoder = Geocoder(requireContext(), Locale.getDefault())

        if (arguments != null) {
            setupObservers()
            //getLastLocation()
            weatherViewModel.getWeather(39.03, 84.46)
        }
        searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {

                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return binding.root
    }

    fun setupObservers() {
        lifecycleScope.launch {
            weatherViewModel.weather.collect { event ->
                when (event) {
                    WeatherViewModel.WeatherEvent.Failure -> {
                        setVisible(false)
                        binding.progressBarCurrent.isVisible = false
                        binding.errorMessageCurrent.isVisible = true
                    }
                    WeatherViewModel.WeatherEvent.Loading -> {
                        setVisible(false)
                        binding.progressBarCurrent.isVisible = true
                        binding.errorMessageCurrent.isVisible = false
                    }
                    is WeatherViewModel.WeatherEvent.Success -> {
                        setData(event.weather)
                        setVisible(true)
                        binding.progressBarCurrent.isVisible = false
                        binding.errorMessageCurrent.isVisible = false
                    }
                }
            }
        }
    }

    fun setVisible(visible: Boolean) {
        binding.textWeather.isVisible = visible
        binding.searchView.isVisible = visible
    }

    fun setData(weather: CurrentWeatherResponse) {
        binding.textWeather.text = weather.main.temp.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                10101)
        }

        val lastLocation = fusedLocationProviderClient.lastLocation

        lastLocation.addOnSuccessListener {
            weatherViewModel.getWeather(it.latitude, it.longitude)
        }
        lastLocation.addOnFailureListener {
            Toast.makeText(requireContext(), "Getting Location Failed", Toast.LENGTH_LONG).show()
        }
    }

    override fun onLocationChanged(location: Location) {
        getLastLocation()
    }

}