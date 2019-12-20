package com.example.findinggarage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.findinggarage.model.Locations;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Insert_Activity extends AppCompatActivity {
    DatabaseReference reff;
    Locations location;
    private Button mo, ca;
    private TextView name, lati, longti, contact, hom;
    private String locationID;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_);
        mo = (Button) findViewById(R.id.butAddM);
        ca = (Button) findViewById(R.id.butAddC);
        hom = (Button) findViewById(R.id.butHome);
        name = (TextView) findViewById(R.id.textName);
        lati = (TextView) findViewById(R.id.textLati);
        longti = (TextView) findViewById(R.id.textLongti);
        contact = (TextView) findViewById(R.id.textContact);


        hom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(Insert_Activity.this, "Bye", Toast.LENGTH_LONG).show();
            }
        });


        mo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDataM();
            }
        });

        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDataC();
            }
        });

    }

    public void AddDataM() {
        try {
            if (name.getText().toString().trim().equals(null) && lati.getText().toString().trim().equals(null) && longti.getText().toString().trim().equals(null)) {
                Toast.makeText(Insert_Activity.this, "Please complete the information", Toast.LENGTH_LONG).show();
            }else{
                try {
                    location = new Locations();

                    reff = FirebaseDatabase.getInstance().getReference().child("location/Moto");

                    Double la = Double.parseDouble(lati.getText().toString().trim());
                    Double ln = Double.parseDouble(longti.getText().toString().trim());

                    location.setName(name.getText().toString().trim());
                    location.setContact(contact.getText().toString().trim());
                    location.setLati(la);
                    location.setLonti(ln);

                    reff.push().setValue(location);
                    name.setText("");
                    lati.setText("");
                    longti.setText("");
                    contact.setText("");

                    Toast.makeText(Insert_Activity.this, "Inserted Success", Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(Insert_Activity.this, "Inserted Fail", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void AddDataC() {
        try {
            if (name.getText().toString().trim().equals(null) && lati.getText().toString().trim().equals(null) && longti.getText().toString().trim().equals(null)) {
                Toast.makeText(Insert_Activity.this, "Please complete the information", Toast.LENGTH_LONG).show();
            }else{
                try {
                    location = new Locations();

                    reff = FirebaseDatabase.getInstance().getReference().child("location/Car");

                    Double la = Double.parseDouble(lati.getText().toString().trim());
                    Double ln = Double.parseDouble(longti.getText().toString().trim());

                    location.setName(name.getText().toString().trim());
                    location.setContact(contact.getText().toString().trim());
                    location.setLati(la);
                    location.setLonti(ln);

                    reff.push().setValue(location);
                    name.setText("");
                    lati.setText("");
                    longti.setText("");
                    contact.setText("");

                    Toast.makeText(Insert_Activity.this, "Inserted Success", Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(Insert_Activity.this, "Inserted Fail", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


}

