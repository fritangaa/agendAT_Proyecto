package aat.com.usuario;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import aat.com.R;
import aat.com.clases.ItemLongClickListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgendarCitaFragment extends Fragment {

    private DatabaseReference clientes;
    private FirebaseAuth firebaseAuth;

    private FirebaseRecyclerAdapter<objCliente,objClienteViewHolder.ViewHolder> adapterListaClientes;

    private RecyclerView listaClinetes;

    private View view;

    private Integer posicion;

    private RelativeLayout agendaLugar;
    private RelativeLayout agendaServicio;
    private RelativeLayout agendaDia;
    private RelativeLayout agendaHora;


    public AgendarCitaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_agendar_cita, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        clientes= FirebaseDatabase.getInstance().getReference("/Cliente/");

        agendaLugar = (RelativeLayout) view.findViewById(R.id.relativeAgendaLugar);
        agendaServicio = (RelativeLayout) view.findViewById(R.id.relativeAgendaServicio);
        agendaDia = (RelativeLayout) view.findViewById(R.id.relativeAgendaDia);
        agendaHora = (RelativeLayout) view.findViewById(R.id.relativeAgendaHora);
        posicion = getArguments().getInt("id");

        if (posicion==1){
            agendaLugar.setVisibility(View.VISIBLE);
            agendaServicio.setVisibility(View.INVISIBLE);
            agendaDia.setVisibility(View.INVISIBLE);
            agendaHora.setVisibility(View.INVISIBLE);
        }else if (posicion==2){
            agendaLugar.setVisibility(View.INVISIBLE);
            agendaServicio.setVisibility(View.VISIBLE);
            agendaDia.setVisibility(View.INVISIBLE);
            agendaHora.setVisibility(View.INVISIBLE);
        }else if (posicion==3){
            agendaLugar.setVisibility(View.INVISIBLE);
            agendaServicio.setVisibility(View.INVISIBLE);
            agendaDia.setVisibility(View.VISIBLE);
            agendaHora.setVisibility(View.INVISIBLE);
        }else if (posicion==4){
            agendaLugar.setVisibility(View.INVISIBLE);
            agendaServicio.setVisibility(View.INVISIBLE);
            agendaDia.setVisibility(View.INVISIBLE);
            agendaHora.setVisibility(View.VISIBLE);
        }


        listaClinetes = view.findViewById(R.id.listaEstablecimientoUsuario);

        listaClinetes.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterListaClientes=new FirebaseRecyclerAdapter<objCliente, objClienteViewHolder.ViewHolder>(
                objCliente.class,
                R.layout.obj_lista_clientes,
                objClienteViewHolder.ViewHolder.class,
                clientes
        ) {
            @Override
            protected void populateViewHolder(final objClienteViewHolder.ViewHolder viewHolder,
                                              final objCliente model, final int position) {
                viewHolder.nombre.setText(model.getNombre());
                viewHolder.valoracion.setText(model.getValoracion());
                viewHolder.direccion.setText(model.getDireccion());

                viewHolder.setItemLongClickListener(new ItemLongClickListener() {
                    @Override
                    public void onItemLongClick(View v, int pos) {
                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(view.getContext());
                        dialogo1.setTitle("¡Aviso!");
                        dialogo1.setIcon(R.drawable.ic_alerta_notificacion);
                        dialogo1.setMessage("El evento que seleccionaste se eliminara");
                        dialogo1.setCancelable(false);
                        dialogo1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                Toast.makeText(view.getContext(), "Evento eliminado", Toast.LENGTH_SHORT).show();
                                adapterListaClientes.getRef(position).removeValue();
                            }
                        });
                        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                Toast.makeText(view.getContext(), "El evento no se elimino", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialogo1.show();

                    }
                });

            }
        };

        listaClinetes.setAdapter(adapterListaClientes);

        listaClinetes.setItemAnimator(new DefaultItemAnimator());



        return view;
    }

}
