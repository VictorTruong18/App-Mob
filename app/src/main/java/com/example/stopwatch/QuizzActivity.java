package com.example.stopwatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.ImageView;

public class QuizzActivity extends AppCompatActivity {



    private TextView TextViewResult;
    private RequestQueue mQueue;
    private ImageView image;
    private List<Article> articles = new ArrayList<>();
    private static int indiceQuestion = 0;
    private static int nbBonneReponse = 0;


    private Button btnreponse1 ;
    private Button btnreponse2 ;
    private Button btnreponse3 ;
    private Button btnreponse4 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        Initialisation();
    }


    private void Initialisation(){
        TextViewResult = findViewById(R.id.text_view_result);
        this.image = findViewById(R.id.imgview);


        mQueue = Volley.newRequestQueue(this);

        String url = "https://newsapi.org/v2/top-headlines?country=fr&apiKey=5e5b4f88a60f488aa44b243036dfbad7";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Json request", String.valueOf(response));
                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");

                            for (int i = 0; i < 5; i++) {
                                JSONObject articleJSON = jsonArray.getJSONObject(i);

                                String titre = articleJSON.getString("title");
                                String auteur = articleJSON.getString("author");
                                String description = articleJSON.getString("description");
                                String url = articleJSON.getString("url");
                                String img = articleJSON.getString("urlToImage");
                                String publishedAt = articleJSON.getString("publishedAt");
                                String content = articleJSON.getString("content");
//

                                Article article = new Article(i,titre,auteur,description,url,img,publishedAt,content);
                                articles.add(article);
                            }

                            LoadQuestion(indiceQuestion);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

    private void LoadQuestion(int QuestionId){
        final int random = new Random().nextInt((4 -  1) + 1) + 1;
        String url = articles.get(QuestionId).getUrlToImage();
        loadImageFromUrl(url);

        btnreponse1 = findViewById(R.id.btnreponse1);
        btnreponse2 = findViewById(R.id.btnreponse2);
        btnreponse3 = findViewById(R.id.btnreponse3);
        btnreponse4 = findViewById(R.id.btnreponse4);


        switch (random){
            case 1:
                btnreponse1.setText(articles.get(QuestionId).getTitle());
                break;
            case 2:
                btnreponse2.setText(articles.get(QuestionId).getTitle());
                break;
            case 3:
                btnreponse3.setText(articles.get(QuestionId).getTitle());
                break;
            case 4:
                btnreponse4.setText(articles.get(QuestionId).getTitle());
                break;
        }

        ArrayList<Integer> dejaRemplie = new ArrayList();
        for(int i = 0; i < 5; i++){
            if(i != indiceQuestion ) {
                if( random != 1 && !dejaRemplie.contains(1)){
                    btnreponse1.setText(articles.get(i).getTitle());
                    dejaRemplie.add(1);

                }
                else if( random != 2 && !dejaRemplie.contains(2)){
                    btnreponse2.setText(articles.get(i).getTitle());
                    dejaRemplie.add(2);

                }
                else if(  random != 3 && !dejaRemplie.contains(3)){
                   btnreponse3.setText(articles.get(i).getTitle());
                    dejaRemplie.add(3);

                 }
                else if( random != 4 && !dejaRemplie.contains(4)){
                    btnreponse4.setText(articles.get(i).getTitle());
                    dejaRemplie.add(4);

                }
            }

        }

        loadButtonListener();



    }

    private void loadImageFromUrl(String url){
        Picasso.with(this).load(url).into(image);
    }

    private void loadButtonListener(){

        btnreponse1 = findViewById(R.id.btnreponse1);
        btnreponse2 = findViewById(R.id.btnreponse2);
        btnreponse3 = findViewById(R.id.btnreponse3);
        btnreponse4 = findViewById(R.id.btnreponse4);


        btnreponse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(QuizzActivity.this);

                if(btnreponse1.getText().equals(articles.get(indiceQuestion).getTitle())){
                    builder1.setMessage("Correct !  \nDo you want to read the full article?");
                    nbBonneReponse++;

                    builder1.setCancelable(true);
                }
                else {
                    builder1.setMessage("False !  \nDo you want to read the full article?");

                    builder1.setCancelable(true);
                }


                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent fullArticleActivityIntent = new Intent(QuizzActivity.this, FullArticleActivity.class);
                                fullArticleActivityIntent.putExtra("idArticle", indiceQuestion);
                                fullArticleActivityIntent.putExtra("titre",articles.get(indiceQuestion).getTitle() );
                                fullArticleActivityIntent.putExtra("imgUrl",articles.get(indiceQuestion).getUrlToImage() );
                                fullArticleActivityIntent.putExtra("description",articles.get(indiceQuestion).getDescription() );
                                fullArticleActivityIntent.putExtra("content",articles.get(indiceQuestion).getContent() );
                                startActivity(fullArticleActivityIntent);
                                indiceQuestion++;
                                if(indiceQuestion != 5){
                                    LoadQuestion(indiceQuestion);
                                }else {
                                    Intent ScoreActivityIntent = new Intent(QuizzActivity.this, FinalScoreActivity.class);
                                    ScoreActivityIntent.putExtra("scoreFinal", nbBonneReponse);
                                    startActivity(ScoreActivityIntent);
                                }

                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                indiceQuestion++;
                                if(indiceQuestion != 5){
                                    LoadQuestion(indiceQuestion);
                                }else {
                                    Intent ScoreActivityIntent = new Intent(QuizzActivity.this, FinalScoreActivity.class);
                                    ScoreActivityIntent.putExtra("scoreFinal", nbBonneReponse);
                                    startActivity(ScoreActivityIntent);
                                }

                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }

        });


        btnreponse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(QuizzActivity.this);
                if((btnreponse2.getText().equals(articles.get(indiceQuestion).getTitle()))){
                    builder1.setMessage("Correct !  \nDo you want to read the full article?");
                    nbBonneReponse++;

                    builder1.setCancelable(true);
                }
                else {
                    builder1.setMessage("False !  \nDo you want to read the full article?");

                    builder1.setCancelable(true);
                }

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent fullArticleActivityIntent = new Intent(QuizzActivity.this, FullArticleActivity.class);
                                fullArticleActivityIntent.putExtra("idArticle", indiceQuestion);
                                fullArticleActivityIntent.putExtra("titre",articles.get(indiceQuestion).getTitle() );
                                fullArticleActivityIntent.putExtra("imgUrl",articles.get(indiceQuestion).getUrlToImage() );
                                fullArticleActivityIntent.putExtra("description",articles.get(indiceQuestion).getDescription() );
                                fullArticleActivityIntent.putExtra("content",articles.get(indiceQuestion).getContent() );
                                startActivity(fullArticleActivityIntent);

                                indiceQuestion++;
                                if(indiceQuestion != 5){
                                    LoadQuestion(indiceQuestion);
                                }else {
                                    Intent ScoreActivityIntent = new Intent(QuizzActivity.this, FinalScoreActivity.class);
                                    ScoreActivityIntent.putExtra("scoreFinal", nbBonneReponse);
                                    startActivity(ScoreActivityIntent);

                                }

                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                indiceQuestion++;
                                if(indiceQuestion != 5){
                                    LoadQuestion(indiceQuestion);
                                }else {
                                    Intent ScoreActivityIntent = new Intent(QuizzActivity.this, FinalScoreActivity.class);
                                    ScoreActivityIntent.putExtra("scoreFinal", nbBonneReponse);
                                    startActivity(ScoreActivityIntent);

                                }
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

            btnreponse3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(QuizzActivity.this);
                    if((btnreponse3.getText().equals(articles.get(indiceQuestion).getTitle()))){
                        builder1.setMessage("Correct !  \nDo you want to read the full article?");
                        nbBonneReponse++;

                        builder1.setCancelable(true);
                    }
                    else {
                        builder1.setMessage("False !  \nDo you want to read the full article?");

                        builder1.setCancelable(true);
                    }

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent fullArticleActivityIntent = new Intent(QuizzActivity.this, FullArticleActivity.class);
                                    fullArticleActivityIntent.putExtra("idArticle", indiceQuestion);
                                    fullArticleActivityIntent.putExtra("titre",articles.get(indiceQuestion).getTitle() );
                                    fullArticleActivityIntent.putExtra("imgUrl",articles.get(indiceQuestion).getUrlToImage() );
                                    fullArticleActivityIntent.putExtra("description",articles.get(indiceQuestion).getDescription() );
                                    fullArticleActivityIntent.putExtra("content",articles.get(indiceQuestion).getContent() );
                                    startActivity(fullArticleActivityIntent);

                                    indiceQuestion++;
                                    if(indiceQuestion != 5){
                                        LoadQuestion(indiceQuestion);
                                    }else {

                                    }

                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    indiceQuestion++;
                                    if(indiceQuestion != 5){
                                        LoadQuestion(indiceQuestion);
                                    }else {

                                    }
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            });

        btnreponse4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(QuizzActivity.this);
                if((btnreponse4.getText().equals(articles.get(indiceQuestion).getTitle()))){
                    builder1.setMessage("Correct !  \nDo you want to read the full article?");
                    nbBonneReponse++;

                    builder1.setCancelable(true);
                }
                else {
                    builder1.setMessage("False !  \nDo you want to read the full article?");

                    builder1.setCancelable(true);
                }

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent fullArticleActivityIntent = new Intent(QuizzActivity.this, FullArticleActivity.class);
                                fullArticleActivityIntent.putExtra("idArticle", indiceQuestion);
                                fullArticleActivityIntent.putExtra("titre",articles.get(indiceQuestion).getTitle() );
                                fullArticleActivityIntent.putExtra("imgUrl",articles.get(indiceQuestion).getUrlToImage() );
                                fullArticleActivityIntent.putExtra("description",articles.get(indiceQuestion).getDescription() );
                                fullArticleActivityIntent.putExtra("content",articles.get(indiceQuestion).getContent() );
                                startActivity(fullArticleActivityIntent);
                                indiceQuestion++;
                                if(indiceQuestion != 5){
                                    LoadQuestion(indiceQuestion);
                                }else {

                                }

                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                indiceQuestion++;
                                if(indiceQuestion != 5){
                                    LoadQuestion(indiceQuestion);
                                }else {

                                }
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


    }


}
