package com.example.telas_background.item;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes_instanciadas.Classe_encontro_request;
import com.example.telas_background.R;
import com.example.telas_background.Request;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_encontro_request extends Item<ViewHolder> implements View.OnClickListener {

    private Integer positionRequest;
    private Item item;

    public Classe_encontro_request request;

    public Item_encontro_request(Classe_encontro_request request) {
        this.request = request;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        TextView nome = viewHolder.itemView.findViewById(R.id.nome_encontro_request);
         TextView dono = viewHolder.itemView.findViewById(R.id.dono_encontro_request);
         TextView localData1 = viewHolder.itemView.findViewById(R.id.local_encontro_request);
         TextView horario1 = viewHolder.itemView.findViewById(R.id.horario_encontro_request);
         ImageView imagem1 = viewHolder.itemView.findViewById(R.id.foto_encontro_request);

        positionRequest = position;
        item = viewHolder.getItem();

        nome.setText(request.getTitulo());
        dono.setText(request.getNome());
        Picasso.get().load(request.getImagem()).into(imagem1);
        localData1.setText(request.getLocal() + " | " + request.getData());
        horario1.setText(request.getHorario());

        viewHolder.itemView.findViewById(R.id.socializar_encontro_request).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.rejeitar_encontro_request).setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.socializar_encontro_request:
                Request.botaoItemEncontroRecycler(item, 0 ,positionRequest);
                break;
            case R.id.rejeitar_encontro_request:
                Request.botaoItemEncontroRecycler(item, 1 ,positionRequest);
                break;

            default:
                break;
        }
    }

    @Override
    public int getLayout() {
        return R.layout.item_encontro_request_add;
    }

}
