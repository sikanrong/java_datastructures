package com.sikanrong.practice.datastructures.hashtable.hashfunction;
//https://create.stephan-brumme.com/fnv-hash/
public final class FNVHash {
	public static int FNV_PRIME = 0x01000193;
	public static int FNV_SEED = 0x811C9DC5;
	
	private static int hashByte(byte input, int seed) {
		int h = (input ^ seed) * FNV_PRIME;
		return h;
	}
	
	public static int hash(String input) {
		return hash(input, FNV_SEED);
	}
	
	public static int hash(String input, int seed) {
		int hash = seed;
		
		for(int i = 0; i < input.length(); i++) {
			byte c = (byte)input.charAt(i);
			hash = FNVHash.hashByte(c, hash);
		}
		
		hash &= ~(1 << 31); //clear the twos-complement bit (always a positive number)
		
		return hash;
	}
}
