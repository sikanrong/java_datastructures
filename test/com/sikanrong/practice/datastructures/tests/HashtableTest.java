package com.sikanrong.practice.datastructures.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.sikanrong.practice.datastructures.hashtable.LomutoPerfectHashTable;
import com.sikanrong.practice.datastructures.hashtable.hashfunction.FNVHash;

import org.json.*;

import org.junit.jupiter.api.Test;

class HashtableTest {

	@Test
	void testFNVHash() {
		String examples[] = {"Hello World", "Hello Worl", "Iceland", "Kangaroo"};
		int hashes[] = new int[4];
		for(int i = 0; i < examples.length; i++) {
			hashes[i] = (FNVHash.hash(examples[i]));
		}
		
		int expected[] = {310551791, 192771031, 1586795959, 1852403975};
		
		assertArrayEquals(expected, hashes);
	}
	
	@Test
	void testLomutoPerfectHashTable() throws Exception {
		//read test data 
		JSONTokener tokenizer = new JSONTokener(getClass().getResourceAsStream("./resources/testdata.json"));
		JSONObject test_data = new JSONObject(tokenizer);
		
		String keys[] = new String[test_data.length()];
		Object data[] = new Object[keys.length];
		//Extract the keys and values from test_data into primitive arrays
		test_data.keySet().toArray(keys);
		for(int i = 0; i < keys.length; i++) {
			data[i] = test_data.get(keys[i]);
		}
		
		int hashtable_size = (int)(data.length / 2);
		
		LomutoPerfectHashTable lpht = new LomutoPerfectHashTable(hashtable_size);
		lpht.putData(keys, data);
		
		Object results_data[] = new Object[data.length]; 
		for(int i = 0; i < keys.length; i++) {
			results_data[i] = lpht.get(keys[i]);
		}
		
		assertArrayEquals(data, results_data);
	}

}
