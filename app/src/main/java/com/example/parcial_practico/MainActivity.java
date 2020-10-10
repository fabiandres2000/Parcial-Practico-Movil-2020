package com.example.parcial_practico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView menu;
    NavController nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.menu_principal);
        nav = Navigation.findNavController(this,R.id.fragment3);
        NavigationUI.setupWithNavController(menu, nav);
        databaseHelper db = new databaseHelper(this,"parcialpractico",null,1);
    }
}