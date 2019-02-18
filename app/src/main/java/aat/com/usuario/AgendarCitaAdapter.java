package aat.com.usuario;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AgendarCitaAdapter extends FragmentPagerAdapter {
    public AgendarCitaAdapter(FragmentManager fm) {
        super(fm);
    }

    private String nombre;

    @Override
    public Fragment getItem(int position) {
        AgendarCitaFragment menuFragment = new AgendarCitaFragment();
        position = position+1;
        Bundle bundle = new Bundle();
        bundle.putString("mensaje","Fragment: "+position);
        bundle.putInt("id",position);
        menuFragment.setArguments(bundle);

        return menuFragment;
    }

    @Override
    public int getCount() {
        return 4;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        position = position+1;
        if (position==1){
            //nombre = "Lugar";
        }else if (position==2){
            //nombre = "Servicio";
        }else if (position==3){
            //nombre = "Hora";
        }else if (position==4){
            //nombre = "Dia";
        }
        return nombre;
    }
}
