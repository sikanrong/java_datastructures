package com.sikanrong.practice.datastructures.hashtable.hashfunction;
public final class FNVHash {
	public static int FNV_PRIME = 0x01000193;
	public static int FNV_SEED = 0x811C9DC5;
	
	private static int hashByte(byte input, int seed) {
		int h = seed * FNV_PRIME;
		h ^= input;
		return h;
	}
	
	public static int hash(String input) {
		return hash(input, 0);
	}
	
	public static int hash(String input, int seed) {
		int hash = seed ^ FNV_SEED;
		
		for(int i = 0; i < input.length(); i++) {
			byte c = (byte)input.charAt(i);
			hash = FNVHash.hashByte(c, hash);
		}
		
		//Bitwise 32-bit absolute value
		int mask = hash >> 31;	
		hash = ((mask ^ hash) - mask);
		
		return hash;
	}
}
