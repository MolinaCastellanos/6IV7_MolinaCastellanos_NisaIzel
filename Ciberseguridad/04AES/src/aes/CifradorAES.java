/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aes;


import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.*;
import java.util.Base64;

public class CifradorAES {
    
    //generar las subllaves y los métodos para cifrar y descifrar
    
    //un método para la llave
    public static final  byte[] keyvalue = new byte[] {
      /*
        Recordemos que dentro de AES se van a manejar diferentes tamaños
        de la llave de acuerdo al tipo de operación: de 128, 192, 256
            128: 16 caracteres, 9 rondas
            192: 24 caracteres, 11 rondas
            256: 32 caracteres, 13 rondas
      */     
        'q', 'w', 'e', 'r', 't', 'y', 'u', 'i',
        'q', 'w', 'e', 'r', 't', 'y', 'u', 'i'
    };
    
    //vamos a definir la instancia del alforitmo
    private static final String instancia = "AES";
    
    public static String encrypt(String Data) throws Exception {
        /*
        para poder cifrar debemos de generar las subclaves necesarias para ejecutar 
        el algoritmo acorde al múmero de rondas, para
        ello vamos a ocupar un método de generación de claves
        */
        Key subllave = generateKey();
        
        //inicializamos el cifrado
        Cipher cifrado = Cipher.getInstance(instancia);
        cifrado.init(Cipher.ENCRYPT_MODE, subllave);
        
        //vamos a obtener el mensaje que se quiere cifrar
        //y lo transformamos en bytes
        
        byte[] encValores = cifrado.doFinal(Data.getBytes());
        System.out.println("Mensaje Cifrado sin formato: " + encValores);
        
        //debemos aplicar formato de codificación formato 64 a partir de la librería sun
        //con un objeto BASE64Encoder
        String valoresEncriptadosFormato = Base64.getEncoder().encodeToString(encValores);
        
        String cadenaEncriptada = encValores.toString();
        return cadenaEncriptada;
    }
    
    public static String decrypt(String valoresEncriptados) throws Exception {
        /*
        para poder cifrar debemos de generar las subclaves necesarias para ejecutar 
        el algoritmo acorde al múmero de rondas, para
        ello vamos a ocupar un método de generación de claves
        */
        Key subllave = generateKey();
        
        //inicializamos el cifrado
        Cipher cifrado = Cipher.getInstance(instancia);
        cifrado.init(Cipher.DECRYPT_MODE, subllave);
        
        //vamos a obtener el mensaje que se quiere cifrar
        //y lo transformamos en bytes
        
        byte[] decodificarValores = Base64.getDecoder().decode(valoresEncriptados);
        byte[] decValores = cifrado.doFinal(decodificarValores);
        //System.out.println("Mensaje Descrifrado sin formato: " + decValores);
        
        //debemos aplicar formato de codificación formato 64 a partir de la librería sun
        //con un objeto BASE64Encoder
        String valoresEncriptadosFormato = Base64.getEncoder().encodeToString(decValores);
        
        String valoresDescifrados = new String(decValores);
        return valoresDescifrados;
    }
    
    private static Key generateKey() throws Exception {
        //vamos a ocupar llaves secretas
        Key subllavekawaii = new SecretKeySpec(keyvalue, instancia);
        return subllavekawaii;
    }
    
    
}
