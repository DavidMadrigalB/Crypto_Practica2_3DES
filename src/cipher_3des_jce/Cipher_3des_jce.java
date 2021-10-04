package cipher_3des_jce;

import java.util.Scanner;

/**
 *
 * @author David Madrigal Buendía
 */
public class Cipher_3des_jce {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //menu_example_permutation();
        menu_3des();
        System.exit(0);
    }
    /**
     * Example, using TripleDes class
     */
    private static void menu_3des()
    {
        // This function it's and test for encipher and decipher files in 3-DES with JCE
        Scanner keyboard = new Scanner(System.in);
        String name, ext, mode, file_name_key;
        
        System.out.println("Write name file (just file name without extention) to encipher: ");
        System.out.println("(The filename must be in the same directory)");
        name = keyboard.nextLine();
        System.out.println("Write the extention of the file (.txt) to encipher: ");
        ext = keyboard.nextLine();
        System.out.println("Write the mode of operation (Uppercase):");
        System.out.println("Some options: ECB, CBC, CBF, OFB");
        mode = keyboard.nextLine();
        
        try
        {
            file_name_key = TripleDes.generate_file_key();
            
            TripleDes.encipher_file(name + ext, mode, file_name_key);
            TripleDes.decipher_file(name + ".des", mode, file_name_key, ext);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Example, using Example_permutation class
     */
    private static void menu_example_permutation()
    {
        byte[] permutation = {1, 5, 2, 0, 3, 7, 4, 6};
        
        byte bits = -67;
        System.out.print("Bits de entrada: ");
        Example_permutation.print_bits(bits);
        Byte bits_permutate = Example_permutation.permute_byte(
                bits, permutation);
        System.out.print("Bits permutados: ");
        Example_permutation.print_bits(bits_permutate);
    }
}
