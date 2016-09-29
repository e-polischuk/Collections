package ua.itea.collections;

/**
 * Test for MyHashMap
 *
 */
public class App 
{
    public static void main( String[] args )
    {
	MyHashMap<String, Integer> map = new MyHashMap<String, Integer>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        map.put("five", 5);
        map.remove("two");
        map.put("six", 10);
        map.put("7", 15);
        map.put("sahur", 45);
        map.remove("three");
        map.remove("one");
        map.remove("7");
        map.remove("six");
        map.put("one", 34);
        map.put("four", Integer.MAX_VALUE >> 1);
        map.put("sahur", 45);
//        map.remove("two");
        System.out.println(map.get("sahur"));
        System.out.println(map.get("four"));
        
        
    }
}
