package org.acme;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FizzBuzzTest {

    /**
     * For example, a typical round of fizz buzz would start as follows:
     * 1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, Fizz Buzz, 16,
     * 17, Fizz, 19, Buzz, Fizz, 22, 23, Fizz, Buzz, 26, Fizz, 28, 29, Fizz Buzz,
     * 31, 32, Fizz, 34, Buzz, Fizz, ...
     */
    @Test
    public void when1shouldReturn1() {
        assertEquals("1", FizzBuzz.compute(1));
    }

    /**
     * For example, a typical round of fizz buzz would start as follows:
     * 1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, Fizz Buzz, 16,
     * 17, Fizz, 19, Buzz, Fizz, 22, 23, Fizz, Buzz, 26, Fizz, 28, 29, Fizz Buzz,
     * 31, 32, Fizz, 34, Buzz, Fizz, ...
     */
    @Test
    public void when2shouldReturn2() {
        assertEquals("2", FizzBuzz.compute(2));
    }
}
