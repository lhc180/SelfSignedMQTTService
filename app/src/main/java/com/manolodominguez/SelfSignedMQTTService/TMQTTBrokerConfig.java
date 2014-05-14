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

/**
 * This class stores the configuration of a remote MQTT broker that listen TLS an has a self signed
 * server certificate or uses its own CA to sign it.
 *
 * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
 * @version 1.0
 */
public class TMQTTBrokerConfig {

    // These are the default values for the constructor of the class. If not specified other, these
    // values will be used as valid configuration to connect to the remote MQTT broker.
    // ********************************************************************************
    // Change this constants as desired to fit your needs.
    // ********************************************************************************
    private static final int MQTT_BROKER_CONFIG_TLSPORT = 8883;
    private static final String MQTT_BROKER_CONFIG_BROKER_ADDRESS = "test.mosquitto.org";
    private static final String MQTT_BROKER_CONFIG_PROTOCOL = "TLS";
    private static final int MQTT_BROKER_CONFIG_CA_CERT_RESID = R.raw.untrusted_ca;
    // ********************************************************************************

    private int tlsPort;
    private String address;
    private String protocol;
    private int brokerCACertificateFileResourceID;

    /**
     * This method is the constructor of the class. It creates a new instance of TMQTTBrokerConfig
     * and initiates the values of all class attributes with values specified in
     * MQTT_BROKER_CONFIG_* constants (defined in this class).
     *
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    public TMQTTBrokerConfig() {
        this.tlsPort = this.MQTT_BROKER_CONFIG_TLSPORT;
        this.address = this.MQTT_BROKER_CONFIG_BROKER_ADDRESS;
        this.protocol = this.MQTT_BROKER_CONFIG_PROTOCOL;
        this.brokerCACertificateFileResourceID = this.MQTT_BROKER_CONFIG_CA_CERT_RESID;
    }

    /**
     * This method returns the port of the remote MQTT broker that is listening to TLS requests.
     *
     * @return int. The port of the remote MQTT broker that is listening to TLS requests.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    public int getTlsPort() {
        return tlsPort;
    }

    /**
     * This method sets the port of the remote MQTT broker that is listening to TLS requests.
     *
     * @param tlsPort The port of the remote MQTT broker that is listening to TLS requests.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    public void setTlsPort(int tlsPort) {
        this.tlsPort = tlsPort;
    }

    /**
     * This method returns the complete name or IP address of the remote MQTT broker that is
     * listening to TLS requests.
     *
     * @return String. The complete name or IP address of the remote MQTT broker that is
     * listening to TLS requests.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * This method sets the complete name or IP address of the remote MQTT broker that is
     * listening to TLS requests.
     *
     * @param address The complete name or IP address of the remote MQTT broker that is
     *                listening to TLS requests.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method returns the protocol of the remote MQTT broker that is listening to TLS requests.
     * It usually is "TLS", "TLSv1.1", etc.
     *
     * @return String. The protocol of the remote MQTT broker that is listening to TLS requests.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    public String getProtocol() {
        return this.protocol;
    }

    /**
     * This method sets the protocol of the remote MQTT broker that is listening to TLS requests.
     * It usually is "TLS", "TLSv1.1", etc.
     *
     * @param protocol The protocol of the remote MQTT broker that is listening to TLS requests.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * This method returns the resource id of a file containing the certificate of the untrusted
     * CA that signed the server certificate of the remote MQTT broker (that is used to listen to
     * TLS request at this broker).
     *
     * @return int. the resource id of a file containing the certificate of the untrusted CA that
     * signed the server certificate of the remote MQTT broker.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    public int getBrokerCACertificateFileResourceID() {
        return this.brokerCACertificateFileResourceID;
    }

    /**
     * This method sets the resource id of a file containing the certificate of the untrusted CA
     * that signed the server certificate of the remote MQTT broker (that is used to listen to TLS
     * request at this broker).
     *
     * @param brokerCACertificateFileResourceID The resource id of a file containing the certificate
     *                                          of the untrusted CA that signed the server
     *                                          certificate of the remote MQTT broker.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    public void setBrokerCACertificateFileResourceID(int brokerCACertificateFileResourceID) {
        this.brokerCACertificateFileResourceID = brokerCACertificateFileResourceID;
    }

    /**
     * This method returns the complete URL to connect to the MQTT broker using the values of the
     * class attributes. It will have the following aspect:
     * ssl://serveraddress:serverport, i.e. ssl://test.mosquitto.org:8883
     *
     * @return String. The URL to connect to the MQTT broker.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    public String getMQTTBrokerURL() {
        return "ssl://" + this.address + ":" + Integer.toString(this.tlsPort);
    }
}
