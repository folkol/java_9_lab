package com.folkol.hello;

import com.folkol.greeter.Greeter;

public class Hello
{
    public static void main(String[] args) {
        String greeting = Greeter.greet();
        System.out.println(greeting);
    }
}

