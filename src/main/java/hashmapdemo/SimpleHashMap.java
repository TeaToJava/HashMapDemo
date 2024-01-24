package hashmapdemo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SimpleHashMap implementation that provides basic map operations.
 * 
 * @author
 *
 * @param <K>
 * @param <V>
 */
public class SimpleHashMap<K, V> implements Map<K, V> {

	/**
	 * The default initial capacity.
	 */
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * The load factor. If the map is filled in by 0.75 the map should be resized.
	 */
	private static final double LOAD_FACTOR = 0.75;

	/**
	 * The number to increase map when the map is filled in by 0.75.
	 */
	private static final int DOUBLE_SIZE = 2;

	private int size;

	/**
	 * The array of elements for saving key-value pairs.
	 */
	private Node<K, V>[] table;

	/**
	 * The number of elements for creating the new array. If the number of elements
	 * isn't passed in the constructor, it will be 16.
	 */
	private int capacity;

	/**
	 * Constructor for SimpleHashMap with default capacity.
	 */
	public SimpleHashMap() {
		table = new Node[DEFAULT_CAPACITY];
		capacity = DEFAULT_CAPACITY;
	}

	/**
	 * Constructor for SimpleHashMap if capacity is passed as parameter.
	 * 
	 * @param capacity the capacity.
	 */
	public SimpleHashMap(int capacity) {
		this.capacity = capacity;
		table = new Node[capacity];
	}

	/**
	 * Generates hash function for key as key hashCode divided by the capacity.
	 * 
	 * @param key the key
	 */
	private int hash(K key) {
		return Math.abs(key.hashCode()) % capacity;
	}

	/**
	 * Returns the quantity of buckets that are filled in.
	 */
	public int size() {
		return size;
	}

	/**
	 * If the map is filled in by 0.75 the map is enlarged twice. The position of
	 * previously added elements is changed.
	 */
	private void resize() {
		Node<K, V>[] temp = Arrays.copyOf(table, capacity);
		capacity = capacity * DOUBLE_SIZE;
		table = new Node[capacity];
		Arrays.stream(temp).filter(n -> n != null).forEach(n -> put(n.key, n.value));
	}

	/**
	 * Generates hash and puts Node in the bucket. If the node is contained in
	 * bucket put the node in chain as next element.
	 */
	@Override
	public V put(K key, V value) {
		int index = hash(key);
		if (size != 0 && (double) size / table.length >= LOAD_FACTOR) {
			resize();
		}
		Node<K, V> node = new Node<K, V>(key, value);
		if (table[index] == null) {
			table[index] = node;
			size++;
		} else {
			Node<K, V> nodeFromTable = table[index];
			while (nodeFromTable.getNext() != null) {
				nodeFromTable = nodeFromTable.getNext();
			}
			nodeFromTable.setNext(node);
		}
		return node.getValue();
	}

	/**
	 * Generates hash key and returns value by key.
	 */
	@Override
	public V get(K key) {
		if (key == null) {
			return null;
		}
		int index = hash(key);
		if (index >= capacity) {
			return null;
		}
		Node<K, V> node = table[index];
		while (node != null && !node.getKey().equals(key)) {
			node = node.getNext();
		}
		return node.getValue();
	}

	/**
	 * Returns true if the map contains the specified key.
	 */
	@Override
	public boolean containsKey(K key) {
		if (key == null) {
			return false;
		} else {
			int index = hash(key);
			if (index >= capacity) {
				return false;
			}
			Node<K, V> node = table[index];
			while (node != null) {
				if (node.getKey().equals(key)) {
					return true;
				}
				node = node.getNext();
			}
			return false;
		}
	}

	/**
	 * Returns true if the map doesn't contain elements.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0 ? true : false;
	}

	/**
	 * Returns true if the specified value is present.
	 */
	@Override
	public boolean containsValue(V value) {
		if (size == 0) {
			return false;
		}
		for (int i = 0; i < table.length; i++) {
			Node<K, V> node = table[i];
			while (node != null) {
				if (node.getValue().equals(value)) {
					return true;
				}
				node = node.getNext();
			}
		}
		return false;
	}

	/**
	 * Removes value by key and returns the last removed value found by key.
	 */
	@Override
	public V remove(K key) {
		if (size > 0) {
			int index = hash(key);
			if (index >= capacity) {
				return null;
			}
			Node<K, V> node = table[index];
			if (node == null) {
				return null;
			}
			Node<K, V> temp = null;
			Node<K, V> first = null;
			V value = null;

			while (node != null) {
				K nodeKey = node.getKey();
				V nodeValue = node.getValue();
				if (!nodeKey.equals(key)) {
					Node n = new Node(nodeKey, nodeValue);
					if (temp == null) {
						temp = n;
						first = n;
					} else {
						if (first.getNext() == null) {
							first.setNext(n);
						}
						temp.setNext(n);
						temp = n;
					}
				} else {
					value = nodeValue;
				}
				node = node.getNext();
			}
			table[index] = first;
			if (temp == null) {
				size--;
			}
			return value;
		}
		return null;
	}

	/**
	 * Sets null for all map elements.
	 */
	@Override
	public void clear() {
		if (size > 0) {
			for (int i = 0; i < table.length; i++) {
				table[i] = null;
			}
			size = 0;
		}
	}

	/**
	 * Node class that represents map element.
	 *
	 * @param <K>
	 * @param <V>
	 */
	@EqualsAndHashCode
	@Getter
	@Setter
	@NoArgsConstructor
	static class Node<K, V> {

		private K key;
		private V value;
		private Node<K, V> next;

		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

}