package com.folkol.hello;

import com.folkol.greeter.Greeter;
import java.util.ServiceLoader;

public class Hello
{
    public static void main(String[] args) {
        for(Greeter greeter : ServiceLoader.load(Greeter.class)) {
            System.out.println(greeter.greet());
        }
    }
}

