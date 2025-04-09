/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package aes;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Ejemplo de Cifrado AES");
        String mensaje = "había una vez un patito que decía miau miau";
        
        String mensajeCifrado = CifradorAES.encrypt(mensaje);
        System.out.println("El mensaje cifrado es: "+ mensajeCifrado);
        
        //String mensajeDescifrado = CifradorAES.decrypt(mensajeCifrado);
        //System.out.println("El mensaje descifrado es: "+mensajeDescifrado);
    }
    
}
