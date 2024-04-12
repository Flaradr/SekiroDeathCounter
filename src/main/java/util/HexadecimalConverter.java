package util;

public class HexadecimalConverter {


    /**
     * Read a hexadecimal value and return its integer value in little endian.
     *
     * @param hex Value in hexadecimal
     * @return integer value of the hexadecimal read in little endian
     */
    public static int hexadecimalToIntLittleEndian(String hex) throws NumberFormatException {
        StringBuilder res = new StringBuilder();
        String[] bytes = hex.split("(?<=\\G.{2})"); //Positive lookbehind
        for (String str : bytes) {
            res.insert(0, str);
        }
        return Integer.parseInt(res.toString(), 16);
    }

    /**
     * Convert an array of bytes to a String containing the hexadecimal value.
     *
     * @param bytes Bytes to convert
     * @return Hexadecimal value of the bytes array
     */
    public static String bytesToHexadecimal(byte[] bytes) {
        StringBuilder hexValue = new StringBuilder();
        for (byte aByte : bytes) {
            hexValue.append(String.format("%02X", aByte));
        }
        return hexValue.toString();
    }

    /**
     * Transform hexadecimal value to its ascii value
     * @param hexStr
     * @return String of ASCII
     */
    public static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }
}
