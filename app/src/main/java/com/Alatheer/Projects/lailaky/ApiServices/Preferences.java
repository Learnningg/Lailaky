package com.Alatheer.Projects.lailaky.ApiServices;

import android.content.Context;
import android.content.SharedPreferences;

import com.Alatheer.Projects.lailaky.Models.UserModel;

/**
 * Created by Emad on 2018-04-04.
 */

public class Preferences {

    private Context context;

    public Preferences(Context context) {
        this.context = context;
    }

    public void CreatePref(UserModel userModel)
    {
        SharedPreferences sPref = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();

        editor.putString("id",userModel.getUser_id());
        editor.putString("user_name",userModel.getUser_name());
        editor.putString("email",userModel.getUser_email());
        editor.putString("phone",userModel.getUser_phone());
        editor.putString("pass",userModel.getUser_pass());

        editor.putString("session","login");

        editor.apply();
    }

    public void clear()
    {
        SharedPreferences sPref = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();

        editor.putString("id","");
        editor.putString("user_name","");
        editor.putString("email","");
        editor.putString("phone","");
        editor.putString("pass","");
        editor.putString("session","logout");

        editor.apply();


    }
    public void UpdatePref(UserModel userModel)
    {
        SharedPreferences sPref = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();

        editor.putString("id",userModel.getUser_id());
        editor.putString("user_name",userModel.getUser_name());
        editor.putString("email",userModel.getUser_email());
        editor.putString("phone",userModel.getUser_phone());
        editor.putString("pass",userModel.getUser_pass());

        editor.putString("session","login");

        editor.apply();
    }



    public void setlanguage(Context context,boolean selected){

        SharedPreferences preferences=context.getSharedPreferences("language",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putBoolean("isSelected",selected);
        editor.apply();

    }

    public boolean getlanguage(Context context){
        SharedPreferences preferences=context.getSharedPreferences("language",Context.MODE_PRIVATE);
        boolean selected=preferences.getBoolean("isSelected",false);
        return selected;


    }

}
