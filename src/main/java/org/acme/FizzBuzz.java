package org.acme;

public class FizzBuzz {

    public String compute(int value){
        if(value%3==0 && value%5==0){
            return "Fizz Buzz";
        }else if(value%5==0){
            return "Buzz";
        }else if(value%3==0){
            return "Fizz";
        }else{
            return String.valueOf(value);
        }
    } 
}
