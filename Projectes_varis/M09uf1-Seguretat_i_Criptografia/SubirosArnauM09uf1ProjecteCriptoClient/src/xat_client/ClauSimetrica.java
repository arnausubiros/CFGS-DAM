
package xat_client;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author arnau
 */
public class ClauSimetrica {
    private SecretKey clauSecreta;       
    private Cipher xifrador;  
    private String algorisme = "RC2";  // RC2 tipus d'algorisme de clau simetrica
    private int tamanyClau=16;
    
    /**16*8=128
 * Es crea la  CLAU per  para encriptar/desencriptar
 * @param String value
 */
    public void afegirClau( String value ){
        byte[] valorBytes = value.getBytes();            
        clauSecreta = new SecretKeySpec( Arrays.copyOf( valorBytes, tamanyClau ) , algorisme );}
     /**
 * Metode  per encriptar un text
 * @param String text
 * @return String text encriptat
 */
    public String encriptar( String text ){
        String valor_encriptat="";
        try {
            xifrador = Cipher.getInstance(algorisme );             
            xifrador.init( Cipher.ENCRYPT_MODE, clauSecreta );             
            byte[] text_a_bytes = text.getBytes();
            byte[] xifrador_bytes = xifrador.doFinal( text_a_bytes );
            
            
            /*clase Base 64 (JDK 1.8)  converteix dades binariesen format text
               perque les dades codificades es puguin transportar rapidament 
              a traves de la xarxa sense perduda de dades */
            
            valor_encriptat = new BASE64Encoder().encode( xifrador_bytes );
        } catch (NoSuchAlgorithmException ex) {
            System.err.println( ex.getMessage() );
        } catch (NoSuchPaddingException ex) {
            System.err.println( ex.getMessage() );
        } catch (InvalidKeyException ex) {
            System.err.println( ex.getMessage() );
        } catch (IllegalBlockSizeException ex) {
            System.err.println( ex.getMessage() );
        } catch (BadPaddingException ex) {
            System.err.println( ex.getMessage() );
        }
        return valor_encriptat;
    }

     /**
 * Metode para desencriptat un text
 * @param text text encriptat
 * @return String text desencriptat
 */
    public String desencriptar( String text ){
        String valor_desencriptat="";        
        try {
            byte[] valor = new BASE64Decoder().decodeBuffer(text);                 
            xifrador = Cipher.getInstance(algorisme );            
            xifrador.init( Cipher.DECRYPT_MODE, clauSecreta );
            byte[] desxifrador_bytes = xifrador.doFinal( valor );
            valor_desencriptat = new String( desxifrador_bytes );                                  
        } catch (InvalidKeyException ex) {
            System.err.println( ex.getMessage() );
        }  catch (IllegalBlockSizeException ex) {
            System.err.println( ex.getMessage() );
        } catch (BadPaddingException ex) {
            System.err.println( ex.getMessage() );            
        }   catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }catch (NoSuchAlgorithmException ex) {
            System.err.println( ex.getMessage() );
        } catch (NoSuchPaddingException ex) {
            System.err.println( ex.getMessage() );
        }
        return valor_desencriptat;
    }
}
