package ar.edu.itba.crypto.utils;

public class MyPair<P, Q> {

    public P key;
    public Q value;

    public MyPair(P p, Q q) {
        key = p;
        value = q;
    }

    public P getKey() {
        return key;
    }

    public Q getValue() {
        return value;
    }
}
