package org.acme;

public class FizzBuzz {

    public String compute(int value){
        if(value%5==0){
            return "Buzz";
        }

        if(value%3==0){
            return "Fizz";
        }
        return String.valueOf(value);
    } 
}
