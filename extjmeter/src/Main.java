import com.jmeterx.async.Phone;
import com.jmeterx.async.WriteRunnable;
import com.jmeterx.async.WriterThread;
import com.jmeterx.codec.XCodec;

public class Main {
    public static void main(String args[]) {
        System.out.println(Phone.randomCellphone());

        WriterThread thread1 = new WriterThread("A");
        WriterThread thread2 = new WriterThread("B");
        thread1.start();
        thread2.start();

        new Thread(new WriteRunnable("C")).start();
        new Thread(new WriteRunnable("D")).start();

        String s = "唯品会-小笑牛jitx";
        String b = "\\u552f\\u54c1\\u4f1a\\u002d\\u0046\\u0049\\u004c\\u0041\\u002d\\u004a\\u0049\\u0054\\u0058";

        System.out.println(b + " --转换成中文是："+ XCodec.decodeUnicode(b));
        System.out.println(s + " --Unicode编码："+ XCodec.encodeUnicode(s));
    }
}