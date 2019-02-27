package aat.com.usuario;


import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import aat.com.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgendarCitaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mMapView;

    private DatabaseReference clientes;
    private FirebaseAuth firebaseAuth;

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
        clientes= FirebaseDatabase.getInstance().getReference().child("/Cliente/");
        Toast.makeText(view.getContext(), clientes.toString(), Toast.LENGTH_SHORT).show();

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

        mMapView = view.findViewById(R.id.mapaUbicacionEstablecimiento);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }


        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            view.getContext(), R.raw.mapstyle));

            if (!success) {
                Log.e("ubicacion", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("ubicacion", "Can't find style. Error: ", e);
        }

        // Controles UI
        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);


    }

}
