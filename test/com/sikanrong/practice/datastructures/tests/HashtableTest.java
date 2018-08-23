package com.sikanrong.practice.datastructures.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.sikanrong.practice.datastructures.hashtable.LomutoPerfectHashTable;
import com.sikanrong.practice.datastructures.hashtable.hashfunction.FNVHash;

import org.junit.jupiter.api.Test;

class HashtableTest {

	@Test
	void testFNVHash() {
		String examples[] = {"Hello World", "Hello Worl", "Iceland", "Kangaroo"};
		int hashes[] = new int[4];
		for(int i = 0; i < examples.length; i++) {
			hashes[i] = (FNVHash.hash(examples[i]));
		}
		
		int expected[] = {865084711, 954784505, 1166462341, 452284363};
		
		assertArrayEquals(hashes, expected);
	}
	
	@Test
	void testLomutoPerfectHashTable() {
		
	}

}
