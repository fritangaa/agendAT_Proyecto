package aat.com.usuario;


import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import aat.com.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgendarCitaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mMapView;

    private FirebaseAuth firebaseAuth;

    private View view;

    private Integer posicion;

    private String citaLugar;
    private String citaServicio;
    private String citaPersonal;
    private String citaHora;
    private String citaDia;


    //----------------------------------------------------------
    private DatabaseReference clientes;

    private RelativeLayout agendaLugar;

    private SearchView buscaCliente;

    private CardView clienteInformacion;
    private TextView clienteNom;
    private TextView clienteVal;
    private TextView clienteDir;
    private ImageView clienteImg;
    //--------------------------------------------------------------

    private DatabaseReference servicios;

    private RelativeLayout agendaServicio;
    private FirebaseRecyclerAdapter<objServicio,objServicioViewHolder.ViewHolder> adapterListaServicios;
    private RecyclerView listaServicios;

    //---------------------------------------------------------------------
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

//---------------------------------------------------------------------------------------
        agendaLugar = (RelativeLayout) view.findViewById(R.id.relativeAgendaLugar);

        buscaCliente = view.findViewById(R.id.shEstablecimientoUsuario);

        clienteInformacion = view.findViewById(R.id.infoEstablecimientoUsuario );
        clienteNom = view.findViewById(R.id.obj_establecimiento_nombre);
        clienteVal = view.findViewById(R.id.obj_establecimiento_valoracion);
        clienteDir = view.findViewById(R.id.obj_establecimiento_direccion);
        clienteImg = view.findViewById(R.id.obj_establecimiento_imagen);

//----------------------------------------------------------------------------------------

        agendaServicio = (RelativeLayout) view.findViewById(R.id.relativeAgendaServicio);

        listaServicios = view.findViewById(R.id.listaServiciosCliente);

//----------------------------------------------------------------------------------------

        agendaDia = (RelativeLayout) view.findViewById(R.id.relativeAgendaDia);
        agendaHora = (RelativeLayout) view.findViewById(R.id.relativeAgendaHora);

//--------------------------------------------------------------------------------------
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
//---------------------------------------------------------------------------------------
        mMapView = view.findViewById(R.id.mapaUbicacionEstablecimiento);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

        clienteInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clienteInformacion.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGrisClaro));
                citaLugar="-LZ7qvRwVs31M-cE2G8D";
            }
        });


        buscaCliente.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String lugar = buscaCliente.getQuery().toString();

                Toast.makeText(view.getContext(), "buscando "+lugar, Toast.LENGTH_SHORT).show();

                buscaCliente.setQuery("", false);
                buscaCliente.setIconified(true);

                Geocoder geocoder = new Geocoder(view.getContext());
                List<Address> addresses = null;

                clientes.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            objCliente cliente = dataSnapshot.getValue(objCliente.class);
                            //Obtenemos los valores que queres
                            String oCliente = cliente.getNombre();

                            Toast.makeText(view.getContext(), "nombre " + oCliente, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });

                /*try {
                    // Getting a maximum of 3 Address that matches the input
                    // text
                    addresses = geocoder.getFromLocationName(lugar, 3);
                    if (addresses != null && !addresses.equals(""))
                        search(addresses);

                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });

        clientes= FirebaseDatabase.getInstance().getReference().child("/Cliente/-LZ7qvRwVs31M-cE2G8D/datos");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                objCliente cliente = dataSnapshot.getValue(objCliente.class);

                clienteNom.setText(cliente.getNombre());
                clienteVal.setText(cliente.getValoracion());
                clienteDir.setText(cliente.getDireccion());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        clientes.addValueEventListener(postListener);

//--------------------------------------------------------------------------------

        servicios= FirebaseDatabase.getInstance().getReference().child("/Cliente/-LZ7qvRwVs31M-cE2G8D/servicios");

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        listaServicios.setLayoutManager(linearLayoutManager);


        adapterListaServicios=new FirebaseRecyclerAdapter<objServicio, objServicioViewHolder.ViewHolder>(
                objServicio.class,
                R.layout.obj_lista_servicio,
                objServicioViewHolder.ViewHolder.class,
                servicios
        ) {
            @Override
            protected void populateViewHolder(final objServicioViewHolder.ViewHolder viewHolder,
                                              final objServicio model, final int position) {
                viewHolder.servicio.setText(model.getServicio());
                viewHolder.tiempo.setText(String.valueOf(model.getTiempo())+" min");
                viewHolder.costo.setText("$"+String.valueOf(model.getCosto()));
                if (model.getServicio().toString().equals("Corte de cabello")){
                    viewHolder.imagen.setImageResource(R.drawable.ic_servicio_corte);
                }else if (model.getServicio().toString().equals("Barba")){
                    viewHolder.imagen.setImageResource(R.drawable.ic_servicio_barba);
                }else if (model.getServicio().toString().equals("Uñas")){
                    viewHolder.imagen.setImageResource(R.drawable.ic_servicio_una);
                }
                viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewHolder.cardView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGrisClaro));
                        citaServicio=model.getServicio().toString();
                        Toast.makeText(view.getContext(), "nombre " + citaServicio, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };

        listaServicios.setAdapter(adapterListaServicios);

//--------------------------------------------------------------------------------


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

        LatLng ediTics = new LatLng(19.257010, -99.579551);
        mMap.setMyLocationEnabled(true);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(ediTics).zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }

        protected void search(List<Address> addresses) {

            Address address = (Address) addresses.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

            String addressText = String.format("%s, %s", address.getMaxAddressLineIndex() > 0 ? address
                            .getAddressLine(0) : "", address.getCountryName());

            MarkerOptions markerOptions = new MarkerOptions();

            markerOptions.position(latLng);
            markerOptions.title(addressText);

            mMap.clear();
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


        }



}
