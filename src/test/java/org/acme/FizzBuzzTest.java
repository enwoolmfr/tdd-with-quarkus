package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FizzBuzzTest {

    /**
     * For example, a typical round of fizz buzz would start as follows:
     * 1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, Fizz Buzz, 16,
     * 17, Fizz, 19, Buzz, Fizz, 22, 23, Fizz, Buzz, 26, Fizz, 28, 29, Fizz Buzz,
     * 31, 32, Fizz, 34, Buzz, Fizz, ...
     */
    @Test
    public void shouldReturn3WhenInputIs1() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        assertEquals("1", fizzBuzz.compute(1));
    }

    @Test
    public void shouldReturn2WhenInputIs2() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        assertEquals("2", fizzBuzz.compute(2));
    }

    @Test
    public void shouldReturnFizzWhenInputIs3() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        assertEquals("Fizz", fizzBuzz.compute(3));
    }

    @ParameterizedTest
    @CsvSource({"3,Fizz","6,Fizz","9,Fizz"})
    public void shouldReturnFizzWhenDivisibleBy3(int value,String expected) {
        FizzBuzz fizzBuzz = new FizzBuzz();
        assertEquals(expected, fizzBuzz.compute(value));
    }

     @Test
    public void shouldReturnBuzzWhenInputIs5() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        assertEquals("Buzz", fizzBuzz.compute(5));
    }

    @ParameterizedTest
    @CsvSource({"5,Buzz","10,Buzz","20,Buzz"})
    @DisplayName("Conversion FizzBuzz - input: {0}, attendu: {1}")
    public void shouldReturnBuzzWhenDivisibleBy5(int value,String expected) {
        FizzBuzz fizzBuzz = new FizzBuzz();
        assertEquals(expected, fizzBuzz.compute(value));
    }

    @Test
    public void shouldReturnFizzBuzzWhenInputIs15() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        assertEquals("Fizz Buzz", fizzBuzz.compute(15));
    }
}
