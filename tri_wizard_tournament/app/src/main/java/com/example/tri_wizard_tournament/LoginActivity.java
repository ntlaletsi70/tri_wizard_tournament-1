package com.example.tri_wizard_tournament;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    List<Character> characters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId = (EditText) findViewById(R.id.etId);
        queue = Volley.newRequestQueue(this);
    }

    public void btnLogin(View v) {
        String userId = etId.getText().toString().trim();

        if (userId.isEmpty()) {
            Toast.makeText(this, "Please enter a user id", Toast.LENGTH_SHORT).show();
        } else {
            LoginInner loginInner = new LoginInner();
        }
    }

    private class LoginInner {
        private LoginInner() {
            //characters = (Character)getAllCharacters();
        }

        private List<Object> getAllCharacters() {
            List<Object> returnObjects = new ArrayList<>();

            StringRequest jsonObjectRequest = new StringRequest
                    (Request.Method.GET, CHARACTERS_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                List<Object> objects = ApplicationClass.getValuesForGivenKey(response, "_id");
                                returnObjects.addAll(objects);
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
            return returnObjects;
        }
    }
}
