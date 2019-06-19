package com.cossu.myapplication.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.cossu.myapplication.R;
import com.cossu.myapplication.Recette;
import com.cossu.myapplication.activitys.ListeRecettesActivity;
import com.squareup.picasso.Picasso;
import java.util.List;
import static android.content.Context.MODE_PRIVATE;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {

    private LayoutInflater inflater;
    private List<Recette> maListeDeRecettes;
    Context mContext;
    ListeRecettesActivity listener;


    public RecyclerViewAdapter(Context ctx, ListeRecettesActivity listener, List<Recette> rogerModelArrayList){
        this.mContext = ctx;
        inflater = LayoutInflater.from(ctx);
        this.maListeDeRecettes = rogerModelArrayList;
        this.listener = listener;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_recettes_cell, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);


        Picasso.with(mContext).
                load(String.valueOf(maListeDeRecettes.get(position).getPhoto())).
                into(holder.photo);


        holder.title.setText(String.valueOf(maListeDeRecettes.get(position).getTitle()));

        if(maListeDeRecettes.get(position).getNote() >= 1) {
            holder.imageNote0.setImageResource(R.drawable.start_on);
        }
        if(maListeDeRecettes.get(position).getNote() >= 2) {
            holder.imageNote1.setImageResource(R.drawable.start_on);
        }
        if(maListeDeRecettes.get(position).getNote() >= 3) {
            holder.imageNote2.setImageResource(R.drawable.start_on);
        }
        if(maListeDeRecettes.get(position).getNote() >= 4) {
            holder.imageNote3.setImageResource(R.drawable.start_on);
        }
        if(maListeDeRecettes.get(position).getNote() >= 5) {
            holder.imageNote4.setImageResource(R.drawable.start_on);
        }

        SharedPreferences preferences = mContext.getSharedPreferences("APP", MODE_PRIVATE);
        String sharedFavoris = preferences.getString("favoris", null);

        holder.coeurFav.setImageResource(R.drawable.likeoff);
        if(sharedFavoris != null) {
            String[] splitArray = sharedFavoris.split(",");
            for(int i = 0; i< splitArray.length; i++){
                if(splitArray[i].equals(String.valueOf(maListeDeRecettes.get(position).getId()))) {
                    holder.coeurFav.setImageResource(R.drawable.like);
                }
            }
        }



    }



    @Override
    public int getItemCount() {
        return maListeDeRecettes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView photo;
        ImageView imageNote0;
        ImageView imageNote1;
        ImageView imageNote2;
        ImageView imageNote3;
        ImageView imageNote4;
        ImageView coeurFav;


        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.recettesName);
            photo = itemView.findViewById(R.id.recettesPhoto);
            imageNote0 = itemView.findViewById(R.id.recetteNote0);
            imageNote1 = itemView.findViewById(R.id.recetteNote1);
            imageNote2 = itemView.findViewById(R.id.recetteNote2);
            imageNote3 = itemView.findViewById(R.id.recetteNote3);
            imageNote4 = itemView.findViewById(R.id.recetteNote4);
            coeurFav = itemView.findViewById(R.id.coeurFavListe);
        }

    }

    @Override
    public void onClick(View v) {
        listener.onClickCell(this.maListeDeRecettes.get((int)v.getTag()), (int)v.getTag());
    }

}