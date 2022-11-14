package com.example.clase15_terremotoapi

import android.graphics.BitmapFactory
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapView = MapView(this)
        setContentView(mapView)
        val bundle = intent.extras
        val terremoto: Terremoto? = bundle?.getParcelable("terremotoMap")
        val longitud = terremoto?.longitude
        val latitude = terremoto?.latitude

       
        mapView.getMapboxMap().also {
            it.setCamera(
                CameraOptions.Builder()
                    .center(Point.fromLngLat(longitud!!, latitude!!))
                    .zoom(4.0)
                    .build()
            )
        }.loadStyle(
            styleExtension = style(Style.OUTDOORS) {

                +image(BLUE_ICON_ID) {
                    bitmap(BitmapFactory.decodeResource(resources, R.drawable.red_marker))
                }
                +geoJsonSource(SOURCE_ID) {
                    geometry(Point.fromLngLat(longitud!!, latitude!!))
                }
                +symbolLayer(LAYER_ID, SOURCE_ID) {
                    iconImage(BLUE_ICON_ID)
                    iconAnchor(IconAnchor.BOTTOM)
                }
            }
        )
    }

    companion object {
        private const val BLUE_ICON_ID = "blue"
        private const val SOURCE_ID = "source_id"
        private const val LAYER_ID = "layer_id"
    }
}