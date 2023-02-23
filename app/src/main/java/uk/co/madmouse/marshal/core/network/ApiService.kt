package uk.co.madmouse.marshal.core.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import uk.co.madmouse.marshal.core.models.database.Event

interface ApiService {
    @GET("events/by/date/{epochDate}")
    suspend fun getEventsByDate(@Path("epochDate") epochDate: Long): Response<List<Event>>
}