package aat.com.usuario;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aat.com.R;
import aat.com.clases.ItemLongClickListener;

public class objCitaViewHolder {

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        CardView cardView;
        ImageView estado;
        TextView lugar,fecha,hora;

        ItemLongClickListener itemLongClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.cardview_cita);
            estado=(ImageView)itemView.findViewById(R.id.obj_cita_estado);
            lugar=(TextView) itemView.findViewById(R.id.obj_cita_lugar);
            fecha=(TextView) itemView.findViewById(R.id.obj_cita_fecha);
            hora=(TextView) itemView.findViewById(R.id.obj_cita_hora);

            itemView.setOnLongClickListener(this);
        }

        public void setItemLongClickListener(ItemLongClickListener ic)
        {
            this.itemLongClickListener=ic;
        }

        @Override
        public boolean onLongClick(View v) {
            this.itemLongClickListener.onItemLongClick(v,getLayoutPosition());
            return false;
        }
    }
}
