package aat.com.usuario;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aat.com.R;
import aat.com.clases.ItemLongClickListener;

public class objClienteViewHolder {

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        CardView cardViewCliente;
        ImageView imagenCliente;
        TextView nombre,valoracion,direccion;

        ItemLongClickListener itemLongClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            cardViewCliente=(CardView)itemView.findViewById(R.id.cardview_cliente);
            imagenCliente=(ImageView)itemView.findViewById(R.id.obj_establecimiento_imagen);
            nombre=(TextView) itemView.findViewById(R.id.obj_establecimiento_nombre);
            valoracion=(TextView) itemView.findViewById(R.id.obj_establecimiento_valoracion);
            direccion=(TextView) itemView.findViewById(R.id.obj_establecimiento_direccion);


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
