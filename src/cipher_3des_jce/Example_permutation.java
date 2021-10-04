/*
 * This class, includes an example of byte permutation (8 bits)
 */
package cipher_3des_jce;
/**
 *
 * @author David Madrigal Buendía
 */
public class Example_permutation {
    /**
     * This method permute a byte type (8 bits), with the input permutation
     * @param data bits to permutate, recieve it in a byte type
     * @param permutation array, with the integers with the positions of the bits
     *          to permute data bits
     * @return bits permutated
     */
    static public byte permute_byte (
        byte data, byte[] permutation
    ) {
        int i;
        byte[] data_permutate;
        String bits, bits_permutate;
        // To left pad, resulting string with zeros
        bits = String.format("%8s", 
                    Integer.toBinaryString(data & 0xFF))
                    .replace(' ', '0');
        // Permutation has a size of 8
        bits_permutate = "";
        // Pemutate data's bits 
        for (i = 0; i < 8; i++)
        {
            bits_permutate += bits.charAt(permutation[i]);
        }
        // Return the bits permutate
        return Byte.parseByte(bits_permutate, 2);
    }
    
    /**
     * This method just print bits of a byte type in console
     * @param bits the bits to print in console
     */
    static public void print_bits (
        byte bits
    ) {
        System.out.println(String.format("%8s", Integer.toBinaryString(bits & 0xFF)).replace(' ', '0'));
    }
}