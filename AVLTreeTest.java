package map;

import junit.framework.TestCase;

public class AVLTreeTest extends TestCase {
	private AVLTree<Integer, Integer> tree = new AVLTree<Integer, Integer>();

	public void put() {
		tree.put(6, 6);
		tree.put(4, 7);
		tree.put(5, 3);
		tree.put(8, 1);
		tree.put(3, 2);
		tree.put(1, 5);
		tree.put(2, 10);
	}

	public void testPut() {
		put();
		assertEquals(tree.get(2), new Integer(10));
		assertEquals(tree.get(3), new Integer(2));
		assertNull(tree.get(7));
		tree.put(3,7);
		assertEquals(tree.get(3), new Integer(7));
	}

	public void testRemove() {
		put();
		assertEquals(tree.get(2), new Integer(10));
		assertNull(tree.remove(57));
		assertEquals(tree.remove(2), new Integer(10));
		//assertNull(tree.get(8));
		assertEquals(tree.get(5), new Integer(6));
	}

	public void testSize() {
		put();
		assertEquals(tree.size(), 7);
		// assertNull(tree.get());
	}
	
	public void testContainsKey() {
		put();
		assertEquals(tree.containsKey(2), true);
		assertEquals(tree.containsKey(6), true);
		assertEquals(tree.containsKey(15), false);
		
	}
	
}
