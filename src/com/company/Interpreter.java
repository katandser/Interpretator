package com.company;

import com.sun.source.util.Plugin;

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

    Queue <List <Uno> > stackLevelInterpretation = Collections.asLifoQueue(new ArrayDeque<>());

    void addLevel(List <Uno> list) {
        stackLevelInterpretation.add(list);
    }
    void removeLevel() {
        stackLevelInterpretation.remove();
    }

    static private int check(int lex) {
        if (lex == CONSDEC || lex == CONSHEX || lex == VAR) {
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

    static void vir() {
        List <Uno> input = new LinkedList<>();
        List <Uno> output = new LinkedList<>();
        Queue<Uno> stack = Collections.asLifoQueue(new ArrayDeque<>());



        Uno d;
        input.add(new Uno("3", CONSDEC, 1));
        input.add(new Uno("--", MINUSMINUS, 1));
        input.add(new Uno("*", STAR, 1));
        input.add(new Uno("4", CONSDEC, 1));
//        input.add(new Uno("*", STAR, 1));
//        input.add(new Uno("2", CONSDEC, 1));
//        input.add(new Uno("/", SLASH, 1));
//        input.add(new Uno("(", OPEN_CIRCLE, 1));
//        input.add(new Uno("1", CONSDEC, 1));
//        input.add(new Uno("-", MINUS, 1));
//        input.add(new Uno("5", CONSDEC, 1));
//        input.add(new Uno(")", CLOSE_CIRCLE, 1));
//        input.add(new Uno("*", STAR, 1));
//        input.add(new Uno("2", CONSDEC, 1));
//        input.add(new Uno("!=", NOTEQ, 1));
//        input.add(new Uno("-1", CONSDEC, 1));
//        input.add(new Uno("<", LESS, 1));
//        input.add(new Uno("-2", CONSDEC, 1));




        input.forEach(element-> System.out.print(element.getName()));
        System.out.println();

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
        output.forEach(element-> System.out.print(element.getName()));
        calculation(output);
    }


    private static Object binaryOperDis(Object in1, Object in2, int oper) {
        if (in1 instanceof Long || in2 instanceof Long) {
            return (long) binaryOper((long)in1 , (long)in2 ,oper );
        }
        else if (in1 instanceof Integer || in2 instanceof Integer) {
            return (int) binaryOper((int)in2, (int)in1, oper);
        }
        else
        {
            return (short) binaryOper((short)in1 , (short)in2 ,oper );
        }
    }


    private static Object unaryOperDis(Object in1, int oper) {
        if (in1 instanceof Long) {
            return (long) unaryOper((long)in1 , oper );
        }
        else if (in1 instanceof Integer) {
            return (int) unaryOper((int)in1, oper);
        }
        else {
            return (short) unaryOper((short)in1 ,oper );
        }
    }

    private static int unaryOper(int in1, int oper) {
        int result;
        if (oper == PLUSPLUS) {
            result = in1 + 1;
        }
        else if (oper == MINUSMINUS) {
            result = in1 - 1;
        }
        else {
            result = 100;
        }
        return result;
    }

    private static long unaryOper(long in1, int oper) {
        long result;
        if (oper == PLUSPLUS) {
            result = in1 + 1;
        }
        else if (oper == MINUSMINUS) {
            result = in1 - 1;
        }
        else {
            result = 100;
        }
        return result;
    }
    private static short unaryOper(short in1, int oper) {
        short result;
        if (oper == PLUSPLUS) {
            result = (short) (in1 + 1);
        }
        else if (oper == MINUSMINUS) {
            result = (short) (in1 - 1);
        }
        else {
            result = 100;
        }
        return result;
    }


    private static int binaryOper(int in1, int in2, int oper) {
        int result;
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
            result = in1 % in2;
        }
        else if (oper == SLASH) {
            result = in1 / in2;
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

    private static long binaryOper(long in1, long in2, int oper) {
        long result;
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
            result = in1 % in2;
        }
        else if (oper == SLASH) {
            result = in1 / in2;
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

    private static short binaryOper(short in1, short in2, int oper) {
        short result;
        if (oper == PLUS) {
            result = (short) (in1 + in2);
        }
        else if (oper == MINUS) {
            result = (short) (in1 - in2);
        }
        else if (oper == STAR) {
            result = (short) (in1 * in2);
        }
        else if (oper == PROC) {
            result = (short) (in1 % in2);
        }
        else if (oper == SLASH) {
            result = (short) (in1 / in2);
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
    private static void disUnary(Uno i, Queue<Uno> stack) {
        Object f = unaryOperDis(stack.remove().getValue() , i.getType());
        stack.add(new Uno(String.valueOf(f)));
    }


    private static int calculation(List <Uno> input) {
        Queue<Uno> stack = Collections.asLifoQueue(new ArrayDeque<>());
        for (Uno i: input) {
            int type = i.getType();
            if (check(type) == SYMBOL) {
                stack.add(i);
            }
            else if (check(type) == POST) {
;               disUnary(i,stack);
            }
            else {
                disBinary(i,stack);
            }
        }
        System.out.println();
        System.out.println(stack.peek().getValue());
        return 1;
    }
}
