/**
 * @author garcia1@student.unimelb.edu.au, John Robert Garcia, 695559
 * The purpose of this class it to group the animal specie (String) and its statistics int[]
 */
package ethicalengine;

import java.util.Arrays;

public class GenericPurpose<K, V> {
	private K key;
	private V[] value;
		
	public GenericPurpose(K key, V[] value) {
		setKey(key);
		setValue(value);
	}
	
	//setters
	public void setKey(K key) {
		this.key = key;
	}
	
	public void setValue(V[] value) {
		this.value = Arrays.copyOf(value, value.length);
	}
	
	//getters
	public K getKey() {
		return this.key;
	}
	
	public V[] getValue() {
		return Arrays.copyOf(value, value.length);
	}
}
