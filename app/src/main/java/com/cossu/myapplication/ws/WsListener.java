package com.cossu.myapplication.ws;

public interface WsListener {

    void errorRequest(int id);
    void successRequest(int id, String data);

}
