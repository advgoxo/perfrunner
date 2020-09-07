import com.jmeterx.async.AsyncFileUtils;
import com.jmeterx.async.QueueThread;
import com.jmeterx.codec.XCodec;

public class Main {
    public static void main(String args[]) throws InterruptedException {

        QueueThread thread1 = new QueueThread("A");
        thread1.start();

        thread1.add(()->{
            System.out.println("A");
        });
        thread1.add(()->{
            System.out.println("B");
        });
        thread1.add(()->{
            System.out.println("C");
        });

        thread1.safeStop();

        String s = "唯品会-小笑牛jitx";
        String b = "\\u552f\\u54c1\\u4f1a\\u002d\\u0046\\u0049\\u004c\\u0041\\u002d\\u004a\\u0049\\u0054\\u0058";

        System.out.println(b + " --转换成中文是：" + XCodec.decodeUnicode(b));
        System.out.println(s + " --Unicode编码：" + XCodec.encodeUnicode(s));

        String data = "";
        for (int i = 0; i < 1023; ++i) {
            data += "a";
        }

        long beg = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++) {
            AsyncFileUtils.writeLine("debug/a.txt", data);
        }

        Thread.sleep(20000);
        AsyncFileUtils.stop();
        System.out.println("cost = " + (System.currentTimeMillis() - beg));
    }
}