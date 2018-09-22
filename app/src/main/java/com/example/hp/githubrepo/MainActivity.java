package com.example.hp.githubrepo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.githubrepo.ApiGit.GitApi;
import com.example.hp.githubrepo.Beans.GitBeans;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button b1;
    TextView textView,textView2,textView3,textView4,textView5;

    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView5);
        textView2= (TextView) findViewById(R.id.textView8);
        //textView3= (TextView) findViewById(R.id.textView9);
        textView4= (TextView) findViewById(R.id.textView);
        textView5= (TextView) findViewById(R.id.textView2);

        b1.setOnClickListener(this);
        editText = (EditText) findViewById(R.id.editText2);

    }
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) { //for closing the keyboard
        closeKeyboard();


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        GitApi gi = retrofit.create(GitApi.class);
        String name = editText.getText().toString().equals("") ? "kumararunav" : editText.getText().toString();
        Call<GitBeans> gb = gi.getDetails(name);
        gb.enqueue(new Callback<GitBeans>() {
            @Override
            public void onResponse(@NonNull Call<GitBeans> call, @NonNull Response<GitBeans> response) {

                GitBeans beans = response.body();
                assert beans != null;
                try {
                    textView.setText("Public repo: " + beans.getPublicRepos().toString());
                    textView2.setText("Login: " + beans.getLogin().toString());
                    // textView3.setText("Bio: "+beans.getBio().toString());
                    textView4.setText("Followers: " + beans.getFollowers().toString());
                    textView5.setText("Following: " + beans.getFollowing().toString());
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this,"INVALID USERNAME",Toast.LENGTH_SHORT).show();
                    textView.setText("");
                    textView2.setText("");
                    textView4.setText("");
                    textView5.setText("");
                }

            }

            @Override
            public void onFailure(@NonNull Call<GitBeans> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Failed to access", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
