package com.sikanrong.practice.datastructures.hashtable;
import com.sikanrong.practice.datastructures.hashtable.hashfunction.FNVHash;
import java.lang.Exception;
import java.lang.Math;

public class LomutoPerfectHashTable {
	private Object values[][];
	private Object buckets[][][];
	private byte buckets_load[];
	private byte buckets_capacity;
	public float load_factor;
	public int storage_size;
	public int data_size;
	
	public LomutoPerfectHashTable(int size){
		this.storage_size = size;
		this.values = new Object[this.storage_size][];
	}
	
	public void putData(String keys[], Object data[]) throws Exception {
		if(keys.length != data.length) {
			throw new Exception("setData keys array must match values array length");
		}
		
		this.data_size = keys.length;
		this.load_factor = this.data_size / this.storage_size;
		buckets_capacity = (byte)(Math.pow(Math.ceil(load_factor), 2) + load_factor);
		this.buckets = new Object[this.storage_size][buckets_capacity][2];
		this.buckets_load = new byte[this.storage_size];
		
		hashValues(keys, data);
	}
	
	private void hashValues(String keys[], Object data[]) {
		for(int i = 0; i < keys.length; i++) {
			int bucket_idx = FNVHash.hash(keys[i]) % this.storage_size;
			Object subBucket[] = buckets[bucket_idx][buckets_load[bucket_idx]];
			subBucket[0] = keys[i];
			subBucket[1] = i;
			buckets_load[bucket_idx]++;
		}
		
		for(int i = 0; i < this.storage_size; i++) {
			if(buckets_load[i] == 0)
				continue;
						
			int values_secondary_size = (int)Math.pow(buckets_load[i], 2);
			
			this.values[i] = new Object[values_secondary_size + 2];
			
			int item = 0;
			int offset = 0;
			
			//Set slots[] to Object type so that the array initializes to null
			//otherwise slots[j] == slot could be true just because slots[j] was 
			//set to 0 as a default value upon array initialization
			
			Object slots[] = new Object[buckets_load[i]];
			while(item < buckets_load[i]) {
				int hsh = FNVHash.hash((String)buckets[i][item][0], offset);
				int slot =  hsh % values_secondary_size;

				//check if slot exists in slots so far using linear search.
				boolean slotExists = false;
				for(int j = 0; j < item; j++) {
					if(slots[j] != null && (int)slots[j] == slot) {
						slotExists = true;
						break;
					}
				};
				
				if(slotExists || this.values[i][slot + 2] != null) {
					offset++;
					item = 0;
					slots = new Object[buckets_load[i]];
					
				} else {
					slots[item] = slot;
					item++;
				}
			}
			
			this.values[i][0] = offset;
			this.values[i][1] = values_secondary_size;
			for(int j = 0; j < buckets_load[i]; j++) {
				int key_idx = (int)this.buckets[i][j][1];
				Object v = data[key_idx];
				this.values[i][(int)slots[j] + 2] = v;
			}
		}
	}
	
	public Object get(String key) {
		int b = FNVHash.hash(key) % this.storage_size;
		int d = (int)this.values[b][0];
		int m = (int)this.values[b][1];
		int c = FNVHash.hash(key, d) % m;
		return this.values[b][c + 2];
	}
}