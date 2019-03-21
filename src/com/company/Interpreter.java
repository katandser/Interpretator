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
        else if (lex == PLUSPLUS || lex == MINUSMINUS) {
            return POST;
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
        input.add(new Uno("+", PLUS, 1));
        input.add(new Uno("4", CONSDEC, 1));
        input.add(new Uno("*", STAR, 1));
        input.add(new Uno("2", CONSDEC, 1));
        input.add(new Uno("/", SLASH, 1));
        input.add(new Uno("(", OPEN_CIRCLE, 1));
        input.add(new Uno("1", CONSDEC, 1));
        input.add(new Uno("-", MINUS, 1));
        input.add(new Uno("5", CONSDEC, 1));
        input.add(new Uno(")", CLOSE_CIRCLE, 1));
        input.add(new Uno("*", STAR, 1));
        input.add(new Uno("2", CONSDEC, 1));

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


    private static int binaryOperDis(Object in1, Object in2, int oper) {
        if (in1 instanceof Long || in2 instanceof Long) {
            //return (int) binaryOper((long)in1 , (long)in2 ,oper );
        }
        else if (in1 instanceof Integer || in2 instanceof Integer) {
            return (int) binaryOper((Integer)in2, (Integer)in1, oper);
        }
        else
        {
            ;//long l = binaryOper((long) in1, (long) in2, oper); return (int) l;
        }
        return 1;

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
        else {
            result = 0;
        }
        return result;
    }ghmghmjgmjgjm
    private static void dis(Uno i, Queue<Uno> stack) {
        Object f = binaryOperDis(stack.remove().getValue() , stack.remove().getValue(), i.getType());
        int jj = (int)f;
        stack.add(new Uno(String.valueOf(jj)));
    }


    private static int calculation(List <Uno> input) {
        Queue<Uno> stack = Collections.asLifoQueue(new ArrayDeque<>());
        for (Uno i: input) {
            int type = i.getType();
            if (check(type) == SYMBOL) {
                stack.add(i);
            }
            else {
                dis(i,stack);
            }
        }
        System.out.println(stack.peek());
        return 1;
    }
}
