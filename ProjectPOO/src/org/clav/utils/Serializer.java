package org.clav.utils;


import java.io.*;

public class Serializer {
	public static Object fromBytes(byte[] bytes){
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		try {
			ObjectInputStream objIn = new ObjectInputStream(in);
			return objIn.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Erreur reading byte");
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
			System.out.println("BUFFER SIZE : "+res.length);
			return res;
		} catch (IOException e) {
			System.out.println("ERROR WRITING BYTES");
			e.printStackTrace();
			return new byte[0];
		}
	}
}
