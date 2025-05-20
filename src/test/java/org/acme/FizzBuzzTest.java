package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FizzBuzzTest {

    /**
     * For example, a typical round of fizz buzz would start as follows:
     * 1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, Fizz Buzz, 16,
     * 17, Fizz, 19, Buzz, Fizz, 22, 23, Fizz, Buzz, 26, Fizz, 28, 29, Fizz Buzz,
     * 31, 32, Fizz, 34, Buzz, Fizz, ...
     */
    @Test
    public void when_1_should_Return_1() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        assertEquals("1", fizzBuzz.compute(1));
    }


    @Test
    public void when_2_should_Return_2() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        assertEquals("2", fizzBuzz.compute(2));
    }
}
