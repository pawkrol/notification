package org.pawkrol.notificationapp

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by pawkrol on 2017-04-23.
 */
interface NotificationService {

    @POST("blink")
    fun blink(): Observable<String>

    @POST("blink/mode/{mode}")
    fun mode(@Path("mode") mode: Int): Observable<String>

    @GET("notif/check")
    fun check(): Observable<String>

    @POST("notif/remove")
    fun remove(): Observable<String>
}