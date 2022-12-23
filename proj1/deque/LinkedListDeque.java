package deque;

public class LinkedListDeque <T>{
    private int size;
    private Node sentinel = new Node();
    private class Node{
        T data;
        Node next;
        Node prev;
        private Node(){
            data = null;
            next = null;
            prev = null;
        }
    }
    public LinkedListDeque(){
        size = 0;
        sentinel.data = null;
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }



    public void addFirst(T item){
        size += 1;
        Node node = new Node();
        node.data = item;
        node.prev = sentinel;
        node.next = sentinel.next;
        sentinel.next.prev = node;
        sentinel.next = node;
    }
    public void addLast(T item){
        size += 1;
        Node node = new Node();
        //maintain node
        node.data = item;
        node.prev = sentinel.prev;
        node.next = sentinel;
        //maintain last node in deque
        sentinel.prev.next = node;
        //maintain sentinel node
        sentinel.prev = node;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        Node node = sentinel.next;
        for (int i = 0; i < size; i++){
            System.out.print(node.data + " ");
            node = node.next;
        }
        System.out.println();
    }
    public T removeFirst(){
        if (size == 0) {
            return null;
        }
        size -= 1;
        T val = sentinel.next.data;
        //maintain second node
        sentinel.next.next.prev = sentinel;
        //maintain sentinel node
        sentinel.next = sentinel.next.next;
        return val;
    }
    public T removeLast(){
        if (size == 0) {
            return null;
        }
        size -= 1;
        T val = sentinel.prev.data;
        //maintain second last node
        sentinel.prev.prev.next = sentinel;
        //maintain sentinel node
        sentinel.prev = sentinel.prev.prev;
        return val;
    }
    public T get(int index){
        Node node = sentinel.next;
        for(int i = 0; i < index; i++){
            node = node.next;
        }
        return node.data;
    }
    private T getRecursiveHelper(int index, int nodeInd, Node node){
        if (node.data == null) {
           return null;
        }
        if (nodeInd == index) {
            return node.data;
        }
        return getRecursiveHelper(index, nodeInd+1 , node.next);
    }
    public T getRecursive(int index){
        Node node = sentinel.next;
        if(index < 0){
            return null;
        }
        int nodeInd = 0;
        return getRecursiveHelper(index, nodeInd, node);
    }
//    public Iterator<T> iterator(){
//
//    }
//    public boolean equals(Object o){
//
//    }
}
