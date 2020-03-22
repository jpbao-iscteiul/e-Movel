package com.example.mycrm_teste;

public class TemplateContactCard {

    private int mImageResource;

   private ClientClass cliente;


    public TemplateContactCard (int mImageResource, ClientClass cliente){
        this.mImageResource = mImageResource;
        this.cliente = cliente;
    }

    public ClientClass getCliente() {
        return cliente;
    }

    public int getmImageResource() {
        return mImageResource;
    }
}
