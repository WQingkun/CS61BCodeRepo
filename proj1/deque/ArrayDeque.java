package deque;
import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T>{
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    public ArrayDeque(){
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }
    private void resize(int capacity){
        T[] Tlist = (T[]) new Object[capacity];
//        System.arraycopy(items, nextFirst + 1, Tlist, 0, size);
        for (int j = 0; j < items.length; j++){
             Tlist[j] = items[conceptInd(j)];
        }
        items = Tlist;
        nextFirst = items.length - 1;
        nextLast = size;
    }
    private int conceptInd(int i){
        if (nextFirst + 1 + i > items.length - 1) {
            return nextFirst + 1 + i - items.length;
        } else {
            return nextFirst + 1 +i;
        }

    }
    public int size(){
        return size;
    }
    public void addFirst(T item){
        if (size == items.length) {
            resize(size*2);
            items[nextFirst] = item;
            size += 1;
            nextFirst -= 1;
        }else {
            items[nextFirst] = item;
            if (nextFirst - 1 < 0) {
                nextFirst = items.length - 1;
            }else {
                nextFirst -= 1;
            }
            size += 1;
        }
    }
    public void addLast(T item){
        if (size == items.length) {
            resize(size*2);
            items[nextLast] = item;
            size += 1;
            nextLast += 1;
        }else{
            if (nextLast == items.length - 1){
                items[items.length - 1] = item;
                nextLast = 0;
            }else{
                items[nextLast] = item;
                nextLast += 1;
            }
            size += 1;
        }
    }
//    public boolean isEmpty(){
//        return size == 0;
//    }
    public void printDeque(){
        for (int i = 0; i < size - 1; i++){
            System.out.print(items[conceptInd(i)] + " ");
        }
        System.out.println();
    }
    public T removeFirst(){
        if (size == 0) {
            return null;
        }
        T val;
        if (nextFirst == items.length - 1){
            val = items[0];
            nextFirst = 0;
        }else{
            val = items[nextFirst + 1];
            nextFirst = nextFirst + 1;
        }
        size --;
        return val;
    }
    public T removeLast(){
        if (size == 0) {
            return null;
        }
        T val;
        if (nextLast == 0) {
            val = items[items.length - 1];
            nextLast = items.length - 1;
        }else{
            val = items[nextLast - 1];
            nextLast --;
        }
        size --;
        return val;
    }
    public T get(int index){
        if (index < 0 || index > size - 1) {
            return null;
        }
        return items[conceptInd(index)];
    }
    public Iterator<T> iterator(){
        return new ArrayListIterator();
    }
    private class ArrayListIterator implements Iterator<T>{
        private int wizPos;
        private ArrayListIterator(){
            wizPos = 0;
        }
        public boolean hasNext(){
            return wizPos < size;
        }
        public T next(){
            T item = get(wizPos);
            wizPos += 1;
            return item;
        }
    }
    public boolean equals(Object o){
        //如果o和此Deque地址相同
        if (this == o) {
            return true;
        }
        //if o is empty ref, return false
        if (o == null) {
            return false;
        }
        //if o is not a ref of a Deque, return false
        if (!(o instanceof Deque)){
            return false;
        }
        Deque<T> ShallowO = (Deque<T>) o;
        if (ShallowO.size() != this.size()){
            return false;
        }
        for(int i = 0; i < this.size(); i++){
            if (ShallowO.get(i) != this.get(i)){
                return false;
            }
        }
        return true;
    }
}
