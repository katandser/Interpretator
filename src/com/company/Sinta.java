package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static com.company.Scan.*;
import static com.company.Scan.BLACK;
import static com.company.Scan.VAR;

class Sinta{
    private static final int ERTYPE = 0;
    private static final int ERID  = 1;
    private static final int ERDATE  = 2;
    private static final int ERENDVIR = 3;
    private static final int EREX = 4;
    private static final int EROPF = 5;
    private static final int ERFPAR = 6;
    private static final int ERCOMORDOTCOM = 7;
    private static final int ERVOID = 8;
    private static final int ERASS = 9;
    private static final int EREND = 10;
    boolean error = false;
    int last;
    String startFunc = "ff";
    ArrayList<Uno> arrayList;
    ArrayList <Integer> arrayType;
    int lastType;
    Uno o , uprO;
    Scan sc;
    Tree tr;
    Interpreter interpreter;
    Sinta() {
        interpreter = new Interpreter();
        sc = new Scan();
        tr = new Tree();
        arrayList = new ArrayList<Uno>();
        arrayType = new ArrayList<Integer>();
    }
    Sinta(String file) throws IOException{
        interpreter = new Interpreter();
        sc = new Scan();
        sc.open(file);
        tr = new Tree();
        arrayList = new ArrayList<Uno>();
        arrayType = new ArrayList<Integer>();
    }

    public void open(String file) throws IOException{
        sc.open(file);
    }

    Uno read() {
        return sc.read();
    }
    Uno uprRead() {
        int y = sc.i;
        Uno obl = read();
        sc.i = y;
        return obl;
    }


    boolean pref(){
        o = read();
        if (o.getType() == ID) {
            Uno un = tr.findVar(o);
            if (un == null) {
                System.out.println("ERROR--" + "unknow var: " + o.getName() + " string: " + o.getStr());
            }
            o = read();
            if (o.getType() == DOTCOM){
                o = read();
                return false;
            }
            return true;
        }
        return true;
    }

    boolean cheak (int type) {
            if (type == LONG || type == LONGLONG || type == SHORT || type == SHORTINT) {
                return false;
            }
            else {
                return true;
            }
    }


    int cheakType() {
        if (o.getType() == LONG) {
            o = read();
            if (o.getType() == LONG){
                o = read();
                return LONG;
            }
            return LONGLONG;
        }
        else if(o.getType() == SHORT) {
            o = read();
            if (o.getType() == INT){
                o = read();
                return SHORT;
            }
            return SHORTINT;
        }
        else {
            return ERROR;
        }
    }


    boolean in()
    {
        int type = cheakType();
        if (!cheak(type) && o.getType() == ID) {
            uprO = uprRead();
            if (uprO.getType() == OPEN_CIRCLE) {
                if (o.getName().equals(startFunc)) {
                    interpreter.flagInterpretation = true;
                }
                else {
                    interpreter.flagInterpretation = false;
                }
                return function(type);
            }
            else {
                return date(type);
            }
        }
        else {
            printERROR(ERTYPE);
            return true;
        }
    }

    boolean date(int type) {
        Uno un = tr.findVar(o);
        if (un == null) {
            tr.addLeft(o,VAR, type);
        }
        else {
            System.out.println("ERROR var '" + o.getName() +  "' is use in string: " + o.getStr());
        }
        if (peremen() == false) {
            if (o.getType() == COM) {
                o = read();
                if (o.getType() == ID) {
                    return date(type);
                }
                else {
                    printERROR(ERID);
                    return true;
                }
            }
            else if (o.getType() == DOTCOM) {
                o = read();
                return false;
            }
            else{
                printERROR(ERDATE);
                return  true;
            }
        }
        else {
            printERROR(EREX);
            return true;
        }
    }
    boolean  peremen() {
        uprO = uprRead();
        if (uprO.getType() == ASSIGN) {
            Uno result = o;
            o = read();
            o = read();
            return interViraj(result, ASSIGN);
        }
        else if (uprO.getType() == DOTCOM) {
            o = read();
            return false;
        }
        else if (uprO.getType() == COM) {
            o = read();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean function(int type) {

        if (tr.findFunc(o) == false) {
            tr.addLeft(o, FUNCTION, type);
        }
        else {
            System.out.println("ERROR func '" + o.getName() +  "' is use in string: " + o.getStr());
        }
        o = read();
        o = read();
        if (o.getType() == CLOSE_CIRCLE) {
            tr.getCurrent().i = sc.getPC();
            o = read();
            return body(true);
        }
        return spisPerem();
    }
    private boolean viraj() {

        boolean b = false;
        while (b == false) {
            b = viraj2();
            if (b == false) {
                if (o.getType() == EQ || o.getType() == NOTEQ) {
                    interpreter.addElement(o);
                    o = read();
                }
                else {
                    return b;
                }
            }
            else{
                return true;
            }
        }
        return true;
    }
    boolean viraj2() {
        boolean b = false;
        while (b == false) {
            b = viraj3();
            if (b == false) {
                if (o.getType() == EQLESS || o.getType() == EQGREAT || o.getType() == LESS || o.getType() == GREAT) {
                    interpreter.addElement(o);
                    o = read();
                }
                else {
                    return b;
                }
            }
            else{
                return true;
            }
        }
        return true;
    }
    boolean viraj3() {
        boolean b = false;
        while (b == false) {
            b = viraj4();
            if (b == false) {
                if (o.getType() == PLUS || o.getType() == MINUS) {
                    interpreter.addElement(o);
                    o = read();
                }
                else {
                    return b;
                }
            }
            else{
                return true;
            }
        }
        return true;
    }
    boolean viraj4() {
        boolean b = false;
        while (b == false) {
            b = viraj5();
            if (b == false) {
                if (o.getType() == SLASH || o.getType() == STAR || o.getType() == PROC) {
                    interpreter.addElement(o);
                    o = read();
                }
                else {
                    return b;
                }
            }
            else{
                return true;
            }
        }
        return false;
    }
    boolean viraj5() {
        while (o.getType() == PLUS || o.getType() == MINUS)
            o = read();
        return viraj6();
    }
    boolean viraj6() {
        boolean b = false;
        if (o.getType() == OPEN_CIRCLE) {
            interpreter.addElement(o);
            o = read();
            b = viraj();
            if (b == false) {
                if (o.getType() == CLOSE_CIRCLE) {
                    interpreter.addElement(o);
                    o = read();
                    return false;
                }
                else {
                    printERROR(ERID);
                    return true;
                }
            }
            else{
                return true;
            }
        }
        else if(o.getType() == CONSDEC || o.getType() == CONSHEX) {
            interpreter.addElement(o);
            o = read();
            return false;
        }
        else if(o.getType() == ID) {
            uprO = uprRead();
            Uno ob = o;
            if (uprO.getType() == OPEN_CIRCLE) {
                if (tr.findFunc(o) == false) {
                    System.out.println("ERROR--" + "unknow function: " + o.getName() + " string: " + o.getStr());
                }
                int count = 0;
                Node nd = tr.findFuncReNd(o);
                o = read();
                o = read();
                if (o.getType() == CLOSE_CIRCLE) {
                    return cheackArgsFunc(ob, count, nd);
                }
                else {
                    while (b == false)     {
                        count++;
                        b = viraj();
                        if (b == false)         {
                            if (o.getType() == CLOSE_CIRCLE)             {
                                return cheackArgsFunc(ob, count, nd);
                            }
                            else if (o.getType() == COM)             {
                                o = read();
                            }
                            else             {
                                printERROR(ERFPAR);
                                return true;
                            }
                        }
                    }
                }
            }
            else {
                Uno un = tr.findVar(o);
                if (un == null) {
                    System.out.println("ERROR--" + "unknow var: " + o.getName() + " string: " + o.getStr());
                    System.exit(1);
                }
                else {
                    interpreter.addElement(un);
                }
                if (uprO.getType() == PLUSPLUS){
                    interpreter.inc(un);
                    o = read();
                }
                if (uprO.getType() == MINUSMINUS){
                    interpreter.dec(un);
                    o = read();
                }
                o = read();
                return false;
            }
        }
        else if (o.getType() == PLUSPLUS) {
            o = read();
            if (o.getType() == ID) {
                Uno un = tr.findVar(o);
                if (un == null) {
                    System.out.println("ERROR--" + "unknow var: " + o.getName() + " string: " + o.getStr());
                    System.exit(1);
                }else {
                    if (un.getValue() == null) {
                        System.out.println("\nNon init var: " + un.getName());
                        System.exit(1);
                    }
                    else {
                        interpreter.inc(un);
                        interpreter.addElement(un);
                    }
                }
                o = read();

                return false;
            }
            else {
                return true;
            }
        }
        else if (o.getType() == MINUSMINUS) {
            o = read();
            if (o.getType() == ID) {
                Uno un = tr.findVar(o);
                if (un == null) {
                    System.out.println("ERROR--" + "unknow var: " + o.getName() + " string: " + o.getStr());
                    System.exit(1);
                }else {
                    if (un.getValue() == null) {
                        System.out.println("\nNon init var: " + un.getName());
                        System.exit(1);
                    }
                    else {
                        interpreter.dec(un);
                        interpreter.addElement(un);
                    }
                }
                o = read();
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return true;
        }
        return true;
    }

    private boolean cheackArgsFunc(Uno ob, int count, Node nd) {
        //o = read();
        if (nd.getListType().size() != count) {
            System.out.println("ER: func " + ob.getName() + " have: " + count + " args, need " + nd.getListType().size() + " in string " + ob.getStr());
        }
        return false;
    }

    boolean spisPerem() {
        int type = cheakType();
        if (cheak(type) == false) {
            if (o.getType() == ID) {
                arrayList.add(o);
                arrayType.add(type);
                o = read();
                if (o.getType() == COM) {
                    o = read();
                    return spisPerem();
                }
                else if (o.getType() == CLOSE_CIRCLE) {
                    tr.getCurrent().i = sc.getPC();
                    o = read();
                    boolean b = body(true);
                }
                else {
                    printERROR(ERFPAR);
                    return true;
                }
            }
            else{
                printERROR(ERTYPE);
                return true;
            }
        }
        else {
            printERROR(ERTYPE);
            return true;
        }
        return false;
    }
    boolean body(boolean bool) {
        boolean b = false;
        if (o.getType() == OPEN_F) {
            for (int i = 0; i < arrayList.size(); i++) {
                tr.getCurrent().addElem(arrayType.get(i));
                tr.getCurrent().addElUno(arrayList.get(i));//arrayList.get(i).getType());
            }
            //tr.getCurrent().setListType(arrayList.listIterator().next());
            if (bool == true) {
                tr.addRight(o,BLACK,BLACK);
                while (arrayList.size() > 0) {
                    tr.addLeft(arrayList.get(0),VAR, arrayType.get(0));
                    arrayList.remove(0);
                    arrayType.remove(0);
                }
            }
            o = read();
            while(b == false) {
                int type = cheakType();
                if (cheak(type) == false) {
                    b = date(type);
                }
                else {
                    b = oper();
                }
                if (o.getType() == CLOSE_F) {
                    tr.up();
                    o = read();
                    break;
                }
            }
            if (b == false) {
                return false;
            }
            else{
                return true;
            }
        }
        else {
            printERROR(EROPF);
            return true;
        }
    }
    boolean oper() {
        boolean b = false;
        if (o.getType() == OPEN_F) {
            return body(true);
        }
        else if (o.getType() == DOTCOM) {
            o = read();
            return false;
        }
        else if (o.getType() == PRINT || o.getType() == PRINTLN) {
            Uno pr = o;
            o = read();
            if (o.getType() != OPEN_CIRCLE) {
                return true;
            }
            o = read();
            if (o.getType() == CLOSE_CIRCLE) {
                if (interpreter.flagInterpretation == true) {
                    if (pr.getType() == PRINT) {
                        System.out.print(" ");
                    }
                    else {
                        System.out.println();
                    }
                }
            }
            else {
                Uno un = new Uno();
                interViraj(un);
                if (interpreter.flagInterpretation == true) {
                    if (pr.getType() == PRINT) {
                        System.out.print(un.getValue());
                    }
                    else {
                        System.out.println(un.getValue());
                    }
                }
            }
            o = read();
            o = read();
            return false;
        }
        else if(o.getType() == FOR) {
            o = read();
            return funcFor();
        }
        else if (o.getType() == ID) {
            Uno ob = o;
            uprO = uprRead();
            if (uprO.getType() == OPEN_CIRCLE) {
                Node nd = tr.findFuncReNd(o);
                if (nd == null) {
                    System.out.println("ERROR--" + "unknow function: " + o.getName() + " string: " + o.getStr());
                }
                o = read();
                uprO = uprRead();
                int count = 0;
                if (uprO.getType() == CLOSE_CIRCLE) {
                    return cheackArgsFunc(ob, count, nd);
                }
                else {
                    o = read();
                    for (int i = 0; b == false; i++) {
                        count++;
                        b = interViraj(nd.getElUno(i));
                        if (b == false) {
                            if (o.getType() == CLOSE_CIRCLE) {
                                o = read();
                                if (o.getType() == DOTCOM) {
                                    boolean ch = cheackArgsFunc(ob, count, nd);
                                    if (interpreter.flagInterpretation) {
                                        interpreter.pushPC(sc.getPC());
                                        sc.setPC(nd.i);
                                        o = read();
                                        Node save = tr.getCurrent();
                                        tr.setCurrent(nd);
                                        tr.setCurrent(tr.getCurrent().getRight());
                                        for (int j = 0; j < count; j++ ) {
                                            tr.setCurrent(tr.getCurrent().getLeft());
                                        }
                                        if (interpreter.stackPC.size() < 30)
                                        body(false);
                                        tr.setCurrent(save);
                                        sc.setPC(interpreter.pullPC());
                                        interpreter.flagInterpretation = true;
                                    }
                                    o = read();
                                    return ch;
                                }
                                else {
                                    printERROR(ERENDVIR);
                                    return true;
                                }
                            }
                            else if (o.getType() == COM) {
                                o = read();
                            }
                            else {
                                printERROR(ERCOMORDOTCOM);
                                return true;
                            }
                        }
                    }
                }
            }
            else {
                b = assign();
                if (b == false) {

                    if (o.getType() == DOTCOM)     {
                        o = read();
                        return false;
                    }
                    else     {
                        printERROR(EREND);
                        return true;
                    }
                }
                else {
                    return true;
                }
            }
        }
        else if(o.getType() == RETURN) {
            o = read();
            List<Uno> output = new LinkedList<>();
            interpreter.addLevel(output);
            b = viraj();
            if (interpreter.flagInterpretation) {
                Object ob = Interpreter.vir(interpreter.stackLevelInterpretation.peek());
            }
            //.setValue(ob);
            //System.out.println(ob);
            interpreter.removeLevel();

            if (b == false) {
                if (o.getType() == DOTCOM) {
                    o = read();
                    interpreter.flagInterpretation = false;
                    return false;
                }
                else {
                    printERROR(ERENDVIR);
                    return true;
                }
            }
            else{
                printERROR(ERVOID);
                return true;
            }
        }
        else if (o.getType() == PLUSPLUS) {
            return pref();
        }
        else if (o.getType() == MINUSMINUS) {
            return pref();
        }
        else {
            return true;
        }
        return false;
    }

    boolean funcFor() {
        boolean b;
        if (o.getType() == OPEN_CIRCLE) {
            o = read();
            b = assign();
            int prefFunc, postFunc;
            if (o.getType() == DOTCOM && b == false) {
                prefFunc = sc.getPC();
                o = read();
            }
            else {
                return true;
            }
            List<Uno> output = new LinkedList<>();
            interpreter.addLevel(output);
            b = viraj();
            interpreter.removeLevel();
            if (o.getType() == DOTCOM && b == false) {
                postFunc = sc.getPC();
                o = read();
            }
            else {
                return true;
            }

            if (interpreter.flagInterpretation == false) {
                b = assign();
                o = read();
                return body(true);
            }
            else if (b == false) {
                interpreter.flagInterpretation = false;
                b = assign();
                interpreter.flagInterpretation = true;
                int body = sc.getPC();
                o = read();
                //interpreter.pushPC(sc.getPC());
                while (true) {
                    sc.i = prefFunc;
                    o = read();
                    output = new LinkedList<>();
                    interpreter.addLevel(output);
                    b = viraj();
                    Object ob = Interpreter.vir(interpreter.stackLevelInterpretation.peek());
                    interpreter.removeLevel();
                    sc.setPC(body);
                    if ((int)ob != 0) {
                        o = read();
                        b = body(true);
                        int current = sc.getPC();
                        sc.i = postFunc;
                        o = read();
                        assign();
                        sc.i = current;
                    }
                    else {
                        interpreter.flagInterpretation = false;
                        o = read();
                        body(true);
                        interpreter.flagInterpretation = true;
                        return false;
                    }
                }
            }
            else {
                return true;
            }
        }
        else {
            return true;
        }
    }
    boolean assign() {
        if (o.getType() == ID) {
            Uno un = tr.findVar(o);
            if (un == null) {
                System.out.println("ERROR--" + "unknow var: " + o.getName() + " string: " + o.getStr());
            }
            //Uno result = o;
            o = read();
            int type = o.getType();
            if (type == PLUSASS || type == MINUSASS || type == STARASS || type == PROCASS || type == SLASHASS || type == ASSIGN) {
                o = read();
                return interViraj(un, type);
            }
            else {
                printERROR(ERASS);
                return true;
            }
        }
        else {
            printERROR(ERID);
            return true;
        }
    }

    private boolean interViraj(Uno un, int type) {
        //o = read();
        List<Uno> output = new LinkedList<>();
        interpreter.addLevel(output);
        boolean b = viraj();
        if (interpreter.flagInterpretation ) {
            Object ob = Interpreter.vir(interpreter.stackLevelInterpretation.peek());
            un.setValue(ob,type);
        }
        interpreter.removeLevel();
        return b;
    }

    private boolean interViraj(Uno un) {
        //o = read();
        List<Uno> output = new LinkedList<>();
        interpreter.addLevel(output);
        boolean b = viraj();
        if (interpreter.flagInterpretation ) {
            Object ob = Interpreter.vir(interpreter.stackLevelInterpretation.peek());
            un.setValue(ob);
        }
        interpreter.removeLevel();
        return b;
    }



    void printERROR(int er) {
        if (!error) {
            error = true;
            switch (er)
            {
                case ERTYPE : System.out.println("Expected an type" + "   ERROR in string: " + o.getName() + " in string: " + o.getStr()); break;
                case ERID : System.out.print("Expected an identifier" + "   ERROR in string: " + o.getName() + " in string: " + o.getStr()); break;
                case ERENDVIR : System.out.print("ff" + "   ERROR in string: " + o.getName() + " in string: " + o.getStr()); break;
                case EREX : System.out.print("ERROR expretion" + "   ERROR in string: " + o.getName() + " in string: " + o.getStr()); break;
                case ERDATE : System.out.print("dd" + "   ERROR in string: " + o.getName() + " in string: " + o.getStr()); break;
                case ERFPAR : System.out.print("Expected an ',' or ')'" + "   ERROR in string: " + o.getName() + " in string: " + o.getStr()); break;
                case EROPF : System.out.print("Expected an '{'" + "   ERROR in string: " + o.getName() + " in string: " + o.getStr()); break;
                case ERCOMORDOTCOM : System.out.print("Expected an ';' or ','" + "   ERROR in string: " + o.getName() + " in string: " + o.getStr()); break;
                case ERASS : System.out.print("Expected an assign" + "   ERROR in string: " + o.getName() + " in string: " + o.getStr()); break;
                case ERVOID : System.out.print("Return void" + "   ERROR in string: " + o.getName() + " in string: " + o.getStr()); break;
                case EREND : System.out.print("Expected an ';'" + "   ERROR in string: " + o.getName() + " in string: " + o.getStr()); break;
                default: break;
            }
            last = sc.i;
        }

    }
}
