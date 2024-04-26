package com.shoykatsaha.myapplication
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.GsonBuilder
import com.shoykatsaha.myapplication.Constants.Companion.TOPIC
import com.shoykatsaha.myapplication.databinding.FragmentHomeAdminBinding
import com.shoykatsaha.myapplication.models.NotificationData
import com.shoykatsaha.myapplication.models.PushNotification
import com.shoykatsaha.myapplication.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeAdminFragment : Fragment() {
    val TAG = "HomeAdminFragment"
    private lateinit var binding: FragmentHomeAdminBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            val recipientToken = binding.etToken.text.toString()

            if (message.isNotEmpty()) {
                // Create the PushNotification object
                val pushNotification = PushNotification(NotificationData(message), TOPIC)

                // Send the notification
                sendNotification(pushNotification)
            }
        }



        return binding.root
    }
    private fun sendNotification(notification: PushNotification) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Use Gson to convert the PushNotification object to JSON
                val gson = GsonBuilder().create()
                val json = gson.toJson(notification)

                // Log the JSON string
                Log.d(TAG, "Notification JSON: $json")

                // Send the notification using Retrofit
                val response = RetrofitInstance.api.postNotification(notification)

                if (response.isSuccessful) {
                    Log.d(TAG, "Notification sent successfully: ${response.body()}")
                } else {
                    Log.e(TAG, "Failed to send notification: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to send notification: ${e.message}")
            }
        }
    }
}
