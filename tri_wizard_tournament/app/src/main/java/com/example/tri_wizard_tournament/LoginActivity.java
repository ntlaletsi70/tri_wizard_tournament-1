package com.example.tri_wizard_tournament;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import static com.example.tri_wizard_tournament.ApplicationClass.CHARACTERS_URL;

public class LoginActivity extends AppCompatActivity {
    EditText etId;
    RequestQueue queue;
    List<Object> characters = new ArrayList<>();
    List<Object> charactersBack = new ArrayList<>();
    ProgressDialog progressDialog;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId = findViewById(R.id.etId);
        queue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
    }

    public void btnLogin(View v) {
        String userId = etId.getText().toString().trim();

        if (userId.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter  Your Id", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.setTitle("Logging you in");
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            if (connectionAvailable()) {
                final LoginQA myAsyncTask = new LoginQA();
                myAsyncTask.execute(userId);
            }
        }
    }

    private boolean connectionAvailable() {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                connected = true;
            } else {
                connected = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
            }
        }
        return connected;
    }

    private class LoginInner {
        private LoginInner(String userId) {
            getAllCharacters();
            charactersBack = returnAllCharacters();
        }

        public boolean validateUser(String userId, List<Object> characters) {
            boolean status = false;
            for (int i = 0; i < characters.size() && status == false; i++) {
                Object character = characters.get(i);
                status = character.equals(userId);
            }
            return status;
        }
        private List<Object> returnAllCharacters(List<Object> returnObjects) {
            characters = returnObjects;
            return characters;
        }

        private List<Object> returnAllCharacters() {
            return characters;
        }

        private void getAllCharacters() {
            StringRequest jsonObjectRequest = new StringRequest
                    (Request.Method.GET, CHARACTERS_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                List<Object> objects = ApplicationClass.getValuesForGivenKey(response, "_id");
                                if (objects.size() > 0) {
                                    returnAllCharacters(objects);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            queue.add(jsonObjectRequest);
        }
    }

    public class LoginQA extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            LoginInner loginInner = null;
            try {
                Thread.sleep(3000);
                loginInner = new LoginInner(strings[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return loginInner.validateUser(strings[0], characters);
        }

        @Override
        protected void onPostExecute(Boolean status) {
            if (status.equals(true)) {
                Intent intent = new Intent(getApplicationContext(), UserOption.class);
                startActivity(intent);
                progressDialog.dismiss();
            } else {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Validation Failure", Toast.LENGTH_SHORT).show();
            }
        }
    }
}