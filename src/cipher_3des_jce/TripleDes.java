/*
 * In this class we do an application about 3des
 * using the library JCE.jar
 * We cipher and descipher a file with 3des
 */
package cipher_3des_jce;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author David Madrigal Buendía
 */
public class TripleDes {
    /**
     * @param name the name of a file to encipher its content
     * @param variant it can be a variant of 3des like: 
     *          ECB, CBC, CBF, OFB
     */
    static SecretKey encipher_file(String name, String variant)
    {
        try
        {
            // Create an instance about which cipher we will use for generate a key, in this case 3DES
            KeyGenerator key_generator = KeyGenerator.getInstance("DESede");
            // Generate a secret key for the cipher and descipher, in this case 3DES
            SecretKey key = key_generator.generateKey();
            byte[] bytes = key.getEncoded();
            key = new SecretKeySpec(bytes, "DESede");
            
            // Create an instance cipher of 3DES and its variant
            Cipher cipher = Cipher.getInstance("DESede/" + variant + "/PKCS5Padding");
            // Initialize encrypt mode
            if(variant.equals("ECB")) 
            {
                cipher.init(Cipher.ENCRYPT_MODE, key);
            }else
            {
                cipher.init(Cipher.ENCRYPT_MODE, key,
                        new IvParameterSpec(new byte[8]));
            }
            
            try
            {
                File file_in = new File(name);
                File file_out, file_key;
                
                String[] parts_name = name.split("\\.");
                String extention;
                
                byte[] bytes_content;
                byte[] bytes_encipher;
                
                // Encipher the content of a file
                bytes_content = Files.readAllBytes(file_in.toPath());
                bytes_encipher = cipher.doFinal(bytes_content);
                // Obtain the name of the file without its extention
                extention = "." + parts_name[parts_name.length - 1];
                // Change the extention of our output file (.des)
                file_out = new File(name.replace(extention, ".des"));
                try (FileOutputStream fos = new FileOutputStream(file_out))
                {
                    // Save the encipher content with base64 encoder in a file
                    fos.write(Base64.getEncoder().encode(bytes_encipher));
                    fos.close();
                }catch(IOException e)
                {
                    e.printStackTrace();
                }
                
                file_key = new File("key_" + name.replace(extention, ".key"));
                try (FileOutputStream fos = new FileOutputStream(file_key))
                {
                    // Save the key used cipher content with base64 encoder in a file
                    fos.write(Base64.getEncoder().encode(key.getEncoded()));
                    fos.close();
                }catch(IOException e)
                {
                    e.printStackTrace();
                }
                return key;
            }catch(IOException e)
            {
                e.printStackTrace();
            }
            
        }catch(NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | 
                IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @param file_name the name of a file to decipher its content
     * @param variant it can be a variant of 3des like:
     *          ECB, CBC, CBF, OFB
     * @param file_name_key the name of the key file
     */
    static void decipher_file(String file_name, String variant, String file_name_key)
    {
        try
        {
            // Read the key, in the file key
            File file_key = new File(file_name_key);
            byte[] bytes_key = Files.readAllBytes(file_key.toPath());
            // Decode bytes in base64 encoded
            byte[] decoded = Base64.getDecoder().decode(bytes_key);
            // Initialize secret key
            SecretKey key = new SecretKeySpec(decoded, "DESede");
            try
            {
                // Create an instance cipher of 3DES and its variant
                Cipher cipher = Cipher.getInstance("DESede/" + variant + "/PKCS5Padding");
                // Initialize Decryp mode
                if(variant.equals("ECB")) 
                {
                    cipher.init(Cipher.DECRYPT_MODE, key);
                }else
                {
                    cipher.init(Cipher.DECRYPT_MODE, key,
                            new IvParameterSpec(new byte[8]));
                }
                
                File file_in = new File(file_name);
                File file_out;

                byte[] bytes_content;
                byte[] bytes_decipher;
                // Decipher the content of a file
                bytes_content = Files.readAllBytes(file_in.toPath());
                // First decode bytes in the file (it was encoded in base 64)
                bytes_decipher = cipher.doFinal(Base64.getDecoder().decode(bytes_content));
                
                // Output file with data recovered
                file_out = new File("recovered.txt");
                try (FileOutputStream fos = new FileOutputStream(file_out))
                {
                    // Save the content
                    fos.write(bytes_decipher);
                    fos.close();
                }catch(IOException e)
                {
                    e.printStackTrace();
                }
            }catch(NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | 
                    IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e)
            {
                e.printStackTrace();
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}