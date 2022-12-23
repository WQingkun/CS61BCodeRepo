package deque;

public class ArrayListDeque<T> {
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    public ArrayListDeque(){
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
        if (nextFirst + 1 + i > items.length) {
            return nextFirst + 1 + i - items.length;
        } else {
            return nextFirst + 1 +i;
        }

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
        }
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public void printDeque(){
        for (int i = 0; i < size - 1; i++){
            System.out.print(items[conceptInd(i)] + " ");
        }
        System.out.println();
    }
    public T removeFirst(){
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
        return items[conceptInd(index)];
    }
}
