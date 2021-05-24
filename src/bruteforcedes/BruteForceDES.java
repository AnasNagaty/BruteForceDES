/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bruteforcedes;

import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;

/**
 *
 * @author PC
 */
public class BruteForceDES {

    /**
     * @param args
     * @throws java.lang.Exception
     */
    
    public static void main(String[] args) throws Exception {
        String padded;
        String decrypted;
        String plaintext;
        
        //Generate key for DES
        String initkey = "00000006";
        byte[] key = initkey.getBytes();
        DESKeySpec dks;
        
        //Check parity and adjust it
        if(isDESParityAdjusted(key))
        {
            byte[] keyParAdj = adjustDESParity(key);
            dks =  new DESKeySpec(keyParAdj);
        }
        else
        {
            dks =  new DESKeySpec(key);
        }
        
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        //Declare wether to enc or dec
        cipher.init(Cipher.ENCRYPT_MODE,desKey);
        byte []message = "Decrypted Successfully".getBytes();
        byte []messageEnc = cipher.doFinal(message);
        plaintext = new String(message);
        System.out.println("Cipher Text: " + new String(messageEnc));
        System.out.println();


        for(int i=0;i<100;i++){
            try{
                padded = padLeftZeros(Integer.toString(i),8);
                System.out.println(padded);
                
                byte[] Test = padded.getBytes();
                DESKeySpec dksTest;
                
                if(isDESParityAdjusted(Test))
                {
                    byte[] TestParAdj = adjustDESParity(Test);
                    dksTest =  new DESKeySpec(TestParAdj);
                }
                else
                {
                    dksTest =  new DESKeySpec(Test);
                }
                
                SecretKeyFactory skf2 = SecretKeyFactory.getInstance("DES");
                SecretKey desKey2 = skf2.generateSecret(dksTest);
                Cipher cipher2 = Cipher.getInstance("DES/ECB/PKCS5Padding");
                
                cipher2.init(Cipher.DECRYPT_MODE,desKey2);
                byte[] dec = cipher2.doFinal(messageEnc);
                decrypted = new String(dec);
                
                if(decrypted.equals(plaintext)){
                    System.out.println("Decrypted Text: " + decrypted);
                    System.out.println();
                    break;
                }
            }catch (BadPaddingException ex){
                System.out.println("Wrong Key.");
                System.out.println();
            }   
        }
    }

    public static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);
    
        return sb.toString();
    }
    
    
    public static byte[] adjustDESParity (byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i];
            bytes[i] = (byte)((b & 0xfe) | ((((b >> 1) ^ (b >> 2) ^ (b >> 3) ^ (b >> 4) ^ (b >> 5) ^ (b >> 6) ^ (b >> 7)) ^ 0x01) & 0x01));
        }

        return bytes;
    }
    
    
    /*
     * @param bytes the byte[] to be checked
     * @return true if parity is adjusted else returns false
     */
    public static boolean isDESParityAdjusted (byte[] bytes) {
        byte[] correct = (byte[])bytes.clone();
        adjustDESParity(correct);
        return  Arrays.equals(bytes, correct);
    }
}