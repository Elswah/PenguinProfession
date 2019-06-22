package com.mobileaders.penguinprofession.Interface;

import com.mobileaders.penguinprofession.Module.ProfileModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by lab on 15/01/2017.
 */

public interface ConectionProfile {
    @FormUrlEncoded
    @POST("/activities/getUserworks")
    Call<List<ProfileModel>> getUserWorks(@Field("penguinUserId")String userId,
                                          @Field("penguinUserPass")String serPass);

    @FormUrlEncoded
    @POST("/activities/getUserSkills")
    Call<List<ProfileModel>> getUserSkills(@Field("penguinUserId")String userId,
                                    @Field("penguinUserPass")String serPass);


    @FormUrlEncoded
    @POST("/activities/getUserEducation")
    Call<List<ProfileModel>> getUserEducation(@Field("penguinUserId")String userId,
                                           @Field("penguinUserPass")String serPass);





}
