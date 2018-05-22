package com.example.adrianwong.noted.data.remote;


import com.example.adrianwong.noted.datamodel.NoteItem;
import com.example.adrianwong.noted.datamodel.ResponseDataModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
        Observable<ResponseDataModel> logoutAccess(@Header("Authorization") String accessToken);

        @POST(UrlManager.LOGOUT_REFRESH)
        Observable<ResponseDataModel> logoutRefresh(@Header("Authorization") String refreshToken);

        @POST(UrlManager.REFRESH)
        Observable<ResponseDataModel> refresh();

        @POST(UrlManager.NEW_NOTE)
        Observable<ResponseDataModel> newNote(@Header("Authorization") String accessToken,
                                              @Body NoteItem note);

        @DELETE(UrlManager.DELETE_NOTE)
        Observable<ResponseDataModel> deleteNote(@Header("Authorization") String accessToken,
                                                 @Path("id") int noteId);

        @POST(UrlManager.SYNC)
        Observable<ResponseDataModel> syncNotes(@Header("Authorization") String accessToken,
                                                @Path("username") String username);

        @PUT(UrlManager.UPDATE_NOTE)
        Observable<ResponseDataModel> updateNote(@Header("Authorization") String accessToken,
                                                 @Body NoteItem note,
                                                 @Path("id") int noteId);

        @GET(UrlManager.GET_NOTES)
        Observable<ResponseDataModel> fetchNotes(@Header("Authorization") String accessToken,
                                                 @Path("username") String username);
    }

    public Observable<Response<ResponseDataModel>> loginUser(ResponseDataModel user) {
        return notedService.loginUser(user);
    }

    public Observable<Response<ResponseDataModel>> registerUser(ResponseDataModel user) {
        return notedService.registerUser(user);
    }

    public Observable<ResponseDataModel> fetchNotes(String accessToken, String username) {
        return notedService.fetchNotes(accessToken, username);
    }

    public Observable<ResponseDataModel> deleteNotes(String accessToken, int id) {
        return notedService.deleteNote(accessToken, id);
    }

    public Observable<ResponseDataModel> newNote(String accessToken, NoteItem note) {
        return notedService.newNote(accessToken, note);
    }

    public Observable<ResponseDataModel> updateNotes(String accessToken, NoteItem note, int id) {
        return notedService.updateNote(accessToken, note, id);
    }

    public Observable<ResponseDataModel> logoutAccess(String accessToken) {
        return notedService.logoutAccess(accessToken);
    }

    public Observable<ResponseDataModel> logoutRefresh(String refreshToken) {
        return notedService.logoutRefresh(refreshToken);
    }
}
