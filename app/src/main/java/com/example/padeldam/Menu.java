package com.example.padeldam;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Menu extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar menu;
    LinearLayout home,clientes,logout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Snackbar.make(findViewById(android.R.id.content),"Bienvenido "+currentUser.getEmail(),Snackbar.LENGTH_SHORT).show();

        ImageView ivPistas = findViewById(R.id.ivPistas);
        ImageView ivAlquiler = findViewById(R.id.ivAlquiler);

//

        ivPistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.aumento_imagen);
                ivPistas.startAnimation(anim);
                Intent i = new Intent(Menu.this, Pistas.class);
                startActivity(i);
            }
        });

        ivAlquiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.aumento_imagen);
                ivAlquiler.startAnimation(anim);
                Intent i = new Intent(Menu.this, Alquiler.class);
                startActivity(i);
            }
        });
//
//        drawerLayout = findViewById(R.id.drawer_layout);
        menu = (Toolbar) findViewById(R.id.menuToolbar);
//        home = findViewById(R.id.home);
//        clientes = findViewById(R.id.clientes);
//        logout = findViewById(R.id.logout);
//
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              recreate();
//            }
//        });
//        clientes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cambiarActivity(Menu.this, Clientes.class);
//            }
//        });
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               Toast.makeText(Menu.this, "logout", Toast.LENGTH_SHORT).show();
//            }
//        });



    }
    public void botonPrueba (View view){
        Intent i = new Intent(Menu.this, Clientes.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.itemC){
            Intent intent = new Intent(this,Clientes.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);    }

    public static void openDrawer(DrawerLayout dl){
        dl.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout dl){
      if(dl.isDrawerOpen(GravityCompat.START)){
          dl.closeDrawer(GravityCompat.START);
      }
    }
    public static void cambiarActivity (Activity a, Class b){
        Intent i = new Intent(a,b);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        a.startActivity(i);
        a.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}