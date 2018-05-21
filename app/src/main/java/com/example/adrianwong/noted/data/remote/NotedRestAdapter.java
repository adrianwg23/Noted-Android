package com.example.adrianwong.noted.data.remote;


import com.example.adrianwong.noted.datamodel.NoteItem;
import com.example.adrianwong.noted.datamodel.ResponseDataModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class NotedRestAdapter {

    private final NotedService notedService;

    public NotedRestAdapter(Retrofit retrofit) {
        this.notedService = retrofit.create(NotedService.class);
    }

    public interface NotedService {

        @POST(UrlManager.LOGIN)
        Observable<Response<ResponseDataModel>> loginUser(@Body ResponseDataModel user);

        @POST(UrlManager.REGISTER)
        Observable<Response<ResponseDataModel>> registerUser(@Body ResponseDataModel user);

        @POST(UrlManager.LOGOUT_ACCESS)
        Observable<ResponseDataModel> logoutAccess();

        @POST(UrlManager.LOGOUT_REFRESH)
        Observable<ResponseDataModel> logoutRefresh();

        @POST(UrlManager.REFRESH)
        Observable<ResponseDataModel> refresh();

        @POST(UrlManager.NEW_NOTE)
        Observable<ResponseDataModel> newNote(NoteItem note);

        @GET(UrlManager.GET_NOTES)
        Observable<Response<List<NoteItem>>> getNotes(
                @Header("Authorization") String accessToken,
                @Path("username") String username);
    }

    public Observable<Response<ResponseDataModel>> loginUser(ResponseDataModel user) {
        return notedService.loginUser(user);
    }

    public Observable<Response<ResponseDataModel>> registerUser(ResponseDataModel user) {
        return notedService.registerUser(user);
    }
}
