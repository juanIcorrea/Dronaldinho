package com.parrot.sdksample.socket;

import android.os.Handler;

import com.parrot.sdksample.drone.MiniDrone;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * Created by Juan on 10/27/2016.
 */
public class SocketServer extends WebSocketServer {
    private MiniDrone mMiniDrone;
    private static final String TAG = "SocketServer";
    private Handler handler;

    private WebSocket mSocket;
    /*
    Se crea un SocketServer con la dirección IP y puerto que recibe. Tambien recibe una instancia de MiniDrone para controlarlo.
     */

    public SocketServer(InetSocketAddress address, MiniDrone drone) {
        super(address);
        mMiniDrone = drone;
        handler = new Handler();
    }

    /*
    Se guarda la instancia de WebSocket en una variable de clase.
     */
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        mSocket = conn;
    }


    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }
/*
Recibe un WebSocket y un mensaje. El mensaje es el comando a ejecutar en el drone.
 */

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Incoming message: " + message);
        switch (message){
            case("takeoff"):
                switch (mMiniDrone.getFlyingState()) {
                    case ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_LANDED:
                        mMiniDrone.takeOff();
                        break;
                    case ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_FLYING:
                    case ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_HOVERING:
                        mMiniDrone.land();
                        break;
                    default:
                }
                break;
            case("down"):
                mMiniDrone.setGaz((byte) -50);
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        mMiniDrone.setGaz((byte) 0);
//                    }
//                }, 500);
                break;
            case("up"):
                mMiniDrone.setGaz((byte) 50);
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        mMiniDrone.setGaz((byte) 0);
//                    }
//                }, 500);
                break;
            case("left"):
                mMiniDrone.setYaw((byte) -50);
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        mMiniDrone.setYaw((byte) 0);
//                    }
//                }, 500);
                break;
            case("right"):
                mMiniDrone.setYaw((byte) 50);
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        mMiniDrone.setYaw((byte) 0);
//                    }
//                }, 500);
                break;
            case("forward"):
                mMiniDrone.setPitch((byte) 50);
                mMiniDrone.setFlag((byte) 1);
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        mMiniDrone.setPitch((byte) 0);
//                        mMiniDrone.setFlag((byte)0);
//                    }
//                }, 1000);
                break;
            case("backward"):
                mMiniDrone.setPitch((byte) -50);
                mMiniDrone.setFlag((byte) 1);
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        mMiniDrone.setPitch((byte) 0);
//                        mMiniDrone.setFlag((byte)0);
//                    }
//                }, 1000);
                break;
            case("rollLeft"):
                mMiniDrone.setRoll((byte) -50);
                mMiniDrone.setFlag((byte) 1);
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        mMiniDrone.setRoll((byte) 0);
//                        mMiniDrone.setFlag((byte)0);
//                    }
//                }, 500);
                break;
            case("rollRight"):
                mMiniDrone.setRoll((byte) 50);
                mMiniDrone.setFlag((byte) 1);
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        mMiniDrone.setRoll((byte) 0);
//                        mMiniDrone.setFlag((byte)0);
//                    }
//                }, 500);
                break;
            case ("backFlip"):
                break;
            case("middle"):
                mMiniDrone.setGaz((byte) 0);
                mMiniDrone.setYaw((byte) 0);
                mMiniDrone.setRoll((byte) 0);
                mMiniDrone.setFlag((byte) 0);
                mMiniDrone.setPitch((byte) 0);
                break;
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

}
