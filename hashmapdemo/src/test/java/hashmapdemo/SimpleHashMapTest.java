package hashmapdemo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SimpleHashMapTest {
	@Test
	public void simpleHashMapShouldContainKeys() {
		Map<Integer, Integer> map = new SimpleHashMap();
		map.put(1, 1);
		map.put(1, 11);
		map.put(18, 18);
		map.put(19, 19);
		map.put(20, 20);
		map.put(21, 21);
		map.put(22, 22);
		map.put(23, 23);
		map.put(24, 24);
		map.put(25, 25);
		map.put(26, 26);
		map.put(27, 27);
		map.put(16, 16);
		map.put(31, 31);
		map.put(28, 28);
		map.put(28, 280);
		assertTrue(map.containsKey(1));
		assertTrue(map.containsValue(28));
	}

	@Test
	public void elementShouldBeRemovedFromTheSimpleHashMap() {
		Map<Integer, Integer> map = new SimpleHashMap();
		map.put(1, 1);
		map.put(1, 11);
		map.put(18, 18);
		map.put(19, 19);
		map.put(20, 20);
		map.put(28, 280);
		map.remove(1);
		assertFalse(map.containsKey(1));
	}

	@Test
	public void clearSimpleHashMap() {
		Map<Integer, Integer> map = new SimpleHashMap();
		map.put(1, 1);
		map.put(1, 11);
		map.put(18, 18);
		map.put(19, 19);
		map.put(20, 20);
		map.put(28, 280);
		map.clear();
		assertTrue(map.isEmpty());
	}
	
	@Test
	public void fillInSimpleHashMap() {
		Map<Integer, Integer> map = new SimpleHashMap();
		for(int i = 0; i< 10000; i++) {
			map.put(i, i);
		}
		assertTrue(map.get(500).equals(500));
	}
}