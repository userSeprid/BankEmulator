package com.bank.logic.controller.auxiliary;

import java.util.Scanner;

/**
 * Created by seprid on 15.07.17.
 */
public class ScannerStubImpl implements ScannerStub {

    Scanner stub;

    public ScannerStubImpl(){

        stub = new Scanner(System.in);

    }

    @Override
    public int nextInt() {
        return stub.nextInt();
    }

    @Override
    public String next() {
        return stub.next();
    }

    @Override
    public String nextLine() {
        return stub.nextLine();
    }

    @Override
    public double nextDouble() {
        return stub.nextDouble();
    }
}
