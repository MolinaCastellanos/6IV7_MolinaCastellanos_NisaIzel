/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package des;

import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.interfaces.*;
import javax.crypto.spec.*;
import javax.swing.JFrame;
/**
 *
 * @author nisit
 */
public class Metodos {
    
    private static SecretKey clave;
    
    public static boolean Cifrar(String ruta, Interfaz1 Parent) throws Exception {

        if (ruta.isEmpty()) {
            Parent.mostrarMensaje("Por favor selecciona un archivo.");
            return false;
        }

        KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
        generadorDES.init(56);
        clave = generadorDES.generateKey();
        Cipher cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cifrador.init(Cipher.ENCRYPT_MODE, clave);

        FileInputStream entrada = new FileInputStream(ruta);
        FileOutputStream salida = new FileOutputStream(ruta + ".cifrado");

        byte[] buffer = new byte[1000];
        int bytesLeidos;
        while ((bytesLeidos = entrada.read(buffer)) != -1) {
            byte[] bufferCifrado = cifrador.update(buffer, 0, bytesLeidos);
            if (bufferCifrado != null) {
                salida.write(bufferCifrado);
            }
        }

        byte[] finalCifrado = cifrador.doFinal();
        if (finalCifrado != null) {
            salida.write(finalCifrado);
        }

        entrada.close();
        salida.close();

        // Guardar la clave en un archivo
        guardarClave(ruta + ".cifrado.key");
        
        Parent.mostrarMensaje("Archivo cifrado correctamente: " + ruta + ".cifrado");
        return true;
    }
    
    public static boolean descifrar(String ruta, Interfaz1 Parent) throws Exception {
        if (ruta.isEmpty()) {
            Parent.mostrarMensaje("Por favor selecciona un archivo.");
            return false;
        }

        // Cargar la clave desde archivo
        try {
            clave = cargarClave(ruta + ".key");
        } catch (IOException e) {
            Parent.mostrarMensaje("No se pudo cargar la clave. ¿Cifraste primero el archivo?");
            return false;
        }
        
        Cipher descifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
        descifrador.init(Cipher.DECRYPT_MODE, clave);

        FileInputStream entrada = new FileInputStream(ruta);
        FileOutputStream salida = new FileOutputStream(ruta + ".descifrado");

        byte[] buffer = new byte[1000];
        int bytesLeidos;
        while ((bytesLeidos = entrada.read(buffer)) != -1) {
            byte[] bufferDescifrado = descifrador.update(buffer, 0, bytesLeidos);
            if (bufferDescifrado != null) {
                salida.write(bufferDescifrado);
            }
        }

        byte[] finalDescifrado = descifrador.doFinal();
        if (finalDescifrado != null) {
            salida.write(finalDescifrado);
        }

        entrada.close();
        salida.close();

        Parent.mostrarMensaje("Archivo descifrado correctamente: " + ruta + ".descifrado");
        return true;
    }
    
    private static void guardarClave(String rutaArchivoClave) throws IOException {
        FileOutputStream fos = new FileOutputStream(rutaArchivoClave);
        fos.write(clave.getEncoded());
        fos.close();
    }

    private static SecretKey cargarClave(String rutaArchivoClave) throws IOException {
        FileInputStream fis = new FileInputStream(rutaArchivoClave);
        byte[] claveBytes = fis.readAllBytes();
        fis.close();
        return new SecretKeySpec(claveBytes, "DES");
    }
    
    private static void mostrarBytes(byte[] buffer) {
        //gracias a que es ECB solo tenemos que escribir el formato
        //del tipo de buffer para el archivo
        System.out.write(buffer, 0, buffer.length);
    }
}
