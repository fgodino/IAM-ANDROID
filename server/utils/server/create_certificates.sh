openssl genrsa -des3 -out server.key 1024
openssl req -new -key server.key -out server.csr
cp server.key server.key.org
openssl rsa -in server.key.org -out server.key
openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
keytool -import -alias "my server cert" -file server.crt -provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath "../bcprov-jdk16-146.jar" -keystore truststore.bks -storepass emotion -storetype BKS