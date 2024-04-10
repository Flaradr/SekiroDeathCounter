package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.HexaConverter.hexToIntLittleEndian;

class HexaConverterTest {

    @Test
    public void when_valid_hexadecimal_value_hexToIntLittleEndian_should_return_int_value_as_little_endian() {
        int expected = 693;
        String hex = "B502";
        int result = hexToIntLittleEndian(hex);

        assertEquals(expected, result);
    }

    @Test
    public void when_invalid_hexadecimal_value_hexToIntLittleEndian_should_throw_NumberFormatException() {
        String invalidValue = "B502X";

        assertThrows(NumberFormatException.class, () -> hexToIntLittleEndian(invalidValue));
    }
}