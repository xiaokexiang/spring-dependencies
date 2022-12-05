package org.springframework.chapter21;

public class S {

    private S() {
    }

    private S s;

    public S getInstance() {
        if (null == s) {
            s = A.s;
        }
        return s;
    }

    private static class A {
        private static final S s = new S();
    }

}
