package com.company;
import java.io.IOException;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {

        Sinta sin = new Sinta();
        String file = new String("file.txt");
        sin.open(file);
        sin.o = sin.read();
        boolean b = false;
        while (sin.o.getType() != sin.sc.END && b == false)
        {
            b = sin.in();
            if (sin.o.getType() == sin.sc.END)
            {
                sin.last = sin.sc.i;
                System.out.println("win");
            }
        }
        sin.tr.view(sin.tr.getRoot());
    }
}
