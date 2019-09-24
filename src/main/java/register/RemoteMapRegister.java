package register;

import framework.URL;

import java.io.*;
import java.util.*;

public class RemoteMapRegister {
    private static Map<String, List<URL>> map = new HashMap<>();

    public static void register(String interfaceName, URL url) {
        if (!map.containsKey(interfaceName)) {
            map.put(interfaceName, Collections.singletonList(url));
        }else {
            map.get(interfaceName).add(url);
        }
        saveFile();
    }

    public static List<URL> get(String interfaceName) {
        map = getFile();
        return map.get(interfaceName);
    }

    /**
     * 写入文本
     */
    public static void saveFile(){
        try {
            FileOutputStream fos = new FileOutputStream("D://register.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            oos.flush();
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取文本
     * @return
     */
    public static Map<String, List<URL>> getFile(){
        try {
            FileInputStream fis = new FileInputStream("D://register.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (Map<String, List<URL>>)ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
