package com.example.tri_wizard_tournament;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//package com.example.tri_wizard_tournament;

public class UserOption extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_option);

        button1 = (Button) findViewById(R.id.buttonHouseList);
        button2 = (Button) findViewById(R.id.buttonSpellsRegistry);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });


    }
    public void openActivity2(){
        Intent intent = new Intent(this, HousesList.class);
        startActivity(intent);
    }
    public void openActivity3(){
       Intent intent = new Intent(this, Spells.class);
       startActivity(intent);
    }


}

