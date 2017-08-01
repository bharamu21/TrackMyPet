package com.benayah.app.trackmypet.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benayah.app.trackmypet.POJO.Place;
import com.benayah.app.trackmypet.R;


import java.util.ArrayList;

/**
 * Created by Benayah on 23/2/2017.
 */

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder>  {

    ArrayList<Place> listOfStores;
    Context context;
    //ContactsFragment contactsFragment;
    //public CardView mCardView;


    public StoreListAdapter(Context context,ArrayList<Place> listOfStores)
    {
        this.listOfStores = listOfStores;
        this.context = context;
        Log.v("storeList length",listOfStores.size()+"");
        //contactsFragment = new ContactsFragment();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_list_item_layout,parent,false);
        //view.setOnClickListener(ContactsFragment.onContactClick);
        //view.setOnLongClickListener(ContactsFragment.onContactClickListener);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        TextView contactRelationShipTextView = holder.storeName;
        TextView contactNameTextView = holder.storeAddress;
        TextView contactHomeNoTextView = holder.storeDistance;


        //holder.mCardView.setOnClickListener(this);


        contactRelationShipTextView.setText(listOfStores.get(pos).getPlaceName());
        contactNameTextView.setText(listOfStores.get(pos).getPlaceAddress());
        contactHomeNoTextView.setText(listOfStores.get(pos).getDistance());




    }

    @Override
    public int getItemCount() {
        return listOfStores.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        View v;
        TextView storeName;
        TextView storeAddress;
        TextView storeDistance;

        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            this.storeName = (TextView) itemView.findViewById(R.id.store_name);
            this.storeAddress = (TextView) itemView.findViewById(R.id.store_address);
            this.storeDistance = (TextView) itemView.findViewById(R.id.distance);

            mCardView = (CardView) itemView.findViewById(R.id.card_view);

           /* v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("position = ");
                    System.out.println("position = "+getAdapterPosition());
                    //return true;
                }
            });*/
        }



    }


}
