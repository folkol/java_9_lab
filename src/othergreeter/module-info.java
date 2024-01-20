module othergreeter {
    requires greeter;
    provides com.folkol.greeter.Greeter
        with com.folkol.othergreeter.OtherGreeterImpl;
}

