package org.springframework.chapter21;

public class A {

    static {
        System.out.println("class A init");
    }

    public static final String hello = "class";

    public static String hello() {
        System.out.println("static method hello ....");
        return "hello method";
    }


    static class B {
        public static void main(String[] args) {
            System.out.println(A.hello);
        }
    }

    static class C extends A {
        static {
            System.out.println("class C init");
        }
    }
}
