package com.hicham.calculator2;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.hicham.calculator2.AreaActivity;
import com.hicham.calculator2.R;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Les attributs du menu
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle  actionBarDrawerToggle;

    //Les attributs du text view
    TextView result;

    //Les attributs des boutons
    MaterialButton buttonAC, buttonSquaredText , buttonModulo, buttonDiv , button1 ,button2 ,
            button3,button4,button5 , button6 , button7 , button8 , button9 , button0 ,
            buttonMulti , buttonMin , buttonPlus ,buttonEqual , buttonV ;

    //Image buttons :
    ImageButton buttonBackSpace;

    //Les attributs qui aide au calcule
    private double chiffre1;
    private boolean clicOperateur = false;
    private boolean update = false;
    private String operateur = "";

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
        setContentView(R.layout.activity_main);

        //Changer la couleur de navbar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        //Changer le titre de l'activite
        getSupportActionBar().setTitle("Calculator");

        //Creation du menu (drawerLayout)
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationview);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_Open,R.string.menu_Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// pour le retour
        navigationView.setNavigationItemSelectedListener(this);

        //Associer les attributs a leur id
        result = findViewById(R.id.result);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonModulo = findViewById(R.id.button_modulo);
        buttonAC = findViewById(R.id.buttonac);
        buttonDiv = findViewById(R.id.buttondivided);
        buttonMulti = findViewById(R.id.buttonmult);
        buttonPlus = findViewById(R.id.buttonplus);
        buttonV = findViewById(R.id.buttonv);
        buttonSquaredText = findViewById(R.id.puissance_carree);
        buttonMin = findViewById(R.id.buttonmin);
        buttonEqual = findViewById(R.id.buttonequal);

        //Operations :
        //Operations d'addition
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusClick();
            }
        });

        buttonSquaredText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                puissanceCarreeFonction();
            }
        });

        buttonModulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moduloClick();
            }
        });

        //Operations de soustraction
        buttonMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moinsClick();
            }
        });

        //Operations de division
        buttonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                divClick();
            }
        });

        //Operations de produit
        buttonMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiClick();
            }
        });

        //Reset CLick
        buttonAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acClick();
            }
        });

        //Egal click
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                egalClick();
            }
        });

        //Buttons
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chiffreClick("0");
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chiffreClick("1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chiffreClick("2");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chiffreClick("3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chiffreClick("4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chiffreClick("5");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chiffreClick("6");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chiffreClick("7");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chiffreClick("8");
            }
        });

        buttonV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chiffreClick(".");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chiffreClick("9");
            }
        });

    }

    //fonction valeur absolu
    private void valeurAbsolu(){
        if(clicOperateur){
            calcul();
            result.setText(String.valueOf(chiffre1));
        }else{
            chiffre1 = Double.valueOf(result.getText().toString()).doubleValue();
            clicOperateur = true;
        }
        operateur = "valeurAbsolue";
        update = true;
    }

    //fonction de puissance au carree
    private void puissanceCarreeFonction() {
        if(clicOperateur){
            calcul();
            result.setText(String.valueOf(chiffre1));
        }else{
            chiffre1 = Double.valueOf(result.getText().toString()).doubleValue();
            clicOperateur = true;
        }
        operateur = "xcarree";
        update = true;
    }

    //Fonctions du modulo
    private void moduloClick() {
        if(clicOperateur){
            calcul();
            result.setText(String.valueOf(chiffre1));
        }else{
            chiffre1 = Double.valueOf(result.getText().toString()).doubleValue();
            clicOperateur = true;
        }
        operateur = "%";
        update = true;
    }

    //Fonctions chiffreClick
    public void chiffreClick(String chiffre){
        if(update){
            update = false;
        }else{
            chiffre  = result.getText() + chiffre;
        }
        result.setText(chiffre);
    }



    //Fonctions moins
    public void moinsClick(){
        if(clicOperateur){
            calcul();
            result.setText(String.valueOf(chiffre1));
        }else{
            chiffre1 = Double.valueOf(result.getText().toString()).doubleValue();
            clicOperateur = true;
        }
        operateur = "-";
        update = true;
    }

    //Fonctions plusClick
    public void plusClick(){
        if(clicOperateur){
            calcul();
            result.setText(String.valueOf(chiffre1));
        }else{
            chiffre1 = Double.valueOf(result.getText().toString()).doubleValue();
            clicOperateur = true;
        }
        operateur = "+";
        update = true;
    }

    //Fonctions div
    public void divClick(){
        if(clicOperateur){
            calcul();
            result.setText(String.valueOf(chiffre1));
        }else{
            chiffre1 = Double.valueOf(result.getText().toString()).doubleValue();
            clicOperateur = true;
        }
        operateur = "/";
        update = true;
    }

    //fonctions de multiplications
    public void multiClick(){
        if(clicOperateur){
            calcul();
            result.setText(String.valueOf(chiffre1));
        }else{
            chiffre1 = Double.valueOf(result.getText().toString()).doubleValue();
            clicOperateur = true;
        }
        operateur = "*";
        update = true;
    }

    //fonctions d'egal
    public void egalClick(){
        calcul();
        clicOperateur = true;
        update = true;
    }

    //bouton de reset
    public void acClick(){
        clicOperateur = false;
        update = true;
        chiffre1 = 0;
        operateur = "";
        result.setText("0");
    }

    private void calcul() {
        if (operateur.equals("+")) {
            chiffre1 = chiffre1 + Double.valueOf(result.getText().toString()).doubleValue();
            result.setText(String.valueOf(chiffre1));
        }

        if (operateur.equals("-")) {
            chiffre1 = chiffre1 - Double.valueOf(result.getText().toString()).doubleValue();
            result.setText(String.valueOf(chiffre1));
        }

        if (operateur.equals("*")) {
            chiffre1 = chiffre1 * Double.valueOf(result.getText().toString()).doubleValue();
            result.setText(String.valueOf(chiffre1));
        }

        if (operateur.equals("/")) {
            try {
                chiffre1 = chiffre1 / Double.valueOf(result.getText().toString()).doubleValue();
                result.setText(String.valueOf(chiffre1));
            } catch (ArithmeticException e) {
                result.setText("0");
            }
        }

        if (operateur.equals("%")) {
            try {
                chiffre1 = chiffre1 % Double.valueOf(result.getText().toString()).doubleValue();
                result.setText(String.valueOf(chiffre1));
            } catch (ArithmeticException e) {
                result.setText("0");
            }
        }

        if(operateur.equals("xcarree")){
            chiffre1 = Double.valueOf(result.getText().toString()).doubleValue() * Double.valueOf(result.getText().toString()).doubleValue();
            result.setText(String.valueOf(chiffre1));
        }

        if(operateur.equals("valeurAbsolue")){
            chiffre1 = Math.abs(Double.valueOf(result.getText().toString()).doubleValue());
            result.setText(String.valueOf(chiffre1));
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.devise){
            Intent deviseIntent = new Intent(this,DeviseActivity.class);
            startActivity(deviseIntent);
            return true;
        }else if(id == R.id.standard){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(id == R.id.volume){
            Intent volumeIntent = new Intent(MainActivity.this, VolumeActivity.class);
            startActivity(volumeIntent);
            return true;
        }else if(id == R.id.area){
            Intent areaIntent = new Intent(MainActivity.this,AreaActivity.class);
            startActivity(areaIntent);
            return true;
        }else if(id == R.id.length){
            Intent lengthIntent = new Intent(MainActivity.this, LengthActivity.class);
            startActivity(lengthIntent);
            return true;
        }else if(id == R.id.weight) {
            Intent intent = new Intent(MainActivity.this, WeightActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.setting){
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.energy){
            Intent intent = new Intent(MainActivity.this, EnergyActivity.class);
            startActivity(intent);
            return true;
        }
        return true;
    }
}