package com.pauljulius.bankocr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class BankOCRTest {

    @Test
    public void should_read_four_lines_when_provided() throws IOException {
        LineNumberReader reader = new LineNumberReader(new StringReader("foo\nbar\nbaz\nfoobarbaz\n"));
        assertEquals(Arrays.asList(new String[]{
                "foo", 
                "bar", 
                "baz", 
                "foobarbaz"}),
                BankOCR.readFourLines(reader));
    }
    
    @Test
    public void should_return_null_when_no_lines_left_to_read() throws IOException {
        LineNumberReader reader = new LineNumberReader(new StringReader("only \n one \n group \n here \n"));
        BankOCR.readFourLines(reader);
        assertNull("The second read should return null.", BankOCR.readFourLines(reader));
    }

    @Test (expected=IOException.class) 
    public void should_bomb_when_full_four_line_record_not_available() throws IOException{
        LineNumberReader reader = new LineNumberReader(new StringReader("only \n 3 \n lines \n"));
        BankOCR.readFourLines(reader);
    }
    
    @Test
    public void should_convert_four_lines_with_zeros_to_all_zero_account_number() {
        String line1 = " _  _  _  _  _  _  _  _  _ ";
        String line2 = "| || || || || || || || || |";
        String line3 = "|_||_||_||_||_||_||_||_||_|";
        String line4 = "";
        assertEquals("000000000", BankOCR.convert(asList(line1, line2, line3, line4)));
    }

    @Test
    public void should_convert_four_lines_with_ones_to_all_one_account_number() {
        String line1 = "                           ";
        String line2 = "  |  |  |  |  |  |  |  |  |";
        String line3 = "  |  |  |  |  |  |  |  |  |";
        String line4 = "";
        assertEquals("111111111", BankOCR.convert(asList(line1, line2, line3, line4)));
    }
    
    @Test
    public void should_return_display_digit_when_at_least_three_chars_provided() {
        String line1 = " _ ";
        String line2 = "| |";
        String line3 = "|_|";
        String line4 = "";
        assertEquals(line1 + line2 + line3, BankOCR.nextDisplayDigit(asList(line1, line2, line3, line4)));
    }
    
    @Test
    public void should_iterate_through_display_digits_when_multiple_provided() {
        String line1 = " _    ";
        String line2 = "| |  |";
        String line3 = "|_|  |";
        String line4 = "";
        List<String> lines = asList(line1, line2, line3, line4);
        assertEquals(BankOCR.ZERO, BankOCR.nextDisplayDigit(lines));
        assertEquals(BankOCR.ONE, BankOCR.nextDisplayDigit(lines));
    }
    
    @Test
    public void should_convert_to_0_when_zero_display_digit() {
        String display = " _ " +
                         "| |" +
                         "|_|";
        assertEquals(0, BankOCR.intFor(display));
    }
    
    @Test
    public void should_convert_to_1_when_one_display_digit() {
        String display = "   " +
                         "  |" +
                         "  |";
        assertEquals(1, BankOCR.intFor(display));
    }
    
    @Test
    public void should_convert_to_2_when_two_display_digit() {
        String display = " _ " +
                         " _|" +
                         "|_ ";
        assertEquals(2, BankOCR.intFor(display));
    }
    
    @Test
    public void should_convert_to_3_when_three_display_digit() {
        String display = " _ " +
                         " _|" +
                         " _|";
        assertEquals(3, BankOCR.intFor(display));
    }
    
    @Test
    public void should_convert_to_4_when_four_display_digit() {
        String display = "   " +
                         "|_|" +
                         "  |";
        assertEquals(4, BankOCR.intFor(display));
    }
    
    @Test
    public void should_convert_to_5_when_five_display_digit() {
        String display = " _ " +
                         "|_ " +
                         " _|";
        assertEquals(5, BankOCR.intFor(display));
    }
    
    @Test
    public void should_convert_to_6_when_six_display_digit() {
        String display = " _ " +
                         "|_ " +
                         "|_|";
        assertEquals(6, BankOCR.intFor(display));
    }
    
    @Test
    public void should_convert_to_7_when_seven_display_digit() {
        String display = " _ " +
                         "  |" +
                         "  |";
        assertEquals(7, BankOCR.intFor(display));
    }
    
    @Test
    public void should_convert_to_8_when_eight_display_digit() {
        String display = " _ " +
                         "|_|" +
                         "|_|";
        assertEquals(8, BankOCR.intFor(display));
    }
    
    @Test
    public void should_convert_to_9_when_nine_display_digit() {
        String display = " _ " +
                         "|_|" +
                         " _|";
        assertEquals(9, BankOCR.intFor(display));
    }
    
    private List<String> asList(String...lines) {
        return Arrays.asList(lines);
    }
    
}
