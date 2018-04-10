package com.Alatheer.Projects.laylaky.Activites;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.Alatheer.Projects.laylaky.Adapter.AdapterAlbumaty;
import com.Alatheer.Projects.laylaky.ApiServices.Api;
import com.Alatheer.Projects.laylaky.ApiServices.Services;
import com.Alatheer.Projects.laylaky.Models.OfferModel;
import com.Alatheer.Projects.laylaky.Models.OfferModel;
import com.Alatheer.Projects.laylaky.Models.UserModel;
import com.Alatheer.Projects.laylaky.R;
import com.Alatheer.Projects.laylaky.SingleTone.Users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Albumaty extends AppCompatActivity implements Users.onCompleteListener {

    RecyclerView recyclerView;
    AdapterAlbumaty AdapterAlbumaty;
    OfferModel OfferModel;
    List<OfferModel> OfferModelList;
    private final int IMG_REQ=200;
    List<Uri> uriList;
    Users users;
    UserModel userModel;
    ImageButton share,back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_album);
        recyclerView=findViewById(R.id.recycalbum);
        share=findViewById(R.id.share);
        back=findViewById(R.id.back);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text ="استوديو ليلاكي";
                String link ="https://play.google.com/store/apps/details?id=com.Alatheer.Projects.laylaky";

                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,text+"\n"+link);
                intent.setType("text/plain");
                startActivity(intent);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });


        users = Users.getInstance();
        users.getData(this);

//        Log.e("id", userModel.getUser_id());


        uriList = new ArrayList<>();
        OfferModelList=new ArrayList<>();


        recyclerView.setLayoutManager(new GridLayoutManager(Albumaty.this,2));
        recyclerView.setHasFixedSize(true);

        AdapterAlbumaty = new AdapterAlbumaty(OfferModelList,Albumaty.this);
        recyclerView.setAdapter(AdapterAlbumaty);
        myoffer();
    }

    private void myoffer(){

        Services services= Api.getClient().create(Services.class);
        Call<List<OfferModel>> call=services.MyOffer(userModel.getUser_id());
        call.enqueue(new Callback<List<OfferModel>>() {
            @Override
            public void onResponse(Call<List<OfferModel>> call, Response<List<OfferModel>> response) {
                OfferModelList.addAll(response.body());
                AdapterAlbumaty.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<OfferModel>> call, Throwable t) {

            }
        });
    }

    public  void pos(int pos){


        OfferModel OfferModel = OfferModelList.get(pos);
        Intent i = new Intent(Albumaty.this, DetailsMyOffer.class);


        i.putExtra("title",OfferModel.getTitle());
        i.putExtra("detail",OfferModel.getDetails());
        i.putExtra("price",OfferModel.getPrice());
        i.putExtra("img", OfferModel.getImg());
        i.putExtra("id_offer", OfferModel.getOffer_id());
        i.putExtra("id_album", OfferModel.getAlbum_id());

        Toast.makeText(this, ""+OfferModel.getAlbum_id(), Toast.LENGTH_SHORT).show();
        startActivity(i);


    }
//    public void setPos(int pos)
//    {
//        //OfferModel OfferModel = OfferModelList.get(pos);
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
//        startActivityForResult(intent,IMG_REQ);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==IMG_REQ && resultCode == RESULT_OK && data!=null)
//        {
//            ClipData clipData = data.getClipData();
//         //   Log.e("eddddd",""+clipData.getItemCount()+"   "+clipData.getItemAt(0));
//            for (int index =0;index<clipData.getItemCount();index++)
//            {
//                ClipData.Item item = clipData.getItemAt(index);
//                Uri uri = item.getUri();
//                uriList.add(uri);
//            }
//
//            Intent intent = new Intent(Albumaty.this,DetailsAlbumaty.class);
//
//            intent.putExtra("details", (Serializable) uriList);
//
//            startActivity(intent);
//
//            Toast.makeText(this, ""+uriList.get(0), Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public void OnDataSuccess(UserModel userModel) {
//        Log.e("id", userModel.getUser_id());
        this.userModel=userModel;

    }
}
