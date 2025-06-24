/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rsa;

import java.math.BigInteger;
import java.util.*;
import java.io.*;

public class RSAAlgoritmo {
    //vamos a definir las variables principales del algoritmo
    int tamPrimo;
    BigInteger n, p, q;   // p*q
    BigInteger fi;   //(p-1)*(q-1)
    BigInteger e, d;
    
    //constructor
    public RSAAlgoritmo(){
        this.tamPrimo = tamPrimo;
    }

    public int getTamPrimo() {
        return tamPrimo;
    }

    public void setTamPrimo(int tamPrimo) {
        this.tamPrimo = tamPrimo;
    }
    
    //metodo para generar primos
    public void generarPrimos(){
        //se debe de comprobar que solo sea divisible entre si mismo y la unidad
        p = new BigInteger(tamPrimo, 10, new Random());
        do q = new BigInteger(tamPrimo, 10, new Random());
        while(q.compareTo(p)==0);
        
        
    }
    
    /*
    Necesitamos generar las claves
    n = p * q
    fi = (p-1)*(q-1)
    determinar el e y d como un coprimo que sea 1 < e < n
    */
    
    public void generarClaves(){
        n = p.multiply(q);
        //tenemos que utilizar el metodo subtract
        fi = p.subtract(BigInteger.valueOf(1));
        
        fi = fi.multiply(q.subtract(BigInteger.valueOf(1)));
        
        
        //calcular e
        
        do e = new BigInteger(2*tamPrimo, new Random());
        while(
                (e.compareTo(fi) != -1) || 
                (e.gcd(fi).compareTo(BigInteger.valueOf(1)) != 0)
                );
        
        d = e.modInverse(fi);
    }
    
    ///programar el metodo para cifrar
    public BigInteger[] cifrar(String mensaje){
        
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        //formula es C = M **e mod(n)
        
        BigInteger[] bigdigitos = new BigInteger[digitos.length];
        
        for(i = 0; i < bigdigitos.length; i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        
        //aplicamos la formula
        BigInteger[] cifrado = new BigInteger[bigdigitos.length];
        
        for(i = 0; i < bigdigitos.length; i++){
            cifrado[i] = bigdigitos[i].modPow(e, n);
        }
        
        return cifrado;
        
    }
    
    //metodo para descifrar
    public String descifrar(BigInteger[] cifrado){
        BigInteger[] descifrar = new BigInteger[cifrado.length];
        //aplico la formula para descifrar
        for(int i = 0; i < descifrar.length; i++){
            // M = C ** d mod(n)
            descifrar[i] = cifrado[i].modPow(d, n);
        }
        
        //crear el nuevo arreglo
        char[] charArray = new char[descifrar.length]; 
        
        for(int i = 0; i < charArray.length; i++){
            charArray[i] = (char)(descifrar[i].intValue());
        }
        
        return (new String(charArray));
    }
    
}