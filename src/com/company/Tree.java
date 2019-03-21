package com.company;

import java.util.LinkedList;
import java.util.List;

class Node{
    private Node left;
    private Node right;
    private Node parent;
    private Uno elem;
    private int reType;
    private int type;
    private List<Integer> listType = new LinkedList<>();

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

    public Node getCurrent() {
        return current;
    }

    public Node getRoot() {
        return root;
    }

    public void addLeft(Uno elem, int type ,int reType){
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

    boolean findVar(Uno elem){
        Node saveCur = current;
        while (current != null){
            if (current.getType() == Scan.VAR && current.getElem().getName().equals(elem.getName())){
                current = saveCur;
                return true;
            }
            current = current.getParent();
        }
        current = saveCur;
        return false;
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
                System.out.println(n.getElem().getName() + " - " + Uno.stringType(n.getReType()) + " - " + Uno.stringType(n.getType()));
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
}