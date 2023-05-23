package com.example.casino.drawerMenu;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.casino.R;
import com.example.casino.activities.CuentaUser;
import com.example.casino.activities.Inicio_Activity;
import com.example.casino.activities.Inicio_Activity2;
import com.example.casino.activities.Ruleta_Activity;
import com.google.android.material.navigation.NavigationView;

public class DrawerBase_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {

        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);

        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            case R.id.nav_inicio:
                startActivity(new Intent(this, Inicio_Activity2.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_datos:
                startActivity(new Intent(this, CuentaUser.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_ruleta:
                startActivity(new Intent(this, Ruleta_Activity.class));
                overridePendingTransition(0, 0);
                break;

/*            case R.id.nav_jackpot:
                startActivity(new Intent(this, Inicio_Activity2.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_blackjack:
                startActivity(new Intent(this, Inicio_Activity2.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_trofeos:
                startActivity(new Intent(this, Inicio_Activity2.class));
                overridePendingTransition(0, 0);
                break;*/

            case R.id.nav_cerrarsesion:
                startActivity(new Intent(this, Inicio_Activity.class));
                overridePendingTransition(0, 0);
                break;
        }
        return false;
    }

    protected void allocateActivityTitle(String titleString) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleString);
        }
    }
}