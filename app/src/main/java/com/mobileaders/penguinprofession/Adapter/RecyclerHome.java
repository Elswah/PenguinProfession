package com.mobileaders.penguinprofession.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mobileaders.penguinprofession.Activity.JobDetailsFragment;
import com.mobileaders.penguinprofession.AppController;
import com.mobileaders.penguinprofession.Module.ModelHome;
import com.mobileaders.penguinprofession.R;

import com.mobileaders.penguinprofession.Utilities.Fonts;
import com.mobileaders.penguinprofession.Utilities.URLS;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdelmageed on 29/09/2016.
 */
public class RecyclerHome extends RecyclerView.Adapter<RecyclerHome.MyHolder>  {

    List<ModelHome> list;
    Context context;
    private ClickeListener clickeListener;
    LayoutInflater inflater;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public RecyclerHome(Context context, List<ModelHome> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
        //----------------------------

    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.recyclehome,parent,false);
        MyHolder holder = new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final ModelHome model = list.get(position);
        holder.setData(model, position);
        holder.setListeners();
    }

    public void setClickeListener(ClickeListener clickeListener) {
        this.clickeListener = clickeListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title,description,txtLocation,txtTag,txtWork;
        LinearLayout rec_lin;
        ImageView tag,location,work,arraw;
        NetworkImageView logo;
        int position;
        ModelHome current;
        public MyHolder(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.jobTitleHome);
            description=(TextView) itemView.findViewById(R.id.descriptionHome);
            txtLocation=(TextView) itemView.findViewById(R.id.txtLocation);
            txtTag=(TextView) itemView.findViewById(R.id.txtJob);
            txtWork=(TextView) itemView.findViewById(R.id.txtwork);
            logo=(NetworkImageView) itemView.findViewById(R.id.logo);
            tag=(ImageView) itemView.findViewById(R.id.job);
            location=(ImageView) itemView.findViewById(R.id.imgLocation);
            work=(ImageView) itemView.findViewById(R.id.work);
            arraw=(ImageView) itemView.findViewById(R.id.arrawTo);
            rec_lin= (LinearLayout) itemView.findViewById(R.id.rec_lin);
            Fonts.set(new TextView[]{title},context,1);
            Fonts.set(new TextView[]{description,txtLocation,txtTag,txtWork},context,0);
            itemView.setOnClickListener(this);
        }
        public void setData(ModelHome current, int position) {
            this.title.setText(current.getTitle());
            this.description.setText(current.getDescription());
            this.location.setImageResource(current.getLocation());
            this.tag.setImageResource(current.getTag());
            this.work.setImageResource(current.getWork());
            this.title.setText(current.getTitle());
            this.description.setText(current.getDescription());
            this.txtWork.setText(current.getTxtWork());
            this.txtLocation.setText(current.getTxtLocation());
            this.txtTag.setText(current.getTxtTag());
            String url=URLS.imageUrl+current.getLogo();
            //Picasso.with(context).load(url).into(logo);
            logo.setImageUrl(url,imageLoader);
            this.position = position;
            this.current = current;
        }
        public void setListeners() {
            logo.setOnClickListener(MyHolder.this);
            arraw.setOnClickListener(MyHolder.this);
            rec_lin.setOnClickListener(MyHolder.this);

        }

        @Override
        public void onClick(View v) {
            if (clickeListener != null) {
                clickeListener.itemClick(v, getPosition());
            }
            switch (v.getId()){
                case R.id.logo:
//                    Toast.makeText(context, "logo", Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(context, JobDetailsFragment.class);
//                    Bundle bundle=new Bundle();
//                    ModelHome modelHome= list.get(position);
//                    bundle.putParcelable("Model_Key",modelHome);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
                    break;
                case R.id.arrawTo:
                    Toast.makeText(context, "arraw", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(context, JobDetailsFragment.class);
                    Bundle b=new Bundle();
                    ModelHome modelHome2= list.get(position);
                    b.putParcelable("Model_Key",modelHome2);
                    i.putExtras(b);
                    context.startActivity(i);
                    break;
                case R.id.rec_lin:
                    // send object to job detailed item
                    Toast.makeText(context, "Linear", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context, JobDetailsFragment.class);
                    Bundle bundle=new Bundle();
                    ModelHome modelHome= list.get(position);
                    bundle.putParcelable("Model_Key",modelHome);
                    intent.putExtras(bundle);
                    context.startActivity(intent);





            }
        }
    }

    public interface ClickeListener {
        public void itemClick(View view, int position);
    }


}
