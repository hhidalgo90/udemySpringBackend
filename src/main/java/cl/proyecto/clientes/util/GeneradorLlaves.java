package cl.proyecto.clientes.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import java.nio.file.Files;
import java.security.*;

import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class GeneradorLlaves {
    private Logger logger = LoggerFactory.getLogger(GeneradorLlaves.class);
    private static final String LLAVE_PRIVADA = "private_key.der";
    private static final String LLAVE_PUBLICA = "public_key.der";

    public GeneradorLlaves() {
        /*try {
            logger.info("[GeneradorLlaves] Inicio constructor. ");
            generarLlavesPublicaYprivada();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/
    }

    private void generarLlavesPublicaYprivada() throws NoSuchAlgorithmException {
        logger.info("[GeneradorLlaves] generarLlavesPublicaYprivada Inicio. ");
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();

        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();
        logger.info("[GeneradorLlaves] generarLlavesPublicaYprivada privateKey. " + privateKey);
        logger.info("[GeneradorLlaves] generarLlavesPublicaYprivada publicKey. " + publicKey);

        try {
            guardarLlavesEnArchivo(pair);
        } catch (FileNotFoundException e) {
            logger.error("[GeneradorLlaves] FileNotFoundException error al crear archivo. " + e.getMessage());
        }
    }

    private void guardarLlavesEnArchivo(KeyPair parDeLlaves) throws FileNotFoundException {
        try {
            FileOutputStream fos = new FileOutputStream(LLAVE_PUBLICA);
            FileOutputStream fos2 = new FileOutputStream(LLAVE_PRIVADA);
            fos.write(parDeLlaves.getPublic().getEncoded());
            fos2.write(parDeLlaves.getPrivate().getEncoded());
            logger.info("[Formato llave publica]" + parDeLlaves.getPublic().getFormat());
            logger.info("[Formato llave privada]" + parDeLlaves.getPrivate().getFormat());
        } catch (IOException e) {
            logger.error("[GeneradorLlaves] IOException error al crear archivo. " + e.getMessage());
        }
    }

    public String obtenerLlavePublica() throws IOException {
        logger.info("[obtenerLlavePublica] INICIO ");
        File publicKeyFile = new File(LLAVE_PUBLICA);

        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());


        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            logger.error("[obtenerLlavePublica] NoSuchAlgorithmException error al obtener llaves rsa. " + e.getMessage());
        }
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey pubKey = null;
        try {
            pubKey = keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException e) {
            logger.error("[obtenerLlavesRSA] InvalidKeySpecException error al generar llaves rsa. " + e.getMessage());
        }
        return pubKey.toString();
    }

    public String obtenerLlavePrivada()  {
        File privateKeyFile = new File(LLAVE_PRIVADA);
        byte[] privateKeyBytes = new byte[0];
        KeyFactory keyFactory = null;
        PrivateKey privKey = null;
        try {
            privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
            keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec  privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            privKey = keyFactory.generatePrivate(privateKeySpec);
        } catch (IOException e) {
            logger.error("[obtenerLlavePrivada] IOException error al obtener llaves rsa. " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            logger.error("[obtenerLlavePrivada] NoSuchAlgorithmException error al obtener llaves rsa. " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            logger.error("[obtenerLlavePrivada] InvalidKeySpecException error al generar llaves rsa. " + e.getMessage());
        }
        return privKey.toString();
    }

    public String getPublicKey(){
        File privateKeyFile = new File(LLAVE_PUBLICA);
        InputStream targetStream = null;
        try {
            targetStream = new FileInputStream(privateKeyFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String publicKey = null;
        publicKey = targetStream.toString();
        return publicKey;
    }
}
