package com.example.fifthhw.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VkApi {
    @GET("/method/photos.get?")
    Observable<PostModel> getImages(@Query("owner_id") String ownerId,
                                    @Query("album_id") String albumId,
                                    @Query("access_token") String accessToken,
                                    @Query("v") String version);
}
