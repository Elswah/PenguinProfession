package com.mobileaders.penguinprofession.Module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abdelmageed on 29/09/2016.
 */
public class ModelHome implements Parcelable {

    private String logo;
    private int tag;
    private int location;
    private int work;
    private String title;
    private String description;
    private String txtTag;
    private String txtLocation;
    private String txtWork;
    private String jobTimeType;
    private String jobSalary;
    private  String jobDetials;
    private String jobID;
    public ModelHome(){

    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getWork() {
        return work;
    }

    public void setWork(int work) {
        this.work = work;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTxtTag() {
        return txtTag;
    }

    public void setTxtTag(String txtTag) {
        this.txtTag = txtTag;
    }

    public String getTxtLocation() {
        return txtLocation;
    }

    public void setTxtLocation(String txtLocation) {
        this.txtLocation = txtLocation;
    }

    public String getTxtWork() {
        return txtWork;
    }

    public void setTxtWork(String txtWork) {
        this.txtWork = txtWork;
    }

    public String getJobTimeType() {
        return jobTimeType;
    }

    public void setJobTimeType(String jobTimeType) {
        this.jobTimeType = jobTimeType;
    }

    public String getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String getJobDetials() {
        return jobDetials;
    }

    public void setJobDetials(String jobDetials) {
        this.jobDetials = jobDetials;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    protected ModelHome(Parcel in) {
        logo = in.readString();
        tag = in.readInt();
        location = in.readInt();
        work = in.readInt();
        title = in.readString();
        description = in.readString();
        txtTag = in.readString();
        txtLocation = in.readString();
        txtWork = in.readString();
        jobTimeType = in.readString();
        jobSalary = in.readString();
        jobDetials = in.readString();
        jobID = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(logo);
        dest.writeInt(tag);
        dest.writeInt(location);
        dest.writeInt(work);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(txtTag);
        dest.writeString(txtLocation);
        dest.writeString(txtWork);
        dest.writeString(jobTimeType);
        dest.writeString(jobSalary);
        dest.writeString(jobDetials);
        dest.writeString(jobID);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ModelHome> CREATOR = new Parcelable.Creator<ModelHome>() {
        @Override
        public ModelHome createFromParcel(Parcel in) {
            return new ModelHome(in);
        }

        @Override
        public ModelHome[] newArray(int size) {
            return new ModelHome[size];
        }
    };
}