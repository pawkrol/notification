package org.pawkrol.notificationapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.enabled
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import org.pawkrol.notificationapp.layout.MainLayout
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var service: NotificationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainLayout().setContentView(this)

        find<EditText>(R.id.address).setText("192.168.0.12")
    }

    fun onSetAddress(addressField: EditText) {
        val address = addressField.text.toString()
        if (address.isEmpty()) {
            Toast.makeText(this, "Address cannot be empty!", Toast.LENGTH_SHORT).show()
            return
        }

        initService(address)

        find<Button>(R.id.blinkBtn).enabled = true
        find<Button>(R.id.checkBtn).enabled = true
        find<Button>(R.id.removeBtn).enabled = true
        find<Button>(R.id.modeZeroBtn).enabled = true
        find<Button>(R.id.modeOneBtn).enabled = true
    }

    fun onBlink(){
        setSubscribe(service.blink())
    }

    fun onCheck(){
        setSubscribe(service.check())
    }

    fun onRemove(){
        setSubscribe(service.remove())
    }

    fun onMode(mode: Int){
        setSubscribe(service.mode(mode))
    }

    private fun setSubscribe(observable: Observable<String>){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        {text -> Toast.makeText(this, text, Toast.LENGTH_SHORT).show()},
                        {error -> Toast.makeText(this, "ERROR: $error", Toast.LENGTH_SHORT).show()}
                )
    }

    private fun initService(address: String){
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        service = Retrofit.Builder()
                .baseUrl("http://$address:8080/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build()
                .create(NotificationService::class.java)
    }
}
