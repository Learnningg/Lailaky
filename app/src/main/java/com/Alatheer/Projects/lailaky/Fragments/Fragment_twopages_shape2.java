package com.Alatheer.Projects.lailaky.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.Alatheer.Projects.lailaky.Activites.DisplayImagesActivity;
import com.Alatheer.Projects.lailaky.Activites.UpdateImageActivity;
import com.Alatheer.Projects.lailaky.ApiServices.Tags;
import com.Alatheer.Projects.lailaky.R;
import com.Alatheer.Projects.lailaky.SingleTone.FinalAlbumImage;

import net.karthikraj.shapesimage.ShapesImage;

/**
 * Created by elashry on 10/07/2018.
 */

public class Fragment_twopages_shape2 extends Fragment implements View.OnTouchListener{
    private static final String TAG1="user_id";
    private static final String TAG2="offer_id";
    private static final String TAG3="album_size";
    private static final String TAG4="paper_id";
    private static final String TAG5="activity";
    private UpdateImageActivity updateImageActivity;

    private String user_id="",offer_id="",paper_id="";
    private int album_size=0;
    private ShapesImage shape1;
    private ImageView shape1_icon;
    private Bitmap bitmap1;
    private final int IMG_REQ1=1;
    private FrameLayout f1;
    private DisplayImagesActivity activity;
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
    FinalAlbumImage instance;
    private EditText textframe;
    int finalHeight, finalWidth;
    private String which_activity="";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shape2_twopages,container,false);
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
            which_activity = bundle.getString(TAG5);
        }
        if (which_activity.equals(Tags.DisplayImagesActivity))
        {
            activity = (DisplayImagesActivity) getActivity();

        }else
        {
            updateImageActivity = (UpdateImageActivity) getActivity();
        }
        shape1 = view.findViewById(R.id.shape1);


        shape1_icon = view.findViewById(R.id.shape1_icon);

        f1 = view.findViewById(R.id.f1);
        root = view.findViewById(R.id.root);
        textframe=view.findViewById(R.id.textframe);



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



    }
    public Bitmap getBitmap()
    {
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
        if (which_activity.equals(Tags.DisplayImagesActivity))
        {
            activity.displayImage(img_req);

        }else
        {
            updateImageActivity.displayImage(img_req);

        }

    }

    public void getImageUri(String uri)
    {
        Bitmap bitmap = BitmapFactory.decodeFile(uri);

        finalWidth=bitmap.getWidth();
        finalHeight=bitmap.getHeight();

        if (finalHeight<100||finalWidth<100){

            Toast.makeText(getActivity(), R.string.night, Toast.LENGTH_LONG).show();
        }
        else {
        if (bitmap1==null)
        {

            if (which_activity.equals(Tags.DisplayImagesActivity))
            {
                activity.setButtonsaveVisibility(Tags.visible_btn);

            }else
            {
                updateImageActivity.setButtonsaveVisibility(Tags.visible_btn);

            }

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


        }}
    }
    public static Fragment_twopages_shape2 getInstance(String user_id, String offer_id, String paper_id, int album_size,String which_activity)
    {
        Fragment_twopages_shape2 fragment = new Fragment_twopages_shape2();
        Bundle bundle = new Bundle();
        bundle.putString(TAG1,user_id);
        bundle.putString(TAG2,offer_id);
        bundle.putString(TAG4,paper_id);
        bundle.putInt(TAG3,album_size);
        bundle.putString(TAG5,which_activity);
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
