package com.robin.entry


import android.Manifest.permission.SEND_SMS
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.robin.entry.hostDatabase.HostViewModel
import com.robin.entry.mailSender.Mailer
import com.robin.entry.visitorDatabase.Visitor
import com.robin.entry.visitorDatabase.VisitorViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_check_in.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class CheckInFragment : Fragment() {

    val visitorViewModel: VisitorViewModel by lazy {
        ViewModelProviders.of(this).get(VisitorViewModel::class.java)
    }

    val hostViewModel: HostViewModel by lazy {
        ViewModelProviders.of(this).get(HostViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInButton.setOnClickListener {
            val currentTime: Date = Calendar.getInstance().time
            val visitor = Visitor(
                visitorName.text.toString(),
                visitorEmail.text.toString(),
                visitorPhone.text.toString(),
                SimpleDateFormat("HH:mm", Locale.ENGLISH).format(currentTime.time),
                hostname.text.toString(),
                false
            )
            visitorViewModel.insert(visitor)
            checkAndroidVersion(hostPhone.text.toString(), visitorDetails(visitor))
            Mailer.sendMail(hostemail.text.toString(), "Visitor Details", visitorDetails(visitor))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
            clearEditText()
        }
    }

    fun sendSMS(phoneNo: String?, msg: String?) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNo, null, msg, null, null)
            Toast.makeText(
                context?.applicationContext,
                "Message Sent",
                Toast.LENGTH_LONG
            ).show()
        } catch (ex: Exception) {
            Toast.makeText(
                context?.applicationContext,
                ex.message.toString(),
                Toast.LENGTH_SHORT
            ).show()
            ex.printStackTrace()
        }
    }

    private fun visitorDetails(visitor: Visitor): String {
        return "Visitor Name: ${visitor.name} \n Visitor Email ID: ${visitor.email} \n Visitor Phone Number: ${visitor.phoneNumber}"
    }

    private fun clearEditText() {
        hostname.text?.clear()
        hostemail.text?.clear()
        hostPhone.text?.clear()
        visitorEmail.text?.clear()
        visitorPhone.text?.clear()
        visitorName.text?.clear()
    }

    private fun checkAndroidVersion(mobile: String, msg: String) {
        if (Build.VERSION.SDK_INT >= 23) {
            val checkCallPhonePermission = context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    SEND_SMS
                )
            }
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(SEND_SMS),
                    6
                )
                checkAndroidVersion(mobile, msg)
            } else {
                sendSMS(mobile, msg)
            }
        } else {
            sendSMS(mobile, msg)
        }
    }


}
