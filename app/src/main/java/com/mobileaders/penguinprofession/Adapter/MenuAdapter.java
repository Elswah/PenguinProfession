package com.mobileaders.penguinprofession.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobileaders.penguinprofession.Module.MenuModel;
import com.mobileaders.penguinprofession.R;
import com.mobileaders.penguinprofession.Utilities.Fonts;

import java.util.ArrayList;

/**
 * Created by user on 10/11/2016.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    Context context;
    OnItemClickListener onItemClickListener;
    ArrayList<MenuModel> menuModelList;
    LayoutInflater inflater;


    public MenuAdapter(Context context, ArrayList<MenuModel> menuModelList) {
        this.context = context;
        this.menuModelList = menuModelList;
        inflater = LayoutInflater.from(context);
    }

    public interface OnItemClickListener{

        void onclick(int position);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.menu_single_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MenuModel menuModel = menuModelList.get(position);
        holder.menuImageItem.setImageResource(menuModel.getImage());
        holder.menuTextItem.setText(menuModel.getText());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null){
                    onItemClickListener.onclick(position);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return menuModelList.size();
    }

    public void setOnClickListener(OnItemClickListener onClickListener){
        this.onItemClickListener=onClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {

        ImageView menuImageItem;
        TextView menuTextItem;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            menuImageItem = (ImageView) itemView.findViewById(R.id.menu_image_item);
            menuTextItem = (TextView) itemView.findViewById(R.id.menu_text_item);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearRecycler);

            Fonts.set(new TextView[]{menuTextItem}, context, 1);

        }

    }



}
