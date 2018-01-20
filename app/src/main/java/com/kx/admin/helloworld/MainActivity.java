package com.kx.admin.helloworld;


import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflaterCompat.setFactory2(layoutInflater, new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                if (name.equals("TextView")){
                    return  new Button(context,attrs);
                }
                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {

                return null;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void  changeSkin(View view ){
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Resources superResources = getResources();
            Resources resources = new Resources(assetManager,superResources.getDisplayMetrics(),superResources.getConfiguration());
          //  Method method = Method.class.getMethod("","drawable",);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
