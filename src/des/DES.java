/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;

/**
 *
 * @author PC
 */
public class DES {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //Generate key for DES
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecretKey secretKey = keyGenerator.generateKey();
        
        //Text Enc & Dec
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        
        //Declare wether to enc or dec
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        byte[] message = "Anas Hesham".getBytes();
        byte[] messageEnc = cipher.doFinal(message);
        System.out.println("Cipher Text: " + new String(messageEnc));
        
        
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        byte[] dec = cipher.doFinal(messageEnc);
        
        System.out.println("Plain Text: " + new String(dec));
    }
}
