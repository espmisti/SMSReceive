package com.espmisti.smsreceive

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.lang.Exception

class ReceiveService : BroadcastReceiver() {

    private val TAG = "receive_log"

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "android.provider.Telephony.SMS_RECEIVED"){
            val model = ReceiveModel()
            val msgs: Array<SmsMessage?>
            val pdus: Array<Object> = intent.extras?.get("pdus") as Array<Object>
            msgs = arrayOfNulls(pdus.size)
            for (i in msgs.indices) {
                msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                model.from = msgs[i]?.originatingAddress
                model.message = msgs[i]?.messageBody
                Log.i(TAG, "[Message]: $model")
            }
        }
    }
}