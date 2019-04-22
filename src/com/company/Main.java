package com.company;
import java.io.IOException;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {

        Sinta sin = new Sinta();
        String file = new String("file.txt");
        sin.open(file);
        sin.o = sin.read();
        sin.startFunc = "ff";
        boolean b = false;
        while (sin.o.getType() != sin.sc.END && b == false)
        {
            b = sin.in();
        }
        //sin.tr.view(sin.tr.getRoot());
    }
}
