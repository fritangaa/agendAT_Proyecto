package aat.com.usuario;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import aat.com.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgendarCitaFragment extends Fragment {

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



        return view;
    }

}
