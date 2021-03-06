package com.Alatheer.Projects.lailaky.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.Alatheer.Projects.lailaky.Activites.DesiredCoverActivity;
import com.Alatheer.Projects.lailaky.R;
import com.Alatheer.Projects.lailaky.SingleTone.FinalAlbumImage;
import com.Alatheer.Projects.lailaky.share.Common;

import net.karthikraj.shapesimage.ShapesImage;

/**
 * Created by elashry on 10/07/2018.
 */

public class Fragment_Cover_Shape1 extends Fragment implements View.OnTouchListener{
    private static final String TAG1="user_id";
    private static final String TAG2="offer_id";
    private static final String TAG3="album_size";
    private static final String TAG4="paper_id";

    private String user_id="",offer_id="",paper_id="";
    private int album_size=0;
    private ShapesImage shape1;
    private ImageView shape1_icon;
    private Bitmap bitmap1;
    private final int IMG_REQ1=1;
    private FrameLayout f1;
    //private DisplayImagesActivity activity;
    private DesiredCoverActivity activity;

    private int img1_selected = 0;

    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    // we can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;
    private float[] lastEvent = null;
    private int count=0;
    private RelativeLayout root;
    private FinalAlbumImage instance;
    private EditText textframe;
    int finalHeight, finalWidth;
    private Button btn_add;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shape1_cover,container,false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        instance = FinalAlbumImage.getInstance();
        Bundle bundle = getArguments();

        if (bundle!=null)
        {
            user_id = bundle.getString(TAG1);
            offer_id = bundle.getString(TAG2);
            paper_id = bundle.getString(TAG4);

            album_size = bundle.getInt(TAG3);
        }
        //activity = (DisplayImagesActivity) getActivity();
        activity = (DesiredCoverActivity) getActivity();

        shape1 = view.findViewById(R.id.shape1);


        shape1_icon = view.findViewById(R.id.shape1_icon);

        f1 = view.findViewById(R.id.f1);
        root = view.findViewById(R.id.root);
        textframe=view.findViewById(R.id.textframe);
        btn_add =view.findViewById(R.id.btn_add);



        shape1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap1!=null)
                {
                    f1.setBackgroundResource(R.drawable.img_selected);
                    img1_selected = 1;


                }  else
                {
                    SelectImage(IMG_REQ1);
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getBitmap();
                instance.setCoverImage(bitmap);
                activity.Finish();

            }
        });



    }
    public Bitmap getBitmap()
    {
        btn_add.setVisibility(View.GONE);
        f1.setBackgroundResource(R.drawable.transparent_bg);

        if (textframe.getText().toString().trim().length() == 0){

            textframe.setVisibility(View.GONE);
        }
        root.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(root.getDrawingCache());
        root.setDrawingCacheEnabled(false);
        return bitmap;
    }
    public void clearUi()
    {
        bitmap1=null;

        shape1.setImageBitmap(null);



    }
    private void SelectImage(int img_req)
    {
        Intent intent;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
        {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }else
        {
            intent = new Intent(Intent.ACTION_GET_CONTENT);

        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,"Select Image"),img_req);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQ1 && resultCode == Activity.RESULT_OK && data!=null)

        {

            Uri uri = data.getData();
            getImageUri(uri);
        }

    }

    public void getImageUri(Uri uri)
    {
        Bitmap bitmap = BitmapFactory.decodeFile(Common.getImagePath(getActivity(),uri));

        finalWidth=bitmap.getWidth();
        finalHeight=bitmap.getHeight();

        if (finalHeight<100||finalWidth<100){

            Toast.makeText(activity, R.string.night, Toast.LENGTH_LONG).show();
        }
        else {
        if (bitmap1==null)
        {


            bitmap1 = bitmap;
            shape1.setImageBitmap(bitmap1);
            shape1_icon.setVisibility(View.GONE);
            f1.setBackgroundResource(R.drawable.img_selected);

            img1_selected = 1;
            shape1.setOnTouchListener(this);



        }

        else if (bitmap1!=null)
        {

            if (img1_selected==1)
            {
                shape1.setImageBitmap(bitmap);

            }



        }
            btn_add.setVisibility(View.VISIBLE);

        }
    }
    public static Fragment_Cover_Shape1 getInstance(String user_id, String offer_id, String paper_id, int album_size)
    {
        Fragment_Cover_Shape1 fragment = new Fragment_Cover_Shape1();
        Bundle bundle = new Bundle();
        bundle.putString(TAG1,user_id);
        bundle.putString(TAG2,offer_id);
        bundle.putString(TAG4,paper_id);
        bundle.putInt(TAG3,album_size);
        fragment.setArguments(bundle);
        return fragment;
    }
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;

        int id = v.getId();

        switch (id)
        {
            case R.id.shape1:
                f1.setBackgroundResource(R.drawable.img_selected);

                img1_selected = 1;

                break;


        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                lastEvent = null;

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    matrix.postTranslate(dx, dy);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = (newDist / oldDist);
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                    if (lastEvent != null && event.getPointerCount() == 2) {
                        newRot = rotation(event);
                        float r = newRot - d;
                        float[] values = new float[9];
                        matrix.getValues(values);
                        float tx = values[2];
                        float ty = values[5];
                        float sx = values[0];
                        float xc = (view.getWidth() / 2) * sx;
                        float yc = (view.getHeight() / 2) * sx;
                        matrix.postRotate(r, tx + xc, ty + yc);
                    }
                }
                break;
        }

        if (img1_selected==1)
        {
            shape1.setImageMatrix(matrix);

        }

        return true;
    }


    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return ( float) Math.sqrt(x * x + y * y);
    }




    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }


    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
}
