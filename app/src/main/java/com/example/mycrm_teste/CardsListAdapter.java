package com.example.mycrm_teste;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class CardsListAdapter extends RecyclerView.Adapter <CardsListAdapter.ExampleViewHolder> implements Filterable {

    private List <TemplateContactCard> contactList;
    private List <TemplateContactCard> copyContactList;
    private List <String> mKeys;
    private OnItemClickListener mListener;




    public interface OnItemClickListener {
        void onItemClick (int position);
        void onCallClick(int position);
        void onFavPressedClick(int position);
        void onFavClick(int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener){
        this.mListener = listener;

    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        private ImageView contactPhoto;
        private TextView contactName;
        private TextView eMail;

        private ImageView mCallImage;

        private ImageView mNotifications;

        private ImageView mFavorites;
        private ImageView mFavPressed;


        private Context mContext;
        private String key;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            contactPhoto = itemView.findViewById(R.id.contactPhoto);
            contactName = itemView.findViewById(R.id.name);
            eMail = itemView.findViewById(R.id.contactInfo);
            mCallImage = itemView.findViewById(R.id.call_back_image);
            mNotifications = itemView.findViewById(R.id.notifications);
            mFavorites = itemView.findViewById(R.id.favorite);
            mFavPressed = itemView.findViewById(R.id.favorite_checked);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            listener.onItemClick(pos);
                        }
                    }
                    /*Intent intent = new Intent (mContext , ClientDetailsActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("name", contactName.getText().toString());
                    mContext.startActivity(intent); */
                }
            });

           mCallImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            listener.onCallClick(pos);
                        }
                    }
                }
            });

           mFavorites.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (listener != null){
                       int pos = getAdapterPosition();
                       if (pos != RecyclerView.NO_POSITION){
                           if(mFavorites.getVisibility() == View.VISIBLE && mFavPressed.getVisibility() == View.INVISIBLE){
                               mFavorites.setVisibility(View.INVISIBLE);
                               mFavPressed.setVisibility(View.VISIBLE);
                               listener.onFavClick(pos);
                           }
                       }
                   }
               }
           });

           mFavPressed.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (listener != null) {
                       int pos = getAdapterPosition();
                       if (mFavPressed.getVisibility() == View.VISIBLE && mFavorites.getVisibility() == View.INVISIBLE) {
                           mFavPressed.setVisibility(View.INVISIBLE);
                           mFavorites.setVisibility(View.VISIBLE);
                           listener.onFavPressedClick(pos);
                       }
                   }
               }
           });
        }
    }


    public CardsListAdapter (List<TemplateContactCard> contactList, List<String> mKeys){
        this.contactList = contactList;
        this.copyContactList = new ArrayList<>(contactList);
        this.mKeys = mKeys;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_contact_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        TemplateContactCard currentItem = contactList.get(position);

        holder.contactPhoto.setImageResource(currentItem.getmImageResource());

        holder.eMail.setText(currentItem.getCliente().getEmail());
        holder.contactName.setText(currentItem.getCliente().getName());


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List <TemplateContactCard> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length()==0){
                filteredList.addAll(copyContactList);
            } else {
                String filteredPattern = constraint.toString().toLowerCase().trim();
                for (TemplateContactCard card :  copyContactList){
                    if (card.getCliente().getName().trim().contains(filteredPattern)){
                        filteredList.add(card);

                    }
                }
            }
            FilterResults results = new FilterResults ();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactList.clear();
            contactList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
