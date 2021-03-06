package com.Alatheer.Projects.lailaky.Activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.Alatheer.Projects.lailaky.Models.UserModel;
import com.Alatheer.Projects.lailaky.R;
import com.Alatheer.Projects.lailaky.SingleTone.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;


public class DetailOffer extends AppCompatActivity implements Users.onCompleteListener {
    TextView title, desc, price,size;
    ImageView img;
    Button Upload;
    Users users;
    String titlee, desce, pricee, imgg, idoffer,size_offer;
    int IMG_REQ = 200;

    List<Uri> uriList;
    List<Uri> selectedImage;
    List<String> enCodedImageList;

    UserModel userModel;
    String user_type;
    private ProgressDialog dialog;
    private List<Bitmap>galleryModelList;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);

        super.attachBaseContext(LanguageHelper.onAttach(newBase, Paper.book().read("language", Locale.getDefault().getLanguage())));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_offer);
        initView();
        getDataFromIntent();

        galleryModelList = new ArrayList<>();
        enCodedImageList = new ArrayList<>();
        uriList = new ArrayList<>();
        selectedImage = new ArrayList<>();

        users = Users.getInstance();
        users.getData(this);




        title.setText(titlee);
        desc.setText(desce);
        price.setText(pricee+" "+getString(R.string.sar));
        size.setText(size_offer+" "+getString(R.string.page));


        Picasso.with(this).load(imgg).into(img);
        CreateProgress();


    }


    private void CreateProgress()
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.upload_img));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

    }

    private void initView() {

        title = findViewById(R.id.detail_title);
        desc = findViewById(R.id.detail_desc);
        price = findViewById(R.id.detail_pric);
        size=findViewById(R.id.account_img);
        Upload=findViewById(R.id.Upload);

        img = findViewById(R.id.detail_img);
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image*//*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(intent, IMG_REQ);*/
                navigate();


            }
        });


    }
    /*@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQ && resultCode == RESULT_OK && data != null) {
            ClipData clipData = data.getClipData();
            uriList.clear();


            if (clipData != null) {


                for (int index = 0; index < clipData.getItemCount(); index++) {
                    ClipData.Item item = clipData.getItemAt(index);
                    Uri uri = item.getUri();
                    uriList.add(uri);
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        galleryModelList.add(bitmap);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                   *//* try {
                        //Bitmap bitmap =BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }*//*

                }
                navigate(galleryModelList);


                *//*if (uriList.size() > 5) {
                    for (int i = 0; i <= 4; i++) {
                        selectedImage.add(uriList.get(i));
                    }
                    Toast.makeText(this, "size1" + selectedImage.size(), Toast.LENGTH_SHORT).show();

                    UpdateUI(selectedImage);
                } else {

                    selectedImage = uriList;
                    UpdateUI(selectedImage);
                    Toast.makeText(this, "size2" + selectedImage.size(), Toast.LENGTH_SHORT).show();

                }*//*
            } else {

                Uri uri = data.getData();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    galleryModelList.add(bitmap);
                    navigate(galleryModelList);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                *//*try {
                    Bitmap bitmap =BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    galleryModelList.add(new GalleryModel(bitmap));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*//*

            }


        }
    }*/

    private void navigate()
    {
       /* ArrayList<String> encoded= new ArrayList<>();

        for (Bitmap bitmap :galleryModels)
        {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,outputStream);
            byte [] bytes = outputStream.toByteArray();
            encoded.add(Base64.encodeToString(bytes,Base64.DEFAULT));
        }*/

       /* Intent intent = new Intent(DetailOffer.this,DisplayGalleryActivity.class);
        //intent.putExtra("data",encoded);
        intent.putExtra("album_size",size_offer);
        intent.putExtra("id_offer",idoffer);
        intent.putExtra("user_id",userModel.getUser_id());
        startActivity(intent);
        finish();*/
        Intent intent = new Intent(DetailOffer.this,TypeePaperActivity.class);
        //intent.putExtra("data",encoded);
        intent.putExtra("album_size",size_offer);
        intent.putExtra("id_offer",idoffer);
        intent.putExtra("user_id",userModel.getUser_id());
        startActivity(intent);
        finish();
    }

    @Override
    public void OnDataSuccess(UserModel userModel) {
        this.userModel=userModel;
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("user_type"))
            {
                user_type = intent.getStringExtra("user_type");
                if (user_type.equals("visitor"))
                {
                    Upload.setVisibility(View.INVISIBLE);
                }

            }
            titlee = intent.getStringExtra("title");
            desce = intent.getStringExtra("detail");
            pricee = intent.getStringExtra("price");

            imgg = intent.getStringExtra("img");
             idoffer = intent.getStringExtra("id_offer");
            size_offer=intent.getStringExtra("size_offer");
        }
    }

}
