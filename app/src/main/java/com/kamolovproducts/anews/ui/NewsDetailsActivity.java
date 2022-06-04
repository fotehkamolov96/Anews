package com.kamolovproducts.anews.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.kamolovproducts.anews.R;

public class NewsDetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView img1, img2, imgUrl;
    private TextView txttitle, txt1, txt2, txt4, txt3;
    private ProgressBar prg1, prg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        setContentView(R.layout.view_details);
        toolbar = findViewById(R.id.toolbar_det);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.setPadding(0, 0, -10, 0);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        imgUrl = findViewById(R.id.open_web);
        prg1 = findViewById(R.id.dets_progress);
        prg2 = findViewById(R.id.progress_i);
        img1 = findViewById(R.id.image);
        img2 = findViewById(R.id.image2);
        txttitle = findViewById(R.id.det_title);
        txt1 = findViewById(R.id.t1);
        txt2 = findViewById(R.id.t2);
        txt3 = findViewById(R.id.t3);
        txt4 = findViewById(R.id.dataT);
        data_content();
    }

    private void data_content() {
        String t1 = getIntent().getExtras().getString("title");
        String t0 = getIntent().getExtras().getString("author");
        String t2 = getIntent().getExtras().getString("description");
        String t3 = getIntent().getExtras().getString("content");
        String t4 = getIntent().getExtras().getString("publishedAt");
        String image_url = getIntent().getExtras().getString("img_news");

        txttitle.setText(t0);
        txt1.setText(t1);
        txt2.setText(t2);
        txt3.setText(t3);
        txt4.setText(t4);

        imgUrl.setOnClickListener(v -> {
            String url = getIntent().getExtras().getString("url");
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        Glide.with(this)
                .load(image_url)
                .placeholder(R.drawable.ic_cover_img)
                .error(R.drawable.ic_cover_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                        prg1.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        prg1.setVisibility(View.GONE);
                        return false;
                    }

                })
                .into(img1);

        Glide.with(this)
                .load(image_url)
                .placeholder(R.drawable.ic_cover_img)
                .error(R.drawable.ic_cover_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                        prg2.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        prg2.setVisibility(View.GONE);
                        return false;
                    }

                })
                .into(img2);
    }

}