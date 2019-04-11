package com.company;

//import com.sun.source.util.Plugin;

import java.util.*;

import static com.company.Scan.*;
public class Interpreter {
    private static final int SYMBOL = 0;
    private static final int POST = 1;
    private static final int OPEN = 2;
    private static final int CLOSE = 3;
    private static final int PREF = 4;
    private static final int OPER_LVL_0 = 5;
    private static final int OPER_LVL_1 = 6;
    private static final int OPER_LVL_2 = 7;
    private static final int OPER_LVL_3 = 8;
    private static final int OPER_LVL_4 = 9;
    public boolean flagInterpretation = true;

    Queue<Integer> stackPC = Collections.asLifoQueue(new ArrayDeque<>());

    Queue <List <Uno> > stackLevelInterpretation = Collections.asLifoQueue(new ArrayDeque<>());
    void addLevel(List <Uno> list) {
        if (flagInterpretation == true) {
            stackLevelInterpretation.add(list);
        }
    }
    void removeLevel() {
        if (flagInterpretation == true) {
            stackLevelInterpretation.remove();
        }
    }


    public void pushPC(int i) {
        if (flagInterpretation == true) {
            stackPC.add(i);
        }
    }
    public int pullPC() {
            return stackPC.remove();
    }
    public int getPeek() {
            return stackPC.peek();
    }


    public void addElement(Uno o) {
        if (flagInterpretation == true) {
            if (o.getValue() == null && o.getType() == ID) {
                System.out.println("\nNon init var: " + o.getName() + " in string: " + o.getStr());
                System.exit(1);
            }
            Uno un = new Uno();
            un.setValue(o.getValue());
            un.setType(o.getType());
            un.setName(o.getName());
            un.setStr(o.getStr());
            stackLevelInterpretation.peek().add(un);
        }
    }
    public void inc(Uno un) {
        if (flagInterpretation == true) {
            Object ob = un.getValue();
            if (ob instanceof Long) {
                un.setValue((Long) ob + 1);
            } else if (ob instanceof Integer) {
                un.setValue((Integer) ob + 1);
            } else {
                un.setValue((Short) ob + 1);
            }
        }
    }
    public void dec(Uno un) {
        if (flagInterpretation == true) {
            Object ob = un.getValue();
            if (ob instanceof Long) {
                un.setValue((Long) ob - 1);
            } else if (ob instanceof Integer) {
                un.setValue((Integer) ob - 1);
            } else {
                un.setValue((Short) ob - 1);
            }
        }
    }

    static private int check(int lex) {
        if (lex == CONSDEC || lex == CONSHEX || lex == ID) {
            return SYMBOL;
        }
        else if (lex == OPEN_CIRCLE) {
            return OPEN;
        }
        else if (lex == CLOSE_CIRCLE) {
            return CLOSE;
        }
        else if (lex == STAR || lex == PROC || lex == SLASH){
            return OPER_LVL_0;
        }
        else if (lex == PLUS || lex == MINUS) {
            return OPER_LVL_1;
        }
        else if (lex == LESS || lex == GREAT || lex == EQGREAT || lex == EQLESS) {
            return OPER_LVL_2;
        }
        else if (lex == EQ || lex == NOTEQ) {
            return OPER_LVL_3;
        }
        else {
            return 100;
        }
    }

    static Object vir(List <Uno> input) {
        //List <Uno> input = new LinkedList<>();
        List <Uno> output = new LinkedList<>();
        Queue<Uno> stack = Collections.asLifoQueue(new ArrayDeque<>());

        Uno d;
        for (Uno i : input) {
            int type = check(i.getType());

            if (type == SYMBOL || type == POST){
                output.add(i);
            }
            else if (type == PREF) {
                stack.add(i);
            }
            else if (type == OPEN) {
                stack.add(i);
            }
            else if (type == CLOSE) {
                do {
                    d = stack.remove();
                    if (d.getType() == OPEN_CIRCLE){
                        break;
                    }
                    else {
                        output.add(d);
                    }
                } while (d != null);
            }
            else if (type >= OPER_LVL_0 && type <= OPER_LVL_3) {
                if (stack.peek() != null) {
                    int oper;
                    oper = check(stack.peek().getType());
                    while (oper >= PREF && type >= oper) {
                        output.add(stack.remove());
                        if (stack.peek() != null) {
                            oper = check(stack.peek().getType());
                        }
                        else {
                            break;
                        }
                    }
                }
                stack.add(i);
            }
        }
        while (stack.peek() != null) {
            output.add(stack.remove());
        }
        return calculation(output);
    }


    private static Object binaryOperDis(Object in1, Object in2, int oper) {
        if (in1 == null || in2 == null) {
            System.out.println("\nNon initialization param");
            System.exit(1);
        }
        if (in1 instanceof Long || in2 instanceof Long) {
            return (long) binaryOper((long)in1 , (long)in2 ,oper );
        }
        else if (in1 instanceof Integer || in2 instanceof Integer) {
            return (int) binaryOper((int)in2, (int)in1, oper);
        }
        else {
            return (short) binaryOper((short)in1 , (short)in2 ,oper );
        }
    }
    private static int binaryOper(int in1, int in2, int oper) throws ArithmeticException {
        int result = 0;
        if (oper == PLUS) {
            result = in1 + in2;
        }
        else if (oper == MINUS) {
            result = in1 - in2;
        }
        else if (oper == STAR) {
            result = in1 * in2;
        }
        else if (oper == PROC) {
            try {
                result = in1 % in2;
            } catch (ArithmeticException ae) {
                System.out.println("\nДеление на 0");
                System.exit(1);
            }
        }
        else if (oper == SLASH) {
            try {
                result = in1 / in2;
            } catch (ArithmeticException ae) {
                System.out.println("\nДеление на 0");
                System.exit(1);
            }
        }
        else if (oper == EQ) {
            if (in1 == in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == NOTEQ) {
            if (in1 != in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == LESS) {
            if (in1 < in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == EQLESS) {
            if (in1 <= in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == GREAT) {
            if (in1 > in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == EQGREAT) {
            if (in1 >= in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else {
            result = 0;
        }
        return result;
    }

    private static long binaryOper(long in1, long in2, int oper) throws ArithmeticException {
        long result = 0;
        if (oper == PLUS) {
            result = in1 + in2;
        }
        else if (oper == MINUS) {
            result = in1 - in2;
        }
        else if (oper == STAR) {
            result = in1 * in2;
        }
        else if (oper == PROC) {
            try {
                result = in1 % in2;
            } catch (ArithmeticException ae) {
                System.out.println("\nДеление на 0");
                System.exit(1);
            }
        }
        else if (oper == SLASH) {
            try {
                result = in1 / in2;
            } catch (ArithmeticException ae) {
                System.out.println("\nДеление на 0");
                System.exit(1);
            }
        }
        else if (oper == EQ) {
            if (in1 == in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == NOTEQ) {
            if (in1 != in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == LESS) {
            if (in1 < in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == EQLESS) {
            if (in1 <= in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == GREAT) {
            if (in1 > in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == EQGREAT) {
            if (in1 >= in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else {
            result = 0;
        }
        return result;
    }

    private static short binaryOper(short in1, short in2, int oper) throws ArithmeticException {
        short result = 0;
        if (oper == PLUS) {
            result = (short) (in1 + in2);
        }
        else if (oper == MINUS) {
            result = (short) (in1 - in2);
        }
        else if (oper == STAR) {
            result = (short) (in1 * in2);
        }
        else if (oper == PROC) {try {
            result = (short) (in1 % in2);
        } catch (ArithmeticException ae) {
            System.out.println("Деление на 0");
            System.exit(1);
        }
        }
        else if (oper == SLASH) {
            try {
                result = (short) (in1 / in2);
            } catch (ArithmeticException ae) {
                System.out.println("Деление на 0");
                System.exit(1);
            }
        }
        else if (oper == EQ) {
            if (in1 == in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == NOTEQ) {
            if (in1 != in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == LESS) {
            if (in1 < in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == EQLESS) {
            if (in1 <= in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == GREAT) {
            if (in1 > in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else if (oper == EQGREAT) {
            if (in1 >= in2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else {
            result = 0;
        }
        return result;
    }

    private static void disBinary(Uno i, Queue<Uno> stack) {
        Object f = binaryOperDis(stack.remove().getValue() , stack.remove().getValue(), i.getType());
        stack.add(new Uno(String.valueOf(f)));
    }


    private static Object calculation(List <Uno> input) {
        Queue<Uno> stack = Collections.asLifoQueue(new ArrayDeque<>());
        for (Uno i: input) {
            int type = i.getType();
            if (check(type) == SYMBOL) {
                stack.add(i);
            }
            else {
                disBinary(i,stack);
            }
        }
        return stack.peek().getValue();
    }
}
