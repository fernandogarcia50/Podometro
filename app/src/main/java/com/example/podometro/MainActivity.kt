package com.example.podometro

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.podometro.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACTIVITY_RECOGNITION)==PackageManager.PERMISSION_DENIED){
            requestPermissions(arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),1)
        }

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        Log.d("sensorExample", sensor.toString())



        val sensorGiroscopio: Sensor?=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        Log.d("sensorExample", sensorGiroscopio.toString())






        var pasos: Float=0.0F
        val sensorEventListener: SensorEventListener=object : SensorEventListener{

            override fun onSensorChanged(sensorEvent: SensorEvent?) {

                if (sensorEvent != null) {
                    for (value in sensorEvent.values){

                        pasos+=value
                        binding.pasosDados.setText("${pasos}")
                        Log.d("Pasos", "${pasos}")

                    }
                }


            }
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                //TODO
            }


        }


        val sensorEventListenerSecond: SensorEventListener = object : SensorEventListener{
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                Log.d("giros", "${sensorEvent.values[0]}")
               if(sensorEvent.values[2] > 0.5F)
               {
                   Log.d("saludo","es mayor")
                   binding.Fondo.setBackgroundColor(Color.BLUE)
               }else if (sensorEvent.values[2] < -0.5F )
               {
                   binding.Fondo.setBackgroundColor(Color.GRAY)
                   Log.d("saludo","es menor")
               }
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                //TODO("Not yet implemented")
            }
        }

        sensorManager.registerListener(sensorEventListenerSecond, sensorGiroscopio, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)




    }



}