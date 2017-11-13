# Java 9 labs

Fooling around with Java 9

## Java Modules

In Java 9, code is organized into modules. A module is declared by a module-declaration file:

```
$ cat src/my.module/module-info.java
module Name {
	requires ...;
	exports ...;
	provides ...
		from ...;
	needs ...;
}
```

Types of modules:
- Normal modules (with module-info.class)
- Implicit modules (traditional jars on the module path)
	- Depends on everything
	- Exports everything
	- Name based on .jar-name
- Unnamed module(s) (classpath) (One unnamed module per ClassLoader)
	- Depends on everything
	- Exports nothing


```
Usage: javac <options> <source files>
where possible options include:
  @<filename>                  Read options and filenames from file
  -Akey[=value]                Options to pass to annotation processors
  --add-modules <module>(,<module>)*
        Root modules to resolve in addition to the initial modules, or all modules
        on the module path if <module> is ALL-MODULE-PATH.
  -d <directory>               Specify where to place generated class files
  --limit-modules <module>(,<module>)*
        Limit the universe of observable modules
  --module <module-name>, -m <module-name>
        Compile only the specified module, check timestamps
  --module-path <path>, -p <path>
        Specify where to find application modules
  --module-source-path <module-source-path>
        Specify where to find input source files for multiple modules
  --module-version <version>
        Specify version of modules that are being compiled
  --release <release>
        Compile for a specific VM version. Supported targets: 6, 7, 8, 9
  -s <directory>               Specify where to place generated source files
  --source-path <path>, -sourcepath <path>
        Specify where to find input source files
  --system <jdk>|none          Override location of system modules
  --upgrade-module-path <path>
        Override location of upgradeable modules
```

## Basic hello world Module

By convention, the source code for the module is in a directory that is the name of the module.

```
$ tree src/hello
src/hello
├── com
│   └── folkol
│       └── Hello.java
├── hello.iml
└── module-info.java

$ cat src/hello/module-info.java
module hello {

}

$ cat src/hello/com/folkol/Hello.java
package com.folkol;

public class Hello
{
    public static void main(String[] args) {
        System.out.println("hello, world!");
    }
}
```

```
$ javac -d mods/hello src/hello/module-info.java src/hello/com/folkol/Hello.java
```

```
$ java --module-path mods -m hello/com.folkol.Hello
hello, world!
```

## A more advanced hello world Module

N.b. Two modules does not seem to be able to export the same packages. Nor export empty packages.

```
$ javac -d mods/greeter src/greeter src/greeter/module-info.java src/greeter/com/folkol/greeter/Greeter.java

$ cat src/greeter/module-info.java
module greeter {
    exports com.folkol.greeter;
}

$ cat src/greeter/com/folkol/greeter/Greeter.java
package com.folkol.greeter;

public class Greeter
{
    public static String greet() {
        return "Hello, world!";
    }
}
```

```
$ javac -d mods/hello --module-path mods src/hello/module-info.java src/hello/com/folkol/hello/Hello.java

$ cat src/hello/module-info.java
module hello {
    requires greeter;
}

$ cat src/hello/com/folkol/hello/Hello.java
package com.folkol.hello;

import com.folkol.greeter.Greeter;

public class Hello
{
    public static void main(String[] args) {
        String greeting = Greeter.greet();
        System.out.println(greeting);
    }
}
```

```
$ java --module-path mods -m hello/com.folkol.hello.Hello
Hello, world!
```

## Hello world, Enterprise Edition

```

```

```
$ javac -d mods --module-source-path src/ $(find src -name "*.java")
```

## References

- http://blog.joda.org/2017/05/java-se-9-jpms-automatic-modules.html
- https://blog.codecentric.de/en/2015/12/first-steps-with-java9-jigsaw-part-2/
- http://openjdk.java.net/projects/jigsaw/quick-start

