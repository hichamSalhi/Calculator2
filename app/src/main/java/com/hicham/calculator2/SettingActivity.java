package com.hicham.calculator2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class SettingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void navBar(){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        getSupportActionBar().setTitle("About");
    }

    public void traitementMenu(){
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationview);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_Open,R.string.menu_Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        traitementMenu();
        navBar();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.devise){
            Intent deviseIntent = new Intent(this,DeviseActivity.class);
            startActivity(deviseIntent);
            return true;
        }else if(id == R.id.standard){
            Intent deviseIntent = new Intent(this,MainActivity.class);
            startActivity(deviseIntent);
            return true;
        }else if(id == R.id.volume){
            Intent volumeIntent = new Intent(SettingActivity.this, VolumeActivity.class);
            startActivity(volumeIntent);
            return true;
        }else if(id == R.id.area){
            Intent areaIntent = new Intent(SettingActivity.this,AreaActivity.class);
            startActivity(areaIntent);
            return true;
        }else if(id == R.id.length){
            Intent lengthIntent = new Intent(SettingActivity.this, LengthActivity.class);
            startActivity(lengthIntent);
            return true;
        }else if(id == R.id.weight) {
            Intent intent = new Intent(SettingActivity.this, WeightActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.setting){
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }else if(id == R.id.energy){
            Intent energieActi = new Intent(this, EnergyActivity.class);
            startActivity(energieActi);
            return true;
        }
        return true;
    }
}