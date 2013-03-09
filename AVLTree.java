package map;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AVLTree<K extends Comparable<K>, V extends Comparable<V>>
		implements Map<K, V> {
	private class Node {
		public Node(K key2, V value2, Node left, Node right) {
			this.key = key2;
			this.value = value2;
			this.left = left;
			this.right = right;
			this.height = 1;
		}

		K key;
		V value;
		Node left;
		Node right;
		int height;
	}

	private int size = 0;
	private Node root;

	private int height(Node node) {
		if (node != null)
			return node.height;
		return 0;
	}

	private int balanceFactor(Node node) {
		return (height(node.right) - height(node.left));
	}

	public int length() {
		return balanceFactor(root);
	}

	private void fixHeight(Node node) {
		int heightLeft = height(node.left);
		int heightRight = height(node.right);
		if (heightLeft > heightRight)
			node.height = heightLeft + 1;
		else
			node.height = heightRight + 1;
	}

	private Node rightTurn(Node node) {
		Node node1 = node.left;
		node.left = node1.right;
		node1.right = node;
		fixHeight(node);
		fixHeight(node1);
		return node1;
	}

	private Node leftTurn(Node node) {
		Node node1 = node.right;
		node.right = node1.left;
		node1.left = node;
		fixHeight(node);
		fixHeight(node1);
		return node1;
	}

	private Node balance(Node node) {
		fixHeight(node);
		if (balanceFactor(node) == 2) {
			if (balanceFactor(node.right) < 0)
				node.right = rightTurn(node.right);
			return leftTurn(node);
		}
		if (balanceFactor(node) == -2) {
			if (balanceFactor(node.left) > 0)
				node.left = leftTurn(node.left);
			return rightTurn(node);
		}
		return node;
	}

	@Override
	public void clear() {
		root = null;
	}

	private boolean keySearch(Node node, K key) {
		if (node == null)
			return false;
		else {
			int compare = node.key.compareTo(key);
			if (compare == 0)
				return true;
			else if (compare < 0)
				return keySearch(node.right, key);
			return keySearch(node.left, key);

		}
	}

	@Override
	public boolean containsKey(Object arg0) {
		K key = (K) arg0;
		if (root == null)
			return false;
		else
			return keySearch(root, key);
	}

	@Override
	public boolean containsValue(Object arg0) {
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	private V getInternal(Node node, K key) {
		if (!containsKey(key))
			return null;
		else {
			if (node == null)
				return null;
			else {
				int compare = node.key.compareTo(key);
				if (compare == 0)
					return node.value;
				if (compare > 0)
					return getInternal(node.left, key);
				return getInternal(node.right, key);
			}
		}
	}

	@Override
	public V get(Object arg0) {
		K key = (K) arg0;
		return getInternal(root, key);
	}

	@Override
	public boolean isEmpty() {
		return root == null;
	}

	private HashSet<K> keyHashSet(Node node) {
		HashSet<K> hashSet;
		hashSet = new HashSet<K>();
		if (node.left != null)
			keyHashSet(node.left);
		if (node.right != null)
			keyHashSet(node.right);
		if (node.left == null && node.right == null) {
			hashSet.add(node.key);

		}
		return hashSet;

	}

	@Override
	public Set<K> keySet() {
		return keyHashSet(root);
	}

	private V putElement(Node node, Node parent, K key, V value) {
		if (node == null) {
			++size;
			Node node1 = new Node(key, value, null, null);
			if (parent == null) {
				root = node1;
				return value;
			} else {
				int comparePut = parent.key.compareTo(key);
				if (comparePut > 0) {
					parent.left = node1;
				} else
					parent.right = node1;
				balance(node1);
				return value;
			}
		}
		int compare = node.key.compareTo(key);
		if (compare > 0)
			putElement(node.left, node, key, value);
		else if (compare < 0)
			putElement(node.right, node, key, value);
		else {
			if (compare == 0) {
				V returnValue;
				returnValue = node.value;
				node.value = value;
				return returnValue;
			}
		}

		return null;

	}

	@Override
	public V put(K arg0, V arg1) {
		K key = (K) arg0;
		V value = (V) arg1;
		return putElement(root, null, key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> arg0) {
		// TODO Auto-generated method stub

	}

	private Node findMinimalElement(Node node) {
		if (node.left == null)
			return node;
		else
			return (findMinimalElement(node.left));
	}

	V returnValue;
	boolean flag = false;

	private V removeElement(Node node, Node parent, K key) {

		if (node == null) {
			return null;
		}

		int compare = node.key.compareTo(key);
		if (compare < 0)
			removeElement(node.right, node, key);
		else if (compare > 0)
			removeElement(node.left, node, key);
		else {
			flag = true;
			Node nodeLeft = node.left;
			Node nodeRight = node.right;
			returnValue = node.value;
			--size;
			if (parent != null) {
				int compareRemove = parent.key.compareTo(node.key);
				if (compareRemove > 0) {
					parent.left = null;
				} else
					parent.right = null;
			}
			node.key = null;
			node.value = null;
			node.left = null;
			node.right = null;
			if (nodeLeft != null || nodeRight != null) {
				Node min = findMinimalElement(root.right);
				if (root.key.compareTo(key) == 0)
					root = min;
				removeElement(root.right, root, min.key);
				min.left = nodeLeft;
				min.right = nodeRight;
				balance(min);
			}

		}

		balance(node);
		if (!flag) {
			return null;
		} else
			return returnValue;
	}

	@Override
	public V remove(Object arg0) {
		K key = (K) arg0;
		if (root.key.compareTo(key) == 0&root.left==null&root.right==null)
			{V rootValue=root.value;
			root=null;
			return rootValue;
			}
		return removeElement(root, null, key);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

}
