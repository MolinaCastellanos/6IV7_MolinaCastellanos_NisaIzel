/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package des;

/**
 *
 * @author Alumno
 */
import java.io.*;
    // es para definir entradas y salidas del sistema
    // para el manejo de archivos
import java.security.*;
    //es para el cálculo de subllaves
import javax.crypto.*;
    //es para definir el algoritmo de cifrado
import javax.crypto.interfaces.*;
    //para el algoritmo
import javax.crypto.spec.*;
    //para definir el tamaño de la clave y subclases


public class Main {

    public static void main(String[] args) throws Exception {
        /*
        Lo primero que vamos a crear un programa mediante
        el cual debe de leer un archivo de texto plano
        se debe de introducir una clave
        debe de cifrarlo y generar el archkvo correspondiente cifrado
        */
        
        //Vamos a usar DES
        
        //Vamos a comprobar que exista un archivo cargado
        if (args.length != 1) {
            mensajeAyuda();
            System.exit(1);
        }
        
        //Paso 1. Debemos de definir el algoritmo y su clave
        System.out.println("1.- Generar las claves DES");
        //Para generar las claves utilizamos la clase KeyGenerator
        KeyGenerator generadorDES = 
                KeyGenerator.getInstance("DES");
        System.out.println("");
        //Debemos de inicializar el tamaño de la clave
        generadorDES.init(56); //el tamaño de la clave es de 64 bits
        //El algoritmo envía error si no es exactamente 56
        
        //Tenemos dos opciones par la clave, la creamos de forma manual
            //si es de forma manual se ingresa por parte del usuario
            //se valida que el tamaño sea de 8 caracteres
            //transformamos la clave en bits
            
        //o utilizamos la clase SecretKey
            //Estas son las subclaves para las 16 rondas
        SecretKey clave = generadorDES.generateKey();
        System.out.println("la clave es: " + clave);
        //no es posible distinguir los bytes de un caracter si no está codificado
        mostrarBytes(clave.getEncoded());
        System.out.println("Clave codificada: " + clave.getEncoded());
        
        /*
        El tipo de cifrado es DES, es de tipo Simétrico
        Significa que la clave de cifra es la misma para descifrar.
        Hay que definir el modo de operación del cifrado:
            Flujo o por Bloques
            ECB
        Hay que definir si va a tener o no relleno
            Debemos de aplicar un estándar para didcho relleno
            El estándar del relleno es programar PKCS5
        */
        
        Cipher cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
        
        //vamos a crear el menu para cifrar y descifrar
        System.out.println("2.- Cifrar un feichero con DES :"
            + args[0] + "dejamos el resultado en:"
            + args[0] + ".cifrado");
        
        //tenemos que cargar el archivo y ejectuar el cifrado
        cifrador.init(Cipher.ENCRYPT_MODE, clave);
        
        //aquí es donde es importante recordar el modo
        //ECB no puede automatizar el flujo del bloque
        
        byte[] buffer = new byte[1000];
        //este arreglo sirve para guardar el resultado
        byte[] bufferCifrado;
        
        //Definir el archivo
        FileInputStream entrada = new FileInputStream(args[0]);
        FileOutputStream salida = new FileOutputStream(args[0] + ".cifrado");
        
        
        int bytesLeidos = entrada.read(buffer, 0, 1000);
        
        while(bytesLeidos != -1) {
            bufferCifrado = cifrador.update(buffer,0,1000);
            salida.write(bufferCifrado);
            bytesLeidos = entrada.read(buffer, 0, bytesLeidos);
            
        }
        
        //construir salida
        bufferCifrado = cifrador.doFinal();
        //genero el archivo de salida
        salida.write(bufferCifrado);
        
        //Mantiene una conexión con el servidor que podría ser mal utilizada
        entrada.close();
        salida.close();
        
        //ahora a descifrar
        System.out.println("3.- Descifrar un fichero con DES"
            + args[0] + ".cifrado"
            + args[0] + ".descifrado");
        
        
        //este arreglo sirve para guardar el resultado
        byte[] bufferDescifrado;
        
        //Definir el archivo
        entrada = new FileInputStream(args[0]+".cifrado");
        salida = new FileOutputStream(args[0] + ".descifrado");
        
        
        bytesLeidos = entrada.read(buffer, 0, 1000);
        
        while(bytesLeidos != -1) {
            bufferDescifrado = cifrador.update(buffer,0,1000);
            salida.write(bufferDescifrado);
            bytesLeidos = entrada.read(buffer, 0, bytesLeidos);
            
        }
        
        //construir salida
        bufferDescifrado = cifrador.doFinal();
        //genero el archivo de salida
        salida.write(bufferDescifrado);
        
        //Mantiene una conexión con el servidor que podría ser mal utilizada
        entrada.close();
        salida.close();
        
    }
    
    private static void mostrarBytes(byte[] buffer) {
        //gracias a que es ECB solo tenemos que escribir el formato
        //del tipo de buffer para el archivo
        System.out.write(buffer, 0, buffer.length);
    }
    
    private static void mensajeAyuda() {
        System.out.println("Ejemplo de un programa "
                + "que sirve para cifrar y descifrar con DES");
        System.out.println("Favor de ingresar un archivo de texto "
                + "plano, si no, no funciona, o sea .txt");
        
    }
}
