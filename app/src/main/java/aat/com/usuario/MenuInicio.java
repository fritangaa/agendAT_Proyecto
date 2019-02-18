package aat.com.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import aat.com.R;

public class MenuInicio extends AppCompatActivity {

    private DatabaseReference usuarioNombre;
    private DatabaseReference usuarioCorreo;
    private DatabaseReference usuarioUbicacion;
    private DatabaseReference usuarioTelefono;
    private DatabaseReference usuarioValoracion;

    private FirebaseAuth firebaseAuth;
    private StorageReference mStorage;

    private TextView txtUsuarioNombre;
    private TextView txtUsuarioCorreo;
    private RatingBar valUsuarioValoracion;

    private Button btnInicioAC;
    private Button btnInicioCT;
    private Button btnInicioMC;
    private Button btnInicioCE;
    private Button btnInicioPR;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio);

        firebaseAuth = FirebaseAuth.getInstance();
        usuarioNombre= FirebaseDatabase.getInstance().getReference("/Usuario/").child(firebaseAuth.getUid()).child("datos").child("nombre");
        usuarioCorreo= FirebaseDatabase.getInstance().getReference("/Usuario/").child(firebaseAuth.getUid()).child("datos").child("correo");
        usuarioUbicacion= FirebaseDatabase.getInstance().getReference("/Usuario/").child(firebaseAuth.getUid()).child("datos").child("ubicacion");
        usuarioTelefono= FirebaseDatabase.getInstance().getReference("/Usuario/").child(firebaseAuth.getUid()).child("datos").child("telefono");
        usuarioValoracion= FirebaseDatabase.getInstance().getReference("/Usuario/").child(firebaseAuth.getUid()).child("datos").child("valoracion");

        txtUsuarioNombre = (TextView) findViewById(R.id.txtInicioNombre);
        txtUsuarioCorreo = (TextView) findViewById(R.id.txtInicioCorreo);
        valUsuarioValoracion = (RatingBar) findViewById(R.id.valInicioUsuario);

        btnInicioAC = (Button) findViewById(R.id.btnInicioArmarcita);
        btnInicioCT = (Button) findViewById(R.id.btnInicioCercati);
        btnInicioMC = (Button) findViewById(R.id.btnInicioMiscitas);
        btnInicioCE = (Button) findViewById(R.id.btnInicioCitaexp);
        btnInicioPR = (Button) findViewById(R.id.btnInicioPromo);

        //--------------------Obtener informacion de Firebase-----------------------------------

        //Obtener nombre del usuario
        usuarioNombre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtUsuarioNombre.setText(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //Obtener correo del usuario
        usuarioCorreo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtUsuarioCorreo.setText(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //Obtener valoracion del usuario
        usuarioValoracion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int valoracion = dataSnapshot.getValue(Integer.class);
                valUsuarioValoracion.setRating(valoracion);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //------------------------------------------------------------------------------

        btnInicioMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuInicio.this, MisCitas.class);
                startActivity(intent);
            }
        });

        btnInicioAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuInicio.this, AgendarCita.class);
                startActivity(intent);
            }
        });

        /*btnInicioCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuInicio.this, cercaTi.class);
                startActivity(intent);
                finish();
            }
        });*/

        btnInicioCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuInicio.this, CitaExpress.class);
                startActivity(intent);
            }
        });

        btnInicioPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuInicio.this, Promociones.class);
                startActivity(intent);
            }
        });


    }
}
