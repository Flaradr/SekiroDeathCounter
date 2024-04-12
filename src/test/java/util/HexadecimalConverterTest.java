package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.HexadecimalConverter.*;

class HexadecimalConverterTest {

    @Test
    public void when_valid_hexadecimal_value_hexdecimalToIntLittleEndian_should_return_int_value_as_little_endian() {
        //given
        int expected = 693;
        String hex = "B502";

        //when
        int result = hexadecimalToIntLittleEndian(hex);

        //then
        assertEquals(expected, result);
    }

    @Test
    public void when_invalid_hexadecimal_value_hexadecimalToIntLittleEndian_should_throw_NumberFormatException() {
        //given
        String invalidValue = "B502X";

        //when

        //then
        assertThrows(NumberFormatException.class, () -> hexadecimalToIntLittleEndian(invalidValue));
    }

    @Test
    public void when_given_empty_array_bytesToHexadecimal_should_return_empty_string() {
        //given
        byte[] bytes = new byte[0];
        String expected = "";

        //when
        String result = bytesToHexadecimal(bytes);

        //then
        assertEquals(expected, result);
    }


    @Test
    public void when_given_non_empty_array_bytesToHexadecimal_should_return_casted_value() {
        //given
        byte[] bytes = new byte[]{-75, 2};
        String expected = "B502";

        //when
        String result = bytesToHexadecimal(bytes);

        //then
        assertEquals(expected, result);
    }


    @Test
    public void when_given_valid_hexadecimal_value_should_return_ascii() {
        //given
        String input = "54657374";
        String expected = "Test";

        //when
        String result = hexToAscii(input);

        //then
        assertEquals(expected, result);
    }


    @Test
    public void when_given_invalid_hexadecimal_value_should_throw_numberFormatException() {
        //given
        String input = "Invalid";

        //when

        //then
        assertThrows(NumberFormatException.class, () -> hexToAscii(input));
    }
}