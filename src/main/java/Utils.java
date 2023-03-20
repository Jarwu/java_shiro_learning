import java.io.*;

public class Utils {
        public static void serialize(Object obj) throws IOException {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ser.bin"));
            oos.writeObject(obj);
        }

        public static Object unSerialize(String Filename) throws IOException, ClassNotFoundException{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Filename));
            return ois.readObject();
        }

        public static void EXP() throws Exception{
            Process proc = Runtime.getRuntime().exec(new String[]{"python","C:\\Users\\yyds\\PycharmProjects\\des\\main.py","C:\\Users\\yyds\\IdeaProjects\\shiroExp\\ser.bin"});
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        }
}
