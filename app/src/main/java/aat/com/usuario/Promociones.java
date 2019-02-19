package aat.com.usuario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import aat.com.R;

public class Promociones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promociones);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent siguiente = new Intent(Promociones.this, MenuInicio.class);//vamos a la ventana de la confirmacion
        startActivity(siguiente);
        finish();
    }

}
