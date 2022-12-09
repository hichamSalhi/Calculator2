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

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class EnergyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //Attribut pour le convertisseur :
    TextView convertFromDropDownTextView, convertToDropDownTextView ,conversionRateText;
    EditText finalConversion;
    ArrayList<String> arrayList;
    Dialog fromDialog,toDialog;
    Button conversionButton;
    String convertFromValue, convertToValue, conversionValue;
    String[] units = {"j","kj"};


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void navBar(){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        getSupportActionBar().setTitle("Energy");
    }

    public void menuControle(){
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_Open,R.string.menu_Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy);
        menuControle();
        navBar();


        convertFromDropDownTextView = findViewById(R.id.convertFromDropDownMenu);
        convertToDropDownTextView = findViewById(R.id.convertToDropDownMenu);
        conversionButton = findViewById(R.id.ConversionButton);
        conversionRateText = findViewById(R.id.conversionRateText);
        finalConversion = findViewById(R.id.finalConversion);

        arrayList = new ArrayList<>();
        for(String i : units){
            arrayList.add(i);
        }

        convertFromDropDownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDialog = new Dialog(EnergyActivity.this);
                fromDialog.setContentView(R.layout.spinner_layout_from);
                fromDialog.getWindow().setLayout(900,800);
                fromDialog.show();

                EditText editText = fromDialog.findViewById(R.id.editTextSearchFrom);
                ListView listView = fromDialog.findViewById(R.id.listViewFrom);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(EnergyActivity.this, android.R.layout.simple_list_item_1,arrayList);
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

                toDialog = new Dialog(EnergyActivity.this);
                toDialog.setContentView(R.layout.spinner_layout_to);
                toDialog.getWindow().setLayout(900,800);
                toDialog.show();

                EditText editText = toDialog.findViewById(R.id.editTextSearchTo);
                ListView listView = toDialog.findViewById(R.id.listViewTo);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(EnergyActivity.this, android.R.layout.simple_list_item_1,arrayList);
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
                if(convertFromValue == "j" && convertToValue == "kj"){
                    conversionValue = String.valueOf(Double.valueOf(finalConversion.getText().toString()) / 1000);
                    conversionRateText.setText(conversionValue);
                }else if(convertFromValue == "kj" && convertToValue == "j"){
                    conversionValue = String.valueOf(Double.valueOf(finalConversion.getText().toString()) * 1000);
                    conversionRateText.setText(conversionValue);
                }else if(convertToValue == convertFromValue){
                    conversionValue = String.valueOf(Double.valueOf(finalConversion.getText().toString()));
                    conversionRateText.setText(conversionValue);
                }else{
                    Toast.makeText(EnergyActivity.this,"Error",Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        switch (i){
            case R.id.area:
                Intent areaIntent = new Intent(this,AreaActivity.class);
                startActivity(areaIntent);
                return true;
            case R.id.standard:
                Intent standardIntent = new Intent(this,MainActivity.class);
                startActivity(standardIntent);
                return true;
            case R.id.devise:
                Intent deviseIntent  = new Intent(this,DeviseActivity.class);
                startActivity(deviseIntent);
                return true;
            case R.id.volume:
                Intent volumeIntent = new Intent(this,VolumeActivity.class);
                startActivity(volumeIntent);
                return true;

            case R.id.length:
                Intent lengthIntent = new Intent(this,LengthActivity.class);
                startActivity(lengthIntent);
                return true;
            case R.id.weight:
                Intent intent = new Intent(this,WeightActivity.class);
                startActivity(intent);
                return true;
            case R.id.setting:
                Intent settingActivity = new Intent(this,SettingActivity.class);
                startActivity(settingActivity);
                return true;
            case R.id.energy:
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
        }
        return true;
    }
}