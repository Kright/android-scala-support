package com.github.kright.manydepsapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.kright.manydepsapp.manymethods._
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.{ConnectionCallbacks, OnConnectionFailedListener}
import com.google.android.gms.location.LocationServices

class MainActivity extends AppCompatActivity {

	var googleApiClient: GoogleApiClient = null

	override protected def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		if (googleApiClient == null) {
			googleApiClient = new GoogleApiClient.Builder(this).
				addConnectionCallbacks(new CustomConnectionsCallbacks()).
				addOnConnectionFailedListener(new CustomOnConnectionFailedListener).
				addApi(LocationServices.API).
				build()
		}
	}

	override def onStart(): Unit = {
		googleApiClient.connect()
		super.onStart()
	}

	override def onStop(): Unit = {
		googleApiClient.disconnect()
		super.onStop()
	}

	override def onResume(): Unit = {
		super.onResume()
		AppActors.logActor ! "resume"
		new ClassWith4kMethods().log("load", "class", "with", "4k methods")
		new ClassWith4kMethods2().log("load", "class", "with", "4k methods")
		new ClassWith4kMethods3().log("load", "class", "with", "4k methods")
		//new ClassWith4kMethods4().log("load", "class", "with", "4k methods")
	}


	class CustomConnectionsCallbacks extends ConnectionCallbacks {
		override def onConnectionSuspended(i: Int): Unit = AppActors.logActor ! "onConnectionSuspended"

		override def onConnected(bundle: Bundle): Unit = {
			AppActors.logActor ! "onConnected"

			val lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
			if (lastLocation != null)
				AppActors.logActor ! s"location(${lastLocation.getLatitude}, ${lastLocation.getLongitude})"
			else
				AppActors.logActor ! s"location is null"
		}
	}

	class CustomOnConnectionFailedListener extends OnConnectionFailedListener {
		override def onConnectionFailed(connectionResult: ConnectionResult): Unit = {
			AppActors.logActor ! connectionResult
		}
	}

}
