package aat.com.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.florent37.bubbletab.BubbleTab;

import aat.com.R;

public class AgendarCita extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private AgendarCitaAdapter adapter;
    private BubbleTab tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_cita);



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.pager);
        adapter = new AgendarCitaAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent siguiente = new Intent(AgendarCita.this, MenuInicio.class);//vamos a la ventana de la confirmacion
        startActivity(siguiente);
        finish();
    }

}
