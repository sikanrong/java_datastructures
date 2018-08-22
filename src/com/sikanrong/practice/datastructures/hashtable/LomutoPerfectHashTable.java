package com.sikanrong.practice.datastructures.hashtable;
import com.sikanrong.practice.datastructures.hashtable.hashfunction.FNVHash;
import java.lang.Exception;
import java.lang.Math;

public class LomutoPerfectHashTable {
	private Object values[][];
	private int buckets[][];
	private byte buckets_load[];
	private byte buckets_capacity;
	public float load_factor;
	public int storage_size;
	public int data_size;
	
	LomutoPerfectHashTable(int size){
		this.storage_size = size;
		this.values = new Object[this.storage_size][];
	}
	
	public void putData(String keys[], Object values[]) throws Exception {
		if(keys.length != values.length) {
			throw new Exception("setData keys array must match values array length");
		}
		
		this.data_size = keys.length;
		this.load_factor = this.data_size / this.storage_size;
		buckets_capacity = (byte)(Math.ceil(load_factor) * 2);
		this.buckets = new int[this.storage_size][buckets_capacity];
		this.buckets_load = new byte[buckets_capacity];
		
		rehash(keys, values);
	}
	
	private void rehash(String keys[], Object values[]) {
		for(int i = 0; i < keys.length; i++) {
			int bucket_idx = FNVHash.hash(keys[i]) % this.storage_size;
			buckets[bucket_idx][buckets_load[bucket_idx]] = i;
			buckets_load[bucket_idx]++;
		}
	}
}