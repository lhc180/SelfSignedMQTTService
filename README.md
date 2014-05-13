SelfSignedMQTTService
=====================

This classes allow the creation of an android self-signed SSL/TLS MQTT service
for receiving push notifications and publish from/to MQTT brokers. It provides
bindings to the initiating activity so that it can be used in a bidirectional
way. It does not aim to support client authentication based in certificates. It
uses TLS connection from a client to a MQTT broker, based in one-way
authentication, through the broker certificate (hence, clients authentication
mechanisms should be implemented in the application). It uses a customized 
TrustManager to trust the self-signed server certificate.

