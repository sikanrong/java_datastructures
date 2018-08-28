package com.sikanrong.practice.datastructures.hashtable.hashfunction;
import java.io.*;

public final class FNVHash {
	public static int FNV_PRIME = 0x01000193;
	public static int FNV_SEED = 0x811C9DC5;
	
	private static int hashByte(byte input, int seed) {
		int h = seed * FNV_PRIME;
		h ^= input;
		return h;
	}
	
	public static <T extends Serializable> int hash(T input) {
		return hash(input, 0);
	}
	
	public static <T extends Serializable> int hash(T input, int seed) {
		int hash = seed ^ FNV_SEED;
		
		PipedInputStream pistream = new PipedInputStream();
		PipedOutputStream postream = null;
		ObjectOutput out = null; 
		try {
			postream = new PipedOutputStream(pistream);
			out = new ObjectOutputStream(postream);
			out.writeObject(input);
			out.flush();
			out.close();
			byte c = -2;
			while(c != -1) {
				c = (byte)pistream.read();
				hash = FNVHash.hashByte(c, hash);
			}
			
		} catch(IOException ex) {
			//Do nothing
		} finally {
			try {
				pistream.close();
				postream.close();
			} catch(IOException ex) {
				//Do nothing
			}
		}
		
		//Bitwise 32-bit absolute value
		int mask = hash >> 31;	
		hash = ((mask ^ hash) - mask);
		
		return hash;
	}
}
