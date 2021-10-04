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
     * This function generate a key file for 3DES and save it into a .key file
     * @return the name of the key file
     */
    static String generate_file_key() throws IOException, NoSuchAlgorithmException
    {
        return generate_file_key("key");
    }
    /**
     * This function generate a key file for 3DES and save it into a .key file
     * @param name the name of the key file
     * @return the name of the key file
     */
    static String generate_file_key(String name) throws IOException, NoSuchAlgorithmException
    {
        // Create an instance about which cipher we will use for generate a key, in this case 3DES
        KeyGenerator key_generator = KeyGenerator.getInstance("DESede");
        // Generate a secret key for the cipher and descipher, in this case 3DES
        SecretKey key = key_generator.generateKey();

        String name_file = name + ".key";
        File file_key = new File(name_file);
        FileOutputStream fos = new FileOutputStream(file_key);
        // Save the key used cipher content with base64 encoder in a file
        fos.write(Base64.getEncoder().encode(key.getEncoded()));
        fos.close();
        return name_file;
    }
    /**
     * This function read a key file
     * @param file_name_key the name or path of the key file
     * @return SecretKey read
     */
    static SecretKey read_file_key(String file_name_key) throws IOException
    {
        // Read the key, in the file key
        File file_key = new File(file_name_key);
        byte[] bytes_key = Files.readAllBytes(file_key.toPath());
        // Decode bytes in base64 encoded
        byte[] decoded = Base64.getDecoder().decode(bytes_key);
        // Initialize secret key
        SecretKey key = new SecretKeySpec(decoded, "DESede");
        return key;
    }
    /**
     * This function encipher a file using 3DES
     * @param name the name of a file to encipher its content
     * @param variant it can be a variant of 3des like: 
     *          ECB, CBC, CBF, OFB
     */
    static void encipher_file(String name, String variant, String file_name_key)
    {
        try
        {
            SecretKey key = read_file_key(file_name_key);
            
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
            
            File file_in = new File(name);
            File file_out;

            String[] parts_name = name.split("\\.");
            String extention;
            // Encipher the content of a file
            byte[] bytes_content = Files.readAllBytes(file_in.toPath());
            byte[] bytes_encipher = cipher.doFinal(bytes_content);
            
            // Obtain the name of the file without its extention
            extention = "." + parts_name[parts_name.length - 1];
            // Change the extention of our output file (.des)
            file_out = new File(name.replace(extention, ".des"));
            FileOutputStream fos = new FileOutputStream(file_out);
            // Save the encipher content with base64 encoder in a file
            fos.write(Base64.getEncoder().encode(bytes_encipher));
            fos.close();
        }catch(NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | 
                IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException |
                IOException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * This function decipher a file encipher by 3DES
     * @param file_name the name of a file to decipher its content
     * @param variant it can be a variant of 3des like:
     *          ECB, CBC, CBF, OFB
     * @param file_name_key the name of the key file
     */
    static void decipher_file(String file_name, String variant, String file_name_key, String ext)
    {
        try
        {
            SecretKey key = read_file_key(file_name_key);
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
                file_out = new File("recovered" + ext);
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