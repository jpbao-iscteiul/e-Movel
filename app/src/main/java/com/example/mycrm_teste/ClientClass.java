package com.example.mycrm_teste;

import android.os.Parcel;
import android.os.Parcelable;

public class ClientClass implements Parcelable, Comparable <ClientClass>{

    private String id;
    private int fav;
    private String email;
    private String name;
    private String phone;
    private String obs;


    public ClientClass(){

    }

    public ClientClass(String id, int fav, String email, String name, String phone, String obs) {

        this.id=id;
        this.fav = fav;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.obs = obs;
    }

    protected ClientClass(Parcel in) {
        id = in.readString();
        fav = in.readInt();
        email = in.readString();
        name = in.readString();
        phone = in.readString();
        obs = in.readString();
    }

    public static final Creator<ClientClass> CREATOR = new Creator<ClientClass>() {
        @Override
        public ClientClass createFromParcel(Parcel in) {
            return new ClientClass(in);
        }

        @Override
        public ClientClass[] newArray(int size) {
            return new ClientClass[size];
        }
    };

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(fav);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(obs);
    }

    @Override
    public int compareTo(ClientClass o) {
        return o.getFav() - this.getFav();
    }
}
