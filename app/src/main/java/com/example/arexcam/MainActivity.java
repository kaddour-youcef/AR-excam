package com.example.arexcam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Byte4;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ArFragment arFragment;
    private ModelRenderable modelRenderable;

    //declaration des objet3d
    int resID= (int) R.raw.grasshoper;
    int reID2= (int) R.raw.model;
    int reID3= (int) R.raw.puis;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // affichage
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);


        FloatingActionButton B1 =  findViewById(R.id.floatingActionButton);
        FloatingActionButton B2 =  findViewById(R.id.floatingActionButton2);
        FloatingActionButton B3 =  findViewById(R.id.floatingActionButton4);
        FloatingActionButton B4 =  findViewById(R.id.floatingActionButton5);
        B1.setOnClickListener(this);
        B2.setOnClickListener(this);
        B3.setOnClickListener(this);
        B4.setOnClickListener(this);


    }

    private void setUpModel(int res){
       int ress = res;
        ModelRenderable.builder()
                .setSource(this, res)
                .build()
                .thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(MainActivity.this, " le model ne peut pas etre charger", Toast.LENGTH_SHORT).show();
                    return null;
                });

    }

    private void setUpPlans(){
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNod = new AnchorNode(anchor);
                anchorNod.setParent(arFragment.getArSceneView().getScene());
                createModel(anchorNod);
            }
        });

    }
    private void createModel(AnchorNode anchorNode){
        TransformableNode node = new TransformableNode((arFragment.getTransformationSystem()));
        node.setParent(anchorNode);
        node.setRenderable(modelRenderable);
        node.select();



    }

    private void Gen_I (int R){

        setUpModel(R);
        setUpPlans();

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.floatingActionButton : // va vers activité map
                Intent intent = new Intent(this, Map2.class);
                this.startActivity(intent);
                break;
            case R.id.floatingActionButton2 :
                Gen_I(reID3);
                break;
            case R.id.floatingActionButton4 :
                Gen_I(resID);
                break;
            case R.id.floatingActionButton5 :
                Gen_I(reID2);
                break;
        }

    }
}