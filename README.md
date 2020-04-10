# App Mobile - Up To Date

![App%20Mobile%20Up%20To%20Date/Up-To-Date.png](App%20Mobile%20Up%20To%20Date/Up-To-Date.png)

# Summary :

1. Presentation of the functionalities
2. More details about the Api
3. Ameliorations axis

## 1 - Presentation of the functionalities

![App%20Mobile%20Up%20To%20Date/Screen_Shot_2020-04-08_at_11.49.38_(2).png](App%20Mobile%20Up%20To%20Date/Screen_Shot_2020-04-08_at_11.49.38_(2).png)

Up-to-date is an app that permits you to stay 'Up to date'. Everyday you will have a quizz of 5 questions where an image will be presented to you and you will have to guess the title of it.

### 1 - 1) Menu

![App%20Mobile%20Up%20To%20Date/Screenshot_1586508400.png](App%20Mobile%20Up%20To%20Date/Screenshot_1586508400.png)

Type a name in order to start the quizz !

### 1 - 2) Question 1/5 (Question view)

### 1 - 3) Question 1/5 (Alert Correct Answer View)

![App%20Mobile%20Up%20To%20Date/Screenshot_1586508721.png](App%20Mobile%20Up%20To%20Date/Screenshot_1586508721.png)

![App%20Mobile%20Up%20To%20Date/Screenshot_1586508794.png](App%20Mobile%20Up%20To%20Date/Screenshot_1586508794.png)

I have an image and four news titles. I have to guess which title correspond to the image. If I'm right I earn a point, if i'm not I don't earn any point.

I chose the correct answer. I earned a point. I have the opportunity to read the full article. I'm not going to miss it.

### 1 - 4) Question 1/5 (Full article View)

### 1 - 5) Question 2/5 (Question view)

![App%20Mobile%20Up%20To%20Date/Screenshot_1586508857.png](App%20Mobile%20Up%20To%20Date/Screenshot_1586508857.png)

![App%20Mobile%20Up%20To%20Date/Screenshot_1586509029.png](App%20Mobile%20Up%20To%20Date/Screenshot_1586509029.png)

I can read the full article and when I will touch the 'return button' of my smartphone it will directly lead me to the next question.

Let's assume now that I mess up on purpose and that I choose a wrong answer.

### 1 - 6) Question 2/5 (Alert Wrong Answer View)

### 1 - 7) Question 3/5 (Question View)

![App%20Mobile%20Up%20To%20Date/Screenshot_1586509119.png](App%20Mobile%20Up%20To%20Date/Screenshot_1586509119.png)

![App%20Mobile%20Up%20To%20Date/Screenshot_1586509215.png](App%20Mobile%20Up%20To%20Date/Screenshot_1586509215.png)

Well I was wrong so I didn't earn any points however I still have the right to read the full article. Let's say that i am very stubborn and choose not to read the full article.

You saw, it directly led me to the next question !  Now this one is way too obvious I have to get that one right !

### 1 - 8) Question 3/5 (Alert Right Answer View)

### 1 - 8) Question 3/5 (Full article View)

![App%20Mobile%20Up%20To%20Date/Screenshot_1586509279.png](App%20Mobile%20Up%20To%20Date/Screenshot_1586509279.png)

![App%20Mobile%20Up%20To%20Date/Screenshot_1586509391.png](App%20Mobile%20Up%20To%20Date/Screenshot_1586509391.png)

I knew it ! But still I want more information on this subject !

Ohh very interesting ! Now I think you pretty much understood how the app works so let's skip the two next answers !

### 1 - 9) Final score view

![App%20Mobile%20Up%20To%20Date/Screenshot_1586509477.png](App%20Mobile%20Up%20To%20Date/Screenshot_1586509477.png)

Wow guess i'm pretty 'up-to-date'. The see you tommorow button will lead you to the menu. Tomorrow the question will be automatically changed and will be different.

## 2 - More details about the Api

the Api that I used for this project is called news.api [https://newsapi.org](https://newsapi.org/)

![App%20Mobile%20Up%20To%20Date/Screen_Shot_2020-04-10_at_11.27.55.png](App%20Mobile%20Up%20To%20Date/Screen_Shot_2020-04-10_at_11.27.55.png)

this is the url that we used (Api key has been removed :) 

    String url = "https://newsapi.org/v2/top-headlines?country=fr&apiKey=";

So you can see that we are calling the top headlines in France.

The Api will respond with a JSON file which will be looking like that :

    {
    "status": "ok",
    "totalResults": 34,
    -"articles": [
    -{
    -"source": {
    "id": "lequipe",
    "name": "L'equipe"
    },
    "author": "Rédaction",
    "title": "Kingsley Coman (Bayern Munich) : « On s'entraîne avec une distance de sécurité » - Foot - ALL - Bayern - L'Équipe.fr",
    "description": "Dans un entretien à Eurosport, l'attaquant français du Bayern Munich Kingsley Coman est revenu sur les conditions particulières de l'entraînement, durant la pandémie de Covid-19.",
    "url": "https://www.lequipe.fr/Football/Actualites/Kingsley-coman-bayern-munich-on-s-entraine-avec-une-distance-de-securite/1126312",
    "urlToImage": "https://medias.lequipe.fr/img-photo-jpg/coman-et-le-bayern-s-entrainent-p-lahalle-l-equipe/1500000001325965/0:0,1995:1330-640-427-75/53f84.jpg",
    "publishedAt": "2020-04-10T07:40:18Z",
    "content": "Alors que les clubs allemands peuvent continuer à s'entraîner pendant la pandémie de Covid-19, l'attaquant français du Bayern Munich Kingsley Coman a témoigné des conditions particulières dans lesquelles sont réalisées les sessions. « On s'entraîne avec une d… [+360 chars]"
    },
    
    ...
    ]
    }

 This is the code that takes out the data from the JSON file and transforms it into a java object :

    					mQueue = Volley.newRequestQueue(this);
    
            String url = "https://newsapi.org/v2/top-headlines?country=fr&apiKey=5e5b4f88a60f488aa44b243036dfbad7";
    
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("Json request", String.valueOf(response));
                            try {
                                JSONArray jsonArray = response.getJSONArray("articles");
    
                                for (int i = 0; i < nbArticles; i++) {
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

## 3 - Amelioration Axis

- Train an A.I to choose articles that are really different from one another.
- Make the possibility for the user to choose the subject of the articles.
- Train an A.I to choose the articles corresponding to the subject that the user wants. (the JSON file doesn't have any 'subject' or 'theme' attributes.