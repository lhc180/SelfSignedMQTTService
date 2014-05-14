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

import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * This class implements a SSL socket factory that uses a TrustManagerFactory to trust the CA
 * certificate of a self-signed MQTT broker certificate. This allows creating a SSL/TLS socket to
 * that MQTT broker avoiding the problem of using an unreliable CA.
 *
 * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
 * @version 1.0
 */
public class TSelfSignedSSLSocketFactory {

    private TMQTTBrokerConfig mqttBrokerConfig;
    private CertificateFactory certificateFactory;
    private InputStream caInput;
    private X509Certificate untrustedCACertificate;
    private String keyStoreType;
    private KeyStore keyStore;
    private String trustManagerFactoryAlgorithm;
    private TrustManagerFactory trustManagerFactory;
    private SSLContext sslContext;

    /**
     * This is the constructor of the class. It creates a new instance of
     * TSelfSignedSSLSocketFactory and will do the necessary work to initiate attributes and create
     * a SSLSocketFactory as expected.
     *
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    public TSelfSignedSSLSocketFactory(Resources appResources) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        // Create an instance of TMQTTBrokerConfig that stores the current config of the MQTT broker
        // to be accessed via TLS using a self signed broker certificate.
        this.mqttBrokerConfig = new TMQTTBrokerConfig();
        // Load CA certificate file from an InputStream. For the context if this project, this
        // certificate correspond to the CA that signed the broker certificate. It can be a
        // created CA, a CA that usually are not reliable or a self-signed certificate.
        // Certificate should be stored in the res/raw folder of your Android project and configured
        // in TMQTTBrokerConfig class.
        this.certificateFactory = CertificateFactory.getInstance("X.509");
        this.caInput = new BufferedInputStream(appResources.openRawResource(this.mqttBrokerConfig.getBrokerCACertificateFileResourceID()));
        // A X509 certificate is created from the information stored in the aforementioned file.
        Log.i("[TSelfSignedSSLSocketFactory]", "The untrusted CA certificate has been opened");
        this.untrustedCACertificate = (X509Certificate) this.certificateFactory.generateCertificate(this.caInput);
        Log.i("[TSelfSignedSSLSocketFactory]", "X509 certificate has been created from CA certificate file");
        this.caInput.close();
        // Create a KeyStore containing the desired CA. This CA will be trusted, but at this moment
        // it is not.
        this.keyStoreType = KeyStore.getDefaultType();
        this.keyStore = KeyStore.getInstance(this.keyStoreType);
        this.keyStore.load(null, null);
        this.keyStore.setCertificateEntry("ca", this.untrustedCACertificate);
        Log.i("[TSelfSignedSSLSocketFactory]", "Added the untrusted CA certificate to the keystore");
        // Create a TrustManager that will trust the CA in our KeyStore. Hence, the CA that has
        // signed the remote MQTT broker certificate will be reliable.
        this.trustManagerFactoryAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        this.trustManagerFactory = TrustManagerFactory.getInstance(this.trustManagerFactoryAlgorithm);
        this.trustManagerFactory.init(this.keyStore);
        Log.i("[TSelfSignedSSLSocketFactory]", "Created the TrustManagerFactory that will trust the untrusted CA certificate");
        // Create an SSLContext that uses our TrustManager to generate SSLSocket to connect the
        // remote MQTT broker through TLS.
        this.sslContext = SSLContext.getInstance(this.mqttBrokerConfig.getProtocol());
        Log.i("[TSelfSignedSSLSocketFactory]", "Created a SSLContext.");
        this.sslContext.init(null, this.trustManagerFactory.getTrustManagers(), null);
        Log.i("[TSelfSignedSSLSocketFactory]", "Added the untrusted CA certificate to the SSLContext");
    }

    /**
     * This method returns the SocketFactory derived from the created SSLContext. That is, it
     * returns a SSLSocketFactory that will allow a given application to create SSL sockets to the
     * specific MQTT broker whose server certificate has been signed using the CA certificate
     * trusted in this class.
     *
     * @return SSLSocketFactory that will allow creating SSLSockets to connect to the specific MQTT
     * broker whose server certificate has been signed using the CA certificate trusted in this
     * class.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    public SSLSocketFactory getSelfSignedSSLSocketFactory() {
        if (this.sslContext != null) {
            return this.sslContext.getSocketFactory();
        }
        return null;
    }

    /**
     * This method returns the complete URL to connect to the MQTT broker whose server certificate
     * has been trusted in this class. It will have the following aspect:
     * ssl://serveraddress:serverport, i.e. ssl://test.mosquitto.org:8883
     *
     * @return String. The URL to connect to the MQTT broker whose server certificate has been
     * trusted in this class. Connecting to other untrusted MQTT broker different than the one
     * currently trusted in this class will be not possible using this class.
     * @author Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com
     * @since 1.0
     */
    public String getAssociatedMQTTBrokerURL() {
        return this.mqttBrokerConfig.getMQTTBrokerURL();
    }

}
