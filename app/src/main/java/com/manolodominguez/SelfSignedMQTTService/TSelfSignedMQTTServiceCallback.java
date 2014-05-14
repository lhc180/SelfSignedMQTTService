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

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * This class implements a MqttCallback. Its methods are called by the service when something
 * important happens in the context of the MQTT connection.
 *
 * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
 * @version 1.0
 */
public class TSelfSignedMQTTServiceCallback implements MqttCallback {

    /**
     * This method is called when the TSelfSignedMQTTService instance detects that the connection to
     * the remote MQTT broker has been lost.
     *
     * @param throwable The reason behind the loss of connection.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    @Override
    public void connectionLost(Throwable throwable) {
        Log.i("[TSelfSignedMQTTServiceCallback]", "Connection lost.");
    }

    /**
     * This method is called when the TSelfSignedMQTTService instance detects that a new message has
     * been received from the remote MQTT broker.
     *
     * @param s           The topic at which the received message is associated.
     * @param mqttMessage The received message.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        Log.i("[TSelfSignedMQTTServiceCallback]", "Message arrived: " + mqttMessage.toString());
    }

    /**
     * This method is called when the TSelfSignedMQTTService instance detects that a new message has
     * been received from the remote MQTT broker.
     *
     * @param iMqttDeliveryToken The token associated to the publication of a message. It identifies
     *                           the message that was previously sent and, depending on the QoS
     *                           selected, is now completed.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        Log.i("[TSelfSignedMQTTServiceCallback]", "Message delivery identified by " + iMqttDeliveryToken.toString() + ", completed.");
    }
}