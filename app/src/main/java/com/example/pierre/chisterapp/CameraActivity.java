package com.example.pierre.chisterapp;


import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.hardware.Camera;


import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.hardware.Camera.*;

public class + CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private Camera camera = null;
    private SurfaceView surfaceCamera;
    private Boolean isPreview;
    private FileOutputStream stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Nous mettons l'application en plein écran et sans barre de titre
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        isPreview = false;

        // Nous appliquons notre layout
        setContentView(R.layout.activity_camera);

        // Nous récupérons notre surface pour le preview
        surfaceCamera = (SurfaceView) findViewById(R.id.surfaceViewCamera);

        // Méthode d'initialisation de la caméra
        InitializeCamera();

        // Quand nous cliquons sur notre surface
        surfaceCamera.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Nous prenons une photo
                if (camera != null) {
                    SavePicture();
                    //saveToInternalStorage();
                }

            }
        });
    }

    public void InitializeCamera() {
// Nous attachons nos retours du holder à notre activité
        surfaceCamera.getHolder().addCallback(this);
// Nous spécifiions le type du holder en mode SURFACE_TYPE_PUSH_BUFFERS
        surfaceCamera.getHolder().setType(
                SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    // Quand la surface change
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

        // Si le mode preview est lancé alors nous le stoppons
        if (isPreview) {
            camera.stopPreview();
        }
        // Nous récupérons les paramètres de la caméra
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();

        //Camera.Size previewSize = camera.setParameters(parameters);
        // Nous appliquons nos nouveaux paramètres
        //camera.setParameters(parameters);

        try {
            // Nous attachons notre prévisualisation de la caméra au holder de la
            // surface
            camera.setPreviewDisplay(surfaceCamera.getHolder());
        } catch (IOException e) {
        }

        // Nous lançons la preview
        camera.startPreview();

        isPreview = true;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // Nous prenons le contrôle de la camera
        if (camera == null)
            camera = open();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Nous arrêtons la camera et nous rendons la main
        if (camera != null) {
            camera.stopPreview();
            isPreview = false;
            camera.release();
        }
    }

    // Retour sur l'application
    @Override
    public void onResume() {
        super.onResume();
        camera = open();
    }

    // Mise en pause de l'application
    @Override
    public void onPause() {
        super.onPause();

        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    private void SavePicture() {

        try {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
            String fileName = "photo_" + timeStampFormat.format(new Date()) + ".jpg";

            // Metadata pour la photo
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.Media.DESCRIPTION, "Image prise par FormationCamera");
            values.put(MediaStore.Images.Media.DATE_TAKEN, new Date().getTime());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");

            // test support de stockage
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) &&  !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY))
            {
               Log.d("test ok", Environment.MEDIA_MOUNTED);}
            else {Log.d("Warning test KO: ", Environment.MEDIA_MOUNTED);}

            // Support de stockage
            Uri taken = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            // Ouverture du flux pour la sauvegarde
            stream = (FileOutputStream) getContentResolver().openOutputStream(taken);
            camera.takePicture(null, pictureCallback, pictureCallback);
        } catch (Exception e) {
        }

    }

    // Callback pour la prise de photo
    PictureCallback pictureCallback = new PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            if (data != null) {
                // Enregistrement de votre image
                try { System.out.println("Photo TRY");
                    if (stream != null) {
                        stream.write(data);
                        stream.flush();
                        stream.close();
                        System.out.println("Photo CATCH");

                    }
                    System.out.println("Photo prise");
                } catch (Exception e) {
                    // TODO: handle exception
                }

                // Nous redémarrons la prévisualisation
                camera.startPreview();
            }
        }
    };

}

