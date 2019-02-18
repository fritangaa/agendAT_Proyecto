package aat.com.usuario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import aat.com.R;
import aat.com.clases.ItemLongClickListener;

public class MisCitas extends AppCompatActivity {

    private DatabaseReference usuarioCitas;
    private FirebaseAuth firebaseAuth;

    private FirebaseRecyclerAdapter<objCita,objCitaViewHolder.ViewHolder> adapterListaCitas;

    private RecyclerView listaCitas;

    private TextView txtCitaLugar;
    private TextView txtCitaFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_citas);

        firebaseAuth = FirebaseAuth.getInstance();
        usuarioCitas= FirebaseDatabase.getInstance().getReference("/Usuario/").child(firebaseAuth.getUid()).child("citas");


        listaCitas = findViewById(R.id.listaCitasUsuario);

        txtCitaLugar = findViewById(R.id.txtMiscitasLugar);
        txtCitaFecha = findViewById(R.id.txtMiscitasMes);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MisCitas.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listaCitas.setLayoutManager(linearLayoutManager);

        usuarioCitas.orderByChild("fecha").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                objCita proximaCita = dataSnapshot.getValue(objCita.class);
                txtCitaLugar.setText(proximaCita.getLugar());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                objCita proximaCita = dataSnapshot.getValue(objCita.class);
                txtCitaLugar.setText(proximaCita.getLugar());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        adapterListaCitas=new FirebaseRecyclerAdapter<objCita, objCitaViewHolder.ViewHolder>(
                objCita.class,
                R.layout.obj_lista_cita,
                objCitaViewHolder.ViewHolder.class,
                usuarioCitas
        ) {
            @Override
            protected void populateViewHolder(final objCitaViewHolder.ViewHolder viewHolder,
                                              final objCita model, final int position) {
                viewHolder.lugar.setText(model.getLugar());
                viewHolder.fecha.setText(model.getFecha());
                viewHolder.hora.setText(model.getHora());
                if(model.getEstado()==0){
                    viewHolder.estado.setImageResource(R.drawable.ic_cita_previo);
                }else if(model.getEstado()==1){
                    viewHolder.estado.setImageResource(R.drawable.ic_cita_proceso);
                }else if (model.getEstado()==2){
                    viewHolder.estado.setImageResource(R.drawable.ic_cita_completado);
                }else{
                    viewHolder.estado.setImageResource(R.drawable.ic_cita_cargando);
                }
                /*viewHolder.mapa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ubiEventoUM = model.getUbicacion();
                        Toast.makeText(view.getContext(), ubiEventoUM, Toast.LENGTH_SHORT).show();
                        mostrarMapa();
                    }
                });*/
                viewHolder.setItemLongClickListener(new ItemLongClickListener() {
                    @Override
                    public void onItemLongClick(View v, int pos) {
                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MisCitas.this);
                        dialogo1.setTitle("¡Aviso!");
                        dialogo1.setIcon(R.drawable.ic_alerta_notificacion);
                        dialogo1.setMessage("El evento que seleccionaste se eliminara");
                        dialogo1.setCancelable(false);
                        dialogo1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                Toast.makeText(MisCitas.this, "Evento eliminado", Toast.LENGTH_SHORT).show();
                                adapterListaCitas.getRef(position).removeValue();
                            }
                        });
                        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                Toast.makeText(MisCitas.this, "El evento no se elimino", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialogo1.show();

                    }
                });

            }
        };

        listaCitas.setAdapter(adapterListaCitas);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent siguiente = new Intent(MisCitas.this, MenuInicio.class);//vamos a la ventana de la confirmacion
        startActivity(siguiente);
        finish();
    }
}
