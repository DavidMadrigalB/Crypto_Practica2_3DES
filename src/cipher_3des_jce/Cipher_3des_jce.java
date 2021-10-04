package cipher_3des_jce;

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
    }
    
    private static void menu_3des()
    {
        // This function it's and test for encipher and decipher files in 3-DES with JCE
        String name = "Prueba";
        String ext = ".txt";
        String mode = "ECB";
        TripleDes.encipher_file(name + ext, mode);
        TripleDes.decipher_file(name + ".des", mode, "key_" + name + ".key");
    }
    
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
