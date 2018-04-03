package com.example.pierre.chisterapp;

        import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;

public class GalleryActivity extends AppCompatActivity {

    private File[] mlistFiles;
    //private String[] namelist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mlistFiles = new File("/storage/emulated/0/Pictures").listFiles();
        /*String dirPath = "/storage/emulated/0/Pictures";
        File dir = new File(dirPath);
        namelist = dir.list();*/

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mlistFiles.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(1085, 1085));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize = 2;

            imageView.setImageBitmap(BitmapFactory.decodeFile(mlistFiles[position].getPath(), options));

            return imageView;
        }

    }

}