package com.example.tri_wizard_tournament;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CharacterActivity extends AppCompatActivity {

    String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        //Intent intent = getIntent();
        //id = intent.getStringExtra("_id");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        id = intent.getStringExtra("_id");
        joinUrl();
    }

    private String joinUrl() {
        char[] characters = id.toCharArray();
        for (char character : characters) {
            System.out.print(character + " ");
        }
        Toast.makeText(getApplicationContext(), characters + "", Toast.LENGTH_LONG).show();
        return characters.toString();
    }

    private void testAccessibilityOfId() {
        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
    }

    public class LoginQA extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(String... strings) {
//            LoginActivity.LoginInner loginInner = null;
//            try {
//                Thread.sleep(3000);
//                loginInner = new LoginActivity.LoginInner(strings[0]);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return loginInner.validateUser(strings[0], characters);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean status) {
            if (status.equals(true)) {
                Intent intent = new Intent(getApplicationContext(), com.example.tri_wizard_tournament.HousesList.class);
                startActivity(intent);
                //progressDialog.dismiss();
            } else {
                // progressDialog.dismiss();
                //Toast.makeText(LoginActivity.this, "Validation Failure", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
