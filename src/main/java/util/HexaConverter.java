package util;

public class HexaConverter {


    /**
     * Read a hexadecimal value and return its integer value in little endian.
     *
     * @param hex Value in hexadecimal
     * @return integer value of the hexadecimal read in little endian
     */
    public static int hexToIntLittleEndian(String hex) throws NumberFormatException {
        StringBuilder res = new StringBuilder();
        String[] bytes = hex.split("(?<=\\G.{2})"); //Positive lookbehind
        for (String str : bytes) {
            res.insert(0, str);
        }
        return Integer.parseInt(res.toString(), 16);
    }

    /**
     * Convert an array of bytes to a String containing the hexadecimal value.
     * @param bytes Bytes to convert
     * @return Hexadecimal value of the bytes array
     */
    public static String bytesToHexa(byte[] bytes) {
        StringBuilder hexValue = new StringBuilder();
        for (byte aByte : bytes) {
            hexValue.append(String.format("%02X", aByte));
        }
        return hexValue.toString();
    }
}
