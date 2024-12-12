/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

/**
 *
 * @author Windows
 */
public class serialization {

    public serialization() {
    }
    
    public String ObjecttoString(Object obj) throws IOException {
        byte[] request = serialize(obj);
        String str = Base64.getEncoder().encodeToString(request);
        return str;
    }
    
    public Object StringtoObject(String str) throws IOException, ClassNotFoundException {
        byte[] request = Base64.getDecoder().decode(str);
        Object obj = deserialize(request);
        return obj;
    }

    public byte[] serialize(Object obj) throws IOException {
        byte[] byteArray;
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream(); 
                ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
            objectStream.writeObject(obj);
            objectStream.flush();
            byteArray = byteStream.toByteArray();
        }
        return byteArray;
    }

    public Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        Object obj;
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(data); 
                ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {
            obj = objectStream.readObject();
        }
        return obj;
    }
}
