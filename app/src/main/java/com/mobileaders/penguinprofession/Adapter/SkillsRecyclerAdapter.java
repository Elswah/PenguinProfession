package com.mobileaders.penguinprofession.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobileaders.penguinprofession.R;
import com.mobileaders.penguinprofession.Utilities.Fonts;

/**
 * Created by Mohamed Hefny on 18/12/2016.
 */

public class SkillsRecyclerAdapter extends RecyclerView.Adapter<SkillsRecyclerAdapter.SkillsItemHolder> {

    String skills[];
    Context ctx;
    Typeface typeface;

    public SkillsRecyclerAdapter(String skills[],Context context){
        this.skills = skills;
        this.ctx=context;
        typeface=Typeface.createFromAsset(context.getAssets(),"droidsans.ttf");
    }

    @Override
    public SkillsItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View skillItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.chip_item, parent, false);
        SkillsItemHolder skillsItemHolder = new SkillsItemHolder(skillItem);
        return skillsItemHolder;
    }

    @Override
    public void onBindViewHolder(SkillsItemHolder holder, int position) {
        String skill = skills[position];
        holder.SkillTextView.setText(skill);
    }

    @Override
    public int getItemCount() {
        return skills.length;
    }

    public class SkillsItemHolder extends RecyclerView.ViewHolder{
        TextView SkillTextView;

        public SkillsItemHolder(View view){
            super(view);
            SkillTextView = (TextView) view.findViewById(R.id.skill_item);
            SkillTextView.setTypeface(typeface);

        }
    }
}
