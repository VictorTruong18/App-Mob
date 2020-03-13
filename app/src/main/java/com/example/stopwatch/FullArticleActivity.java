package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FullArticleActivity extends AppCompatActivity {

    private TextView viewTitle;
    private TextView viewContent;
    private ImageView viewImage;

    private int idArticle;
    private String title;
    private String imageURL;
    private String description;
    private String content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_article);
        Bundle b = getIntent().getExtras();
        idArticle = b.getInt("idArticle");
        title = b.getString("titre");
        imageURL = b.getString("imgUrl");
        description =b.getString("description");
        content = b.getString("content");

        viewTitle = findViewById(R.id.titreArticleTxt);
        viewContent = findViewById(R.id.article_txt);
        viewImage = findViewById(R.id.articleImg);


        viewTitle.setText(title);
        viewContent.setText(description + "\n\n" + content );
        loadImageFromUrl(imageURL);


    }

    private void loadImageFromUrl(String url){
        Picasso.with(this).load(url).into(viewImage);
    }


}

