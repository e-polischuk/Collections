package ua.itea.collections;

import java.util.LinkedList;

/**
 * Variant_9
 * 
 * @author Polischuk
 *
 */
public class Homework {

    public static void main(String[] args) {
	LinkedList<Integer> list = new LinkedList<Integer>();
	
	for(int i = 1; i <= 10; i++) {
	    list.addLast(i - 1);
	    list.addLast(i);
	    list.addLast(i + 1);
	}
	
	System.out.println(list);
	System.out.println("List size: " + list.size());
	System.out.println();
	
	list = deleteDuplicates(list);
	
	System.out.println(list);
	System.out.println("List size: " + list.size());
    }
    
    public static <T> LinkedList<T> deleteDuplicates(LinkedList<T> list) {
	int size = list.size();
	while(size-- > 0) {
	    T e = list.removeFirst();
	    if(!list.contains(e)) list.addLast(e);
	}
	return list;
    }

}
