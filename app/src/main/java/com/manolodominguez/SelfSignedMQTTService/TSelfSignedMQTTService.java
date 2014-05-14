/*
*                                  The MIT License (MIT)
*
* Copyright (c) 2014 - Manuel Domínguez Dorado <ingeniero@ManoloDominguez.com>
*
* Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
* associated documentation files (the "Software"), to deal in the Software without restriction,
* including without limitation the rights to use, copy, modify, merge, publish, distribute,
* sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
*     The above copyright notice and this permission notice shall be included in all copies or
*     substantial portions of the Software.
*
*     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
*     BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
*     NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
*     DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
*     OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.manolodominguez.SelfSignedMQTTService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * This class implements a service that will be started and destroyed by an Activity and that will
 * connect to a remote MQTT broker using TLS. It will provide the Activity with publishing services
 * as well as push notifications reception.
 *
 * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
 * @version 1.0
 */
public class TSelfSignedMQTTService extends Service {

    private TSelfSignedSSLSocketFactory selfSignedSSLSocketFactory;
    private MqttClient tslMQTTClient;
    private MqttConnectOptions mqttConnectOptions;

    /**
     * This is the constructor of the class. It does nothing because the service is started by an
     * Activity calling startService() or bindService() and using Intents.
     *
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    public TSelfSignedMQTTService() {
    }

    /**
     * This method is called when an Activity bind this service by calling its bindService() method.
     * According to http://developer.android.com/guide/components/services.html, a service can be
     * started (id started via Activity's startService() method) or bound (if started via Activity's
     * bindService() method). If this is a bound service, this is the starting point of the service
     * after its creation.
     *
     * @param intent The intent created by the Activity that is binding this TSelfSignedMQTTService
     *               and used to transport information needed to bind the service correctly.
     * @return IBinder An object that will allow bidirectional communication between an Activity and
     * this TSelfSignedMQTTService.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * This method is called when an Activity unbind this service by calling its unbindService()
     * method. According to http://developer.android.com/guide/components/services.html, an bound
     * service can be unbound in this way.
     *
     * @param intent The intent created by the Activity that is unbinding this
     *               TSelfSignedMQTTService and used to transport information needed to unbind the
     *               service correctly.
     * @return boolean TRUE, if we want onRebind() method to be called if an Activity calls its
     * bindService() after the service has been previously unbound. This implementation always
     * return TRUE.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    @Override
    public boolean onUnbind(Intent intent) {
        super.onUnbind(intent);
        return true;
    }

    /**
     * This method is called when an Activity bind this service by calling its bindService() method
     * and the service was previously unbound.
     * According to http://developer.android.com/guide/components/services.html, a service can be
     * started (id started via Activity's startService() method) or bound (if started via Activity's
     * bindService() method). If this is a bound service, this is the starting point of the service
     * after it was previously unbound.
     *
     * @param intent The intent created by the Activity that is binding this TSelfSignedMQTTService
     *               again and used to transport information needed to bind the service correctly.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    /**
     * This method is called when an Activity bind this service by calling its bindService() method
     * and the service was previously unbound.
     * According to http://developer.android.com/guide/components/services.html, a service can be
     * started (id started via Activity's startService() method) or bound (if started via Activity's
     * bindService() method). If this is a bound service, this is the starting point of the service
     * after it was previously unbound.
     *
     * @param intent The intent created by the Activity that is binding this TSelfSignedMQTTService
     *               again and used to transport information needed to bind the service correctly.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            this.selfSignedSSLSocketFactory = new TSelfSignedSSLSocketFactory(this.getResources());
            Log.i("[TSelfSignedMQTTService]", "selfSignedSSLSocketFactory was created.");
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when an Activity bind this service by calling its bindService() method
     * and the service was previously unbound.
     * According to http://developer.android.com/guide/components/services.html, a service can be
     * started (id started via Activity's startService() method) or bound (if started via Activity's
     * bindService() method). If this is a bound service, this is the starting point of the service
     * after it was previously unbound.
     *
     * @param intent The intent created by the Activity that is binding this TSelfSignedMQTTService
     *               again and used to transport information needed to bind the service correctly.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            this.tslMQTTClient = new MqttClient(this.selfSignedSSLSocketFactory.getAssociatedMQTTBrokerURL(), "consumerId", null);
            this.tslMQTTClient.setCallback(new TSelfSignedMQTTServiceCallback());
            this.mqttConnectOptions = new MqttConnectOptions();
            this.mqttConnectOptions.setConnectionTimeout(60);
            this.mqttConnectOptions.setKeepAliveInterval(60);
            this.mqttConnectOptions.setSocketFactory(this.selfSignedSSLSocketFactory.getSelfSignedSSLSocketFactory());
            this.tslMQTTClient.connect(this.mqttConnectOptions);
            this.tslMQTTClient.subscribe("lolete/hora");
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        try {
            tslMQTTClient.disconnect();
            Log.i("[TSelfSignedMQTTService]", "Connection to remote MQTT broker closed.");
        } catch (MqttException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }


}
