package aat.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import aat.com.preferencias.GuardarUsuario;
import aat.com.usuario.MenuInicio;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference;//temporal

    private FirebaseAuth firebaseAuth; //Firebase

    private MaterialDialog dialog; //Dialog

    private boolean isActivateRecuerda; //Boolean de recuerdame

    private EditText txtCorreo;
    private EditText txtPass;

    private CheckBox ckRecuerdame;

    private Button btnInicio;
    private Button btnRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

                databaseReference= FirebaseDatabase.getInstance().getReference();//temp

        if(GuardarUsuario.obtenerPreferenceBoolean(this,GuardarUsuario.PREFERENCE_ESTADO_BUTTON_SESION)){
            Intent intencion = new Intent(getApplication(), MenuInicio.class);
            startActivity(intencion);
            finish();
        }

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        txtCorreo = findViewById(R.id.txtCorreoLogin);
        txtPass = findViewById(R.id.txtPassLogin);

        ckRecuerdame = findViewById (R.id.ckRecuerda);

        btnInicio = findViewById(R.id.btnInicioLogin);
        btnRegistro = findViewById(R.id.btnRegistroLogin);

        ckRecuerdame.setOnClickListener(new View.OnClickListener() {//asignar valor al checkbox
            //ACTIVADO
            @Override
            public void onClick(View v) {
                if(ckRecuerdame.isChecked()){
                    isActivateRecuerda = true;
                }else{
                    isActivateRecuerda = false;
                }
            }
        });




        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)//contruir dialog
                .title("Iniciando Sesión")
                .content("Espere...")
                .cancelable(false)
                .progress(true, 0);
        dialog = builder.build();


        btnInicio.setOnClickListener(new View.OnClickListener() {//pasar a la siguiente pantalla
            @Override
            public void onClick(View v) {//el evento de click para confirmar la cita
                loguearUsuario();
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {//pasar a la siguiente pantalla
            @Override
            public void onClick(View v) {//el evento de click para confirmar la cita
                databaseReference.child("Cliente").push().child("datos").child("nom").setValue(txtCorreo.getText().toString());
                Toast.makeText(Login.this, "datos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loguearUsuario() {
        //Obtenemos el email y la contraseña desde las cajas de texto
        final String correo = txtCorreo.getText().toString().trim();
        String password = txtPass.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(correo)) {//(precio.equals(""))
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.show();

        //loguear usuario
        firebaseAuth.signInWithEmailAndPassword(correo, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            GuardarUsuario.savePreferenceBoolean(Login.this,ckRecuerdame.isChecked(),GuardarUsuario.PREFERENCE_ESTADO_BUTTON_SESION);
                            Toast.makeText(Login.this, "Bienvenido: " + correo, Toast.LENGTH_SHORT).show();
                            Intent intencion = new Intent(getApplication(), MenuInicio.class);
                            startActivity(intencion);
                            finish();

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(Login.this, "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Login.this, "No se pudo registrar el usuario ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        dialog.dismiss();
                    }
                });


    }
}
