package com.benayah.app.trackmypet.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.benayah.app.trackmypet.FindHomeActivity;
import com.benayah.app.trackmypet.MainActivity;
import com.benayah.app.trackmypet.POJO.DrawerListItems;
import com.benayah.app.trackmypet.R;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by User on 13-07-2017.
 */

public class CoverFlowAdapter extends BaseAdapter {

    private LinkedList<DrawerListItems> data;
    private Context activity;

    public CoverFlowAdapter(Context context, LinkedList<DrawerListItems> objects) {
        this.activity = context;
        this.data = objects;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public DrawerListItems getItem(int position) {
       // Log.d("Position....",position+"");
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Log.d("Position 2",position+"");
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_flow_view, null, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.menuImage.setImageResource(data.get(position).getImageSource());
        viewHolder.menuName.setText(data.get(position).getName());

        convertView.setOnClickListener(onClickListener(position));

        return convertView;
    }

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Log.d("Position in onClick",position+"");
               /* final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.dialog_game_info);
                dialog.setCancelable(true); // dimiss when touching outside
                dialog.setTitle("DrawerListItems Details");

                TextView text = (TextView) dialog.findViewById(R.id.name);
                text.setText(getItem(position).getName());
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageResource(getItem(position).getImageSource());

                dialog.show();*/

               // new MainActivity().displayView(position);


            }
        };
    }


    private static class ViewHolder {
        private TextView menuName;
        private ImageView menuImage;

        public ViewHolder(View v) {
            menuImage = (ImageView) v.findViewById(R.id.image);
            menuName = (TextView) v.findViewById(R.id.name);
        }
    }
}
