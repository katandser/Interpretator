package com.company;

import java.util.LinkedList;
import java.util.List;

import static com.company.Scan.*;

class Node{
    private Node left;
    private Node right;
    private Node parent;
    private Uno elem;
    private int reType;
    private int type;
    int i = 0;
    List<Integer> listType = new LinkedList<>();
    List<Uno> listEl = new LinkedList<>();


    public void setElem(Uno elem) {
        this.elem = elem;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setListType(List<Integer> listType) {
        this.listType = listType;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addElem(int el){
        listType.add(el);
    }

    public void setReType(int reType) {
        this.reType = reType;
    }

    public void addElUno(Uno el) {listEl.add(el); }

    public Uno getElUno(int i) {return listEl.get(i); }

    public void copy(Node nd) {
        this.parent = nd.getParent();
        this.elem = nd.getElem();
        this.type = nd.getType();
        this.reType = nd.getReType();
        this.listType = nd.listType;
        this.listEl = nd.listEl;
        this.left = new Node();
        this.right = new Node();
        if (nd.getLeft() != null) {
            this.left.copy(nd.getLeft());
        }
        if (nd.getRight() != null) {
            this.right.copy(nd.getRight());
        }
//        for (int i = 0; i < nd.listEl.size(); i++) {
//            this.right = nd.getRight();//nd.getRight();
//            this.left = nd.getRight().getLeft();//nd.getLeft();
//        }
        this.i = nd.i;
    }
    public Node(){}


    public void setValueElem(Object el, int pos) {
        if (listType.get(i) == SHORT) {
            listEl.get(pos).setValue((byte)el);
        }
        else if (listType.get(i) == LONG) {
            listEl.get(pos).setValue((int)el);
        }
        else if (listType.get(i) == LONGLONG){
            listEl.get(pos).setValue((long)el);
        }
        else {
            listEl.get(pos).setValue(null);
        }
    }


    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Node getParent() {
        return parent;
    }

    public List<Integer> getListType() {
        return listType;
    }

    public int getType() {
        return type;
    }

    public int getReType() {
        return reType;
    }

    public Uno getElem() {
        return elem;
    }



    Node(Node parent, Uno elem, int type, int reType){
        this.parent = parent;
        this.elem = elem;
        this.reType = reType;
        this.type = type;
        this.left = null;
        this.right = null;
    }
}

public class Tree {
    private Node root;
    private Node current;
    private static int depth;
    public Tree dopTree;

    public Node getCurrent() {
        return current;
    }

    public Node getRoot() {
        return root;
    }

    public void setCurrent(Node current) {
        this.current = current;
    }

    public void addLeft(Uno elem, int type , int reType){
        if (root == null){
            root = new Node(null,elem,type ,reType);
            current = root;
        }
        else {
            current.setLeft(new Node(current, elem, type, reType));
            current = current.getLeft();
        }
    }
    public void addRight(Uno elem, int type ,int reType){
        current.setRight(new Node(current,elem, type ,reType));
        current = current.getRight();
    }
    public void up(){
        while (current.getType() != Scan.BLACK) {
            current = current.getParent();
        }
        current = current.getParent();
    }

    Tree(){root = current = null; depth = 0;}

    Uno findVar(Uno elem){
        Node saveCur = current;
        while (current != null){
            if (current.getType() == Scan.VAR && current.getElem().getName().equals(elem.getName())){
                Uno un = current.getElem();
                current = saveCur;
                return un;
            }
            current = current.getParent();
        }
        current = saveCur;
        return null;
    }
    boolean findFunc(Uno elem){
        Node saveCur = current;
        while (current != null){
            if (current.getType() == Scan.FUNCTION && current.getElem().getName().equals(elem.getName())){
                current = saveCur;
                return true;
            }
            current = current.getParent();
        }
        current = saveCur;
        return false;
    }

    Node findFuncReNd(Uno elem){
        Node saveCur = current;
        while (current != null){
            if (current.getType() == Scan.FUNCTION && current.getElem().getName().equals(elem.getName())){
                Node c = current;
                current = saveCur;
                return c;
            }
            current = current.getParent();
        }
        current = saveCur;
        return null;
    }



    void view(Node n){
        if (n.getType() != Scan.BLACK) {
            for (int i = 0; i < depth; i++) {
                System.out.print("--");
            }
            if (n.getType() == Scan.FUNCTION){
                System.out.print(n.getElem().getName() + " - " + Uno.stringType(n.getReType()) + " - " + Uno.stringType(n.getType()) + "(");
                List <Integer> l = n.getListType();
                for (int i = 0; i < l.size(); i++) {
                    System.out.print(Uno.stringType(l.get(i)));
                    if (i < l.size() - 1){
                        System.out.print(", ");
                    }
                }
                System.out.println(")");
            }
            if (n.getType() == Scan.VAR){
                System.out.println(n.getElem().getName() + " - " + Uno.stringType(n.getReType()) + " - " + Uno.stringType(n.getType())  + " - " +  n.getElem().getValue());
            }
        }
        if (n.getRight() != null)        {
            depth++;
            view(n.getRight());
            depth--;
        }
        if (n.getLeft() != null){
            view(n.getLeft());
        }
    }

//    Tree copyTree() {
//        dopTree = this;
//        copyAll(dopTree.getRoot());
//        dopTree = null;
//    }
//
//
//    Tree copyAll(Node n) {
//        if (n.getRight() != null) {
//            copyAll(n.getRight());
//        }
//        if (n.getLeft() != null){
//            copyAll(n.getLeft());
//        }
//    }
}
