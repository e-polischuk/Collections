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
        System.out.println( map.put("one", 1) );
        System.out.println( map.put("two", 2) );
        System.out.println( map.put("one", 3) );
        System.out.println( map.put("one", 4) );
        System.out.println( map.put("one", 5) );
        
    }
}
