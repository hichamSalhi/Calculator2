package com.hicham.calculator2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class DeviseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Attribut du menu :
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //Attribut pour le convertisseur de devise :
    TextView convertFromDropDownTextView, convertToDropDownTextView ,conversionRateText;
    EditText amountToConvert;
    ArrayList<String> arrayList;
    Dialog fromDialog,toDialog;
    Button conversionButton;
    String convertFromValue, convertToValue, conversionValue;
    String[] country = {"EUR","MAD","USD"};

    //Controle du menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devise);
        navBar();
        menuControle();

        convertFromDropDownTextView = findViewById(R.id.convertFromDropDownMenu);
        convertToDropDownTextView = findViewById(R.id.convertToDropDownMenu);
        conversionButton = findViewById(R.id.ConversionButton);
        conversionRateText = findViewById(R.id.conversionRateText);
        amountToConvert = findViewById(R.id.amountToConvertValueEditText);

        arrayList = new ArrayList<>();
        for(String i : country){
            arrayList.add(i);
        }

        convertFromDropDownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDialog = new Dialog(DeviseActivity.this);
                fromDialog.setContentView(R.layout.spinner_layout_from);
                fromDialog.getWindow().setLayout(900,800);
                fromDialog.show();

                EditText editText = fromDialog.findViewById(R.id.editTextSearchFrom);
                ListView listView = fromDialog.findViewById(R.id.listViewFrom);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(DeviseActivity.this, android.R.layout.simple_list_item_1,arrayList);
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        convertFromDropDownTextView.setText(adapter.getItem(i));
                        fromDialog.dismiss();
                        convertFromValue = adapter.getItem(i);
                    }
                });

            }
        });


        convertToDropDownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toDialog = new Dialog(DeviseActivity.this);
                toDialog.setContentView(R.layout.spinner_layout_to);
                toDialog.getWindow().setLayout(900,800);
                toDialog.show();

                EditText editText = toDialog.findViewById(R.id.editTextSearchTo);
                ListView listView = toDialog.findViewById(R.id.listViewTo);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(DeviseActivity.this, android.R.layout.simple_list_item_1,arrayList);
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        convertToDropDownTextView.setText(adapter.getItem(i));
                        toDialog.dismiss();
                        convertToValue = adapter.getItem(i);
                    }
                });

            }
        });


        conversionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(convertFromValue == "MAD" && convertToValue == "EUR"){
                    conversionValue = String.valueOf(Double.valueOf(amountToConvert.getText().toString()) * 0.090);
                    conversionRateText.setText(conversionValue);
                }else if(convertFromValue == "EUR" && convertToValue == "MAD"){
                    conversionValue = String.valueOf(Double.valueOf(amountToConvert.getText().toString()) / 0.090);
                    conversionRateText.setText(conversionValue);
                }else if(convertToValue == convertFromValue){
                    conversionValue = String.valueOf(Double.valueOf(amountToConvert.getText().toString()));
                    conversionRateText.setText(conversionValue);
                }else if(convertFromValue == "MAD" && convertToValue == "USD"){
                    conversionValue = String.valueOf(Double.valueOf(amountToConvert.getText().toString()) * 0.094);
                    conversionRateText.setText(conversionValue);
                }else if(convertFromValue == "EUR" && convertToValue == "USD"){
                    conversionValue = String.valueOf(Double.valueOf(amountToConvert.getText().toString()) * 1.03);
                    conversionRateText.setText(conversionValue);
                }else if(convertFromValue == "USD" && convertToValue == "EUR"){
                    conversionValue = String.valueOf(Double.valueOf(amountToConvert.getText().toString()) / 1.03);
                    conversionRateText.setText(conversionValue);
                }else if(convertFromValue == "USD" && convertToValue == "MAD"){
                    conversionValue = String.valueOf(Double.valueOf(amountToConvert.getText().toString()) / 0.094);
                    conversionRateText.setText(conversionValue);
                }else{
                    Toast.makeText(DeviseActivity.this,"Error",Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    public void navBar(){
        //Changer la couleur de navbar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        //Changer le titre de l'activite
        getSupportActionBar().setTitle("Currency");
    }

    public void menuControle(){
        //Creation du menu (drawerLayout)
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationview);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_Open,R.string.menu_Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// pour le retour
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.standard){
            Intent MainIntent = new Intent(this,MainActivity.class);
            startActivity(MainIntent);
            return true;
        }else if(id == R.id.devise){
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }else if(id == R.id.volume){
            Intent volumeIntent = new Intent(DeviseActivity.this, VolumeActivity.class);
            startActivity(volumeIntent);
            return true;
        }else if(id == R.id.area){
            Intent areaIntent = new Intent(this,AreaActivity.class);
            startActivity(areaIntent);
            return true;
        }else if(id == R.id.length){
            Intent lengthIntent = new Intent(this,LengthActivity.class);
            startActivity(lengthIntent);
            return true;
        }else if(id == R.id.weight){
            Intent intent = new Intent(this,WeightActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.setting){
            Intent settingActivity = new Intent(this,SettingActivity.class);
            startActivity(settingActivity);
            return true;
        }else if(R.id.energy == id){
            Intent energieActi = new Intent(this, EnergyActivity.class);
            startActivity(energieActi);
            return true;
        }
        return true;
    }

}