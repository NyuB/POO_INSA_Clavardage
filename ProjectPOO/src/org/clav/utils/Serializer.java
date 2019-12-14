package org.clav.utils;


import java.io.*;


//From StackOverFlow
public class Serializer {
	public static Object fromBytes(byte[] bytes){
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		try {
			ObjectInputStream objIn = new ObjectInputStream(in);
			return objIn.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("ERROR READING BYTES");
			e.printStackTrace();
			return new Object();
		}
	}
	public static byte[] toBytes(Object obj){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeObject(obj);
			objOut.flush();
			byte[] res= out.toByteArray();
			return res;
		} catch (IOException e) {
			System.out.println("ERROR WRITING BYTES");
			e.printStackTrace();
			return new byte[0];
		}
	}
}
