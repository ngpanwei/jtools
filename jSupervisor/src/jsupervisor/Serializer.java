package jsupervisor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {
	public void serialize(Object object, String fileName) {
		try {
			FileOutputStream ofStream = new FileOutputStream(fileName) ;
			ObjectOutputStream oStream = new ObjectOutputStream(ofStream) ;
			oStream.writeObject(object) ;
		} catch(FileNotFoundException e) {
		} catch(IOException e) {
			
		}
	}
	public Object deserialize(String fileName) {
		String data = null ;
		try {
			FileInputStream ifStream = new FileInputStream(fileName) ;
			ObjectInputStream iStream = new ObjectInputStream(ifStream);
			data = (String)iStream.readObject();
		} catch(FileNotFoundException e) {
		} catch(IOException e) {
		} catch(ClassNotFoundException e) {
		}
		return data ;
	}
}
