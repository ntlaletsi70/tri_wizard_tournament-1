package com.example.tri_wizard_tournament;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tri_wizard_tournament.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.tri_wizard_tournament.ApplicationClass.HOUSES_URL;


public class HousesList extends AppCompatActivity {

    ListView lv_houses;
    RequestQueue queue;
    List<Object> id_nums = new ArrayList<>();
    Object charactersBack = new Object();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses_list);

        progressDialog = new ProgressDialog(this);
        lv_houses = findViewById(R.id.lv_houses);
        queue = Volley.newRequestQueue(this);

        progressDialog.setTitle("Retrieving  Houses");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final HousesListAsync housesListAsync = new HousesListAsync();
        housesListAsync.execute();

        lv_houses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 int index = (int) id;
                 Intent intent = new Intent(getApplicationContext(), HouseActivity.class);
                 intent.putExtra("_id", id_nums.get(index).toString());
                 startActivity(intent);
            }
        });
    }

    private class HousesListInner {
        private HousesListInner() {
            getAllHouses();
        }

        private List<Object> returnAllHouses(List<Object> _id_nums) {
            id_nums = _id_nums;
            return id_nums;
        }

        private List<Object> returnAllHouses() {
            return id_nums;
        }

        private void getAllHouses() {
            StringRequest jsonObjectRequest = new StringRequest
                    (Request.Method.GET, HOUSES_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                List<Object> house_names = ApplicationClass.getValuesForGivenKey(response, "name");
                                HousesAdapter housesAdapter = new HousesAdapter(house_names, getApplicationContext());
                                List<Object> id_nums = ApplicationClass.getValuesForGivenKey(response, "_id");
                                lv_houses.setAdapter(housesAdapter);

                                if (id_nums.size() > 0 && house_names.size() > 0) {
                                    returnAllHouses(id_nums);
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


    public class HousesListAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
                new HousesListInner();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Void void1) {
            progressDialog.dismiss();
        }
    }
}
