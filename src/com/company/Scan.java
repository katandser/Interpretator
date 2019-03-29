package com.company;

import java.io.IOException;
import java.io.*;
import java.util.Scanner;

import static com.company.Scan.*;

class Uno {
    private String name;
    private int str;
    private int type;
    private Object value;
    public void setName(String name) {
            this.name = name;
    }
    public void setStr(int str) {
            this.str = str;
    }
    public void setType(int type) {
            this.type = type;
    }
    public int getStr() {
            return str + 1;
    }
    public int getType() {
            return type;
    }
    public String getName() {
            return name;
    }
    public String toString() {
            return name + " : " + stringType(type) + " : " +  str + 1 + " : " + value;
    }

    public void setValue(Object value) {
        if (value instanceof Integer) {
            this.value = value;
        }
        if (value instanceof Short) {
            this.value = value;
        }
        if (value instanceof Long) {
            this.value = value;
        }
    }
    public void setValue(Object value, int type) {

        if (this.value == null && type != ASSIGN) {
            System.out.println("\nValue not anable" + name  +" in string: " + str);
            System.exit(1);
        }
        if (type == ASSIGN) {
            if (value instanceof Integer) {
                this.value = value;
            }
            if (value instanceof Short) {
                this.value = value;
            }
            if (value instanceof Long) {
                this.value = value;
            }
        }
        else if(type == PLUSASS) {
            if (this.value instanceof Long) {
                this.value = (long)this.value + (long)value;
            }
            else if (this.value instanceof Integer) {
                this.value = (int)this.value + (int)value;
            }
            else {
                this.value = (short)this.value + (short)value;
            }

        }
        else if(type == MINUSASS) {
            if (this.value instanceof Long) {
                this.value = (long)this.value - (long)value;
            }
            else if (this.value instanceof Integer) {
                this.value = (int)this.value - (int)value;
            }
            else {
                this.value = (short)this.value - (short)value;
            }
        }
        else if(type == STARASS) {
            if (this.value instanceof Long) {
                this.value = (long)this.value * (long)value;
            }
            else if (this.value instanceof Integer) {
                this.value = (int)this.value * (int)value;
            }
            else {
                this.value = (short)this.value * (short)value;
            }
        }
        else if(type == PROCASS) {
            if ((int)value == 0) {
                System.out.println("\nДеление на 0" + name  +" in string: " + str);
                System.exit(1);
            }
            if (this.value instanceof Long) {
                this.value = (long)this.value % (long)value;
            }
            else if (this.value instanceof Integer) {
                this.value = (int)this.value % (int)value;
            }
            else {
                this.value = (short)this.value % (short)value;
            }

        }
        else if(type == SLASHASS) {
            if ((int)value == 0) {
                System.out.println("\nДеление на 0" + name  +" in string: " + str);
                System.exit(1);
            }
            if (this.value instanceof Long) {
                this.value = (long)this.value / (long)value;
            }
            else if (this.value instanceof Integer) {
                this.value = (int)this.value / (int)value;
            }
            else {
                this.value = (short)this.value / (short)value;
            }
        }
        else {
            System.exit(1);
        }


    }

    public Object getValue() {
        return value;
    }

    public static String stringType(int a) {
        switch (a)
        {
            case Scan.LONG : return "LONG";
            case Scan.SHORT : return "SHORT";
            case Scan.INT : return "INT";
            case Scan.FOR : return "FOR";
            case Scan.PLUS : return "PLUS";
            case Scan.MINUS : return "MINUS";
            case Scan.SLASH : return "SLASH";
            case Scan.STAR : return "STAR";
            case Scan.PROC : return "PROC";
            case Scan.LESS : return "LESS";
            case Scan.GREAT : return "GREAT";
            case Scan.EQLESS : return "EQLESS";
            case Scan.EQGREAT : return "EQGREAT";
            case Scan.EQ : return "EQ";
            case Scan.NOTEQ : return "NOTEQ";
            case Scan.DOTCOM : return "DOTCOM";
            case Scan.OPEN_CIRCLE : return "OPEN_CIRCLE";
            case Scan.CLOSE_CIRCLE : return "CLOSE_CIRCLE";
            case Scan.OPEN_F : return "OPEN_F";
            case Scan.CLOSE_F : return "CLOSE_F";
            case ASSIGN : return "ASSIGN";
            case Scan.PLUSASS : return "PLUSASS";
            case Scan.MINUSASS : return "MINUSASS";
            case Scan.STARASS : return "STARASS";
            case Scan.SLASHASS : return "SLASHASS";
            case Scan.PROCASS : return "PROCASS";
            case Scan.PLUSPLUS : return "PLUSPLUS";
            case Scan.MINUSMINUS : return "MINUSMINUS";
            case Scan.ID : return "ID";
            case Scan.ERROR : return "ERROR";
            case Scan.END : return "END";
            case Scan.CONSHEX : return "CONSHEX";
            case Scan.CONSDEC : return "CONSDEC";
            case Scan.COM : return "COM";
            case Scan.RETURN : return "RETURN";
            case Scan.BLACK : return "BLACK";
            case Scan.LONGLONG : return "LONGLONG";
            case Scan.SHORTINT : return "SHORTINT";
            case Scan.VAR : return "VAR";
            case Scan.FUNCTION : return "FUNCTION";
            default: return "WOW";
        }

    }
    Uno(){}
    Uno(int type) {
        setType(type);
    }
    Uno(String name) {
        setName(name);
        setType(Scan.CONSDEC);
        if (type == Scan.CONSDEC) {
            setValue(Integer.valueOf(name));
        }
    }
    Uno(String name, int type, int str) {
            setName(name);
            setType(type);
            setStr(str);
            if (type == Scan.CONSDEC) {
                setValue(Integer.valueOf(name));
            }
    }
}

public class Scan {
    static final int LONG = 1;
    static final int SHORT = 2;
    static final int INT = 3;
    static final int FOR = 4;
    static final int PLUS = 5;
    static final int MINUS = 6;
    static final int SLASH = 7;
    static final int STAR = 8;
    static final int PROC = 9;
    static final int LESS = 10;
    static final int GREAT = 11;
    static final int EQLESS = 12;
    static final int EQGREAT = 13;
    static final int EQ = 14;
    static final int NOTEQ = 15;
    static final int DOTCOM = 16;
    static final int OPEN_CIRCLE = 17;
    static final int CLOSE_CIRCLE = 18;
    static final int OPEN_F = 19;
    static final int CLOSE_F = 20;
    static final int ASSIGN = 21;
    static final int PLUSASS = 22;
    static final int MINUSASS = 23;
    static final int STARASS = 24;
    static final int SLASHASS = 25;
    static final int PROCASS = 26;
    static final int PLUSPLUS = 27;
    static final int MINUSMINUS = 28;
    static final int ID = 29;
    static final int ERROR = 30;
    static final int END = 31;
    static final int CONSHEX = 32;
    static final int CONSDEC = 33;
    static final int COM = 34;
    static final int RETURN = 35;
    static final int BLACK = 36;
    static final int LONGLONG = 37;
    static final int SHORTINT = 38;
    static final int VAR = 39;
    static final int FUNCTION = 40;
    public String s;
    public int i;
    int str;
    Scan(){i = 0;}

    public void setPC(int pc) {
        i = pc;
    }
    public int getPC(){
        return i;
    }



    public void incStr() {
        if (s.charAt(i) == '\n'){
            str++;
        }
        i++;
    }
    private int [] ent;
    public void open(String file) throws IOException {
        try {
            s = new Scanner(new File(file)).useDelimiter("\\Z").next();
            s += '#';
        }
        catch(IOException e) {}
    }
    void skip() {
        while (s.charAt(i) != '\n' && s.charAt(i) != '#') {
            incStr();
        }
    }
    Boolean autoID() {
        Boolean b = true;
        while ((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= '0' && s.charAt(i) <= '9') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') || s.charAt(i) == '_') {
            incStr();
        }
        return b;
    }
    Boolean autoDec() {
        while ((s.charAt(i) >= '0' && s.charAt(i) <= '9')) {
            incStr();
        }
        return true;
    }
    Boolean autoHex() {
        while ((s.charAt(i) >= '0' && s.charAt(i) <= '9') || (s.charAt(i) >= 'a' && s.charAt(i) <= 'f') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'F')) {
            incStr();
        }
        return true;
    }
    Uno autoNum() {
        if (s.charAt(i) == '0' && s.charAt(i + 1) == 'x' && ((s.charAt(i + 2) >= '0' && s.charAt(i + 2) <= '9') || (s.charAt(i + 2) >= 'a' && s.charAt(i + 2) <= 'f') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'F'))) {
            incStr();
            incStr();
            int u = i;
            if (autoHex()) {
                return chaeckLenght(u, CONSHEX);
            }
            else {
                autoID();
                return new Uno(s.substring(u,i),ERROR,str);
            }
        }
        else if((s.charAt(i) == '0' && s.charAt(i + 1) == 'x') || (s.charAt(i) == '0' && s.charAt(i + 1) == 'x')) {
            int u = i;
            incStr();
            incStr();
            return new Uno(s.substring(u,i),ERROR,str);
        }
        else {
            int u = i;
            if (autoDec()) {
                return chaeckLenght(u, CONSDEC);
            }
            else {
                autoID();
                return new Uno(s.substring(u,i),ERROR,str);
            }
        }
    }

    private Uno chaeckLenght(int u, int conshex) {
        if (u + 21 >= i) {
            return new Uno(s.substring(u,i), conshex,str);
        }
        else {
            return new Uno(s.substring(u,i),ERROR,str);
        }
    }

    Uno read() {
        while (s.charAt(i) == ' ' || s.charAt(i) == '\n' || s.charAt(i) == '\t') {
            incStr();
        }
        if ((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')) {
            int u = i;
            if (autoID()) {
                if (s.substring(u,i).equals("long"))
                    return new Uno(s.substring(u,i),LONG,str);
                if (s.substring(u,i).equals("short"))
                    return new Uno(s.substring(u,i),SHORT,str);
                if (s.substring(u,i).equals("int"))
                    return new Uno(s.substring(u,i),INT,str);
                if (s.substring(u,i).equals("for"))
                    return new Uno(s.substring(u,i),FOR,str);
                if (s.substring(u,i).equals("return"))
                    return new Uno(s.substring(u,i),RETURN,str);
                return new Uno(s.substring(u,i),ID,str);
            }
            else {
                return new Uno(s.substring(u,i),ERROR,str);
            }
        }
        else if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
            return autoNum();
        }
        else if (s.charAt(i) == '*') {
            return newUno(STARASS, STAR);
        }
        else if (s.charAt(i) == '%') {
            return newUno(PROCASS, PROC);
        }
        else if (s.charAt(i) == '=') {
            return newUno(EQ, ASSIGN);
        }
        else if (s.charAt(i) == '<') {
            return newUno(EQLESS, LESS);
        }
        else if (s.charAt(i) == '>') {
            return newUno(EQGREAT, GREAT);
        }
        else if (s.charAt(i) == '-') {
            if (s.charAt(i + 1) == '=') {
                incStr();
                return new Uno(s.substring(i - 2,i),MINUSASS,str);
            }
            else if (s.charAt(i + 1) == '-') {
                incStr();
                incStr();
                return new Uno(s.substring(i - 2,i),MINUSMINUS,str);
            }
            else {
                incStr();
                return new Uno(s.substring(i - 1,i),MINUS,str);
            }
        }
        else if (s.charAt(i) == '+') {
            if (s.charAt(i + 1) == '=') {
                incStr();
                incStr();
                return new Uno(s.substring(i - 2,i),PLUSASS,str);
            }
            else if (s.charAt(i + 1) == '+') {
                incStr();
                incStr();
                return new Uno(s.substring(i - 2,i),PLUSPLUS,str);
            }
            else {
                incStr();
                return new Uno(s.substring(i - 1,i),PLUS,str);
            }
        }
        else if (s.charAt(i) == '/') {
            if (s.charAt(i + 1) == '/') {
                skip();
                return read();
            }
            else return newUno(SLASHASS, SLASH);
        }
        else if (s.charAt(i) == '!') {
            return newUno(NOTEQ, ERROR);
        }
        else if (s.charAt(i) == '(') {
            incStr();
            return new Uno(s.substring(i - 1,i),OPEN_CIRCLE,str);
        }
        else if (s.charAt(i) == ';') {
            incStr();
            return new Uno(s.substring(i - 1,i),DOTCOM,str);
        }
        else if (s.charAt(i) == ',') {
            incStr();
            return new Uno(s.substring(i - 1,i),COM,str);
        }
        else if (s.charAt(i) == ')') {
            incStr();
            return new Uno(s.substring(i - 1,i),CLOSE_CIRCLE,str);
        }
        else if (s.charAt(i) == '{') {
            incStr();
            return new Uno(s.substring(i - 1,i),OPEN_F,str);
        }
        else if (s.charAt(i) == '}') {
            incStr();
            return new Uno(s.substring(i - 1,i),CLOSE_F,str);
        }
        else if (s.charAt(i) == '#') {
            return new Uno(s.substring(i,i + 1),END,str);
        }
        else {
            incStr();
            return new Uno(s.substring(i - 1,i),ERROR,str);
        }
    }

    private Uno newUno(int eqgreat, int great) {
        if (s.charAt(i + 1) == '=') {
            incStr();
            incStr();
            return new Uno(s.substring(i - 2,i), eqgreat,str);
        }
        else {
            incStr();
            return new Uno(s.substring(i - 1,i), great,str);
        }
    }
}