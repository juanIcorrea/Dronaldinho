package com.parrot.sdksample.socket;

import android.app.Activity;
import android.content.Intent;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_DEVICE_STATE_ENUM;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceService;
import com.parrot.sdksample.R;
import com.parrot.sdksample.activity.DeviceListActivity;
import com.parrot.sdksample.drone.MiniDrone;

import java.net.InetSocketAddress;

/**
 * Created by Juan on 10/27/2016.
 */
public class SocketServer extends WebSocketServer {
    private MiniDrone mMiniDrone;
    private static final String TAG = "SocketServer";

    private WebSocket mSocket;

    public SocketServer(InetSocketAddress address, MiniDrone drone) {
        super(address);
        mMiniDrone = drone;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        mSocket = conn;
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

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
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    public void sendMessage(String message) {
        mSocket.send(message);
    }
}
