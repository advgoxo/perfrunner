import com.jmeterx.async.AsyncFileUtils;
import com.jmeterx.async.QueueThread;
import com.jmeterx.codec.XCodec;
import com.jmeterx.oms.Goods;

public class Main {
    public static void main(String args[]) throws InterruptedException {
        /*
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
        for (int i = 0; i < 16; ++i) {
            data += "中国万岁";
        }

        long beg = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            AsyncFileUtils.writeLine("debug/a.txt", data);
        }

        Thread.sleep(2000);
        AsyncFileUtils.stop();
        System.out.println("cost = " + (System.currentTimeMillis() - beg));
         */

        String data = "{\"status\":1300,\"data\":{\"html\":\"\\n<tr id=\\\"goods_6318018\\\" class=\\\"\\\">\\n\\t\\t<td>\\n\\t\\t<input type=\\\"checkbox\\\" value=\\\"6318018\\\" name=\\\"goods_id_checkbox\\\" class=\\\"goods_id_checkbox\\\">\\n\\t\\t91845508-2\\t\\t<input type=\\\"hidden\\\" value=\\\"91845508-2\\\" id=\\\"goods_sn_6318018\\\" \\/>\\n\\t<\\/td>\\n\\t\\t<td>6901664812487<\\/td>\\n\\t\\t        \\t\\n\\t\\t<td>\\n\\t<span title=\\\"\\u7537\\u8dd1\\u978b,6.5-10.5\\\">\\u7537\\u8dd1\\u978b,6.5-10.5<\\/span>\\n\\t<\\/td>\\n\\t\\t<!--\\u662f\\u5426\\u542f\\u7528 \\u5168\\u5c40\\u5546\\u54c1\\u542f\\u7528\\u552f\\u4e00\\u7801-->\\n\\t<!--\\n\\t\\t -->\\n\\t    \\t<td>\\n\\t\\t<select name=\\\"color_id\\\" id=\\\"color_id_6318018\\\" onchange=\\\"set_goods_price('6318018', 'color');\\\">\\n\\t\\t\\t\\t\\t\\t<option value=\\\"32266\\\" >\\u9ed1\\/\\u5b89\\u8e0f\\u767d(91748991-2)<\\/option>\\n\\t\\t\\t\\t\\t\\t<option value=\\\"95141\\\" selected>\\u9ed1\\/\\u5b89\\u8e0f\\u767d(91845508-2)<\\/option>\\n\\t\\t\\t\\t\\t<\\/select>\\n\\t<\\/td>\\n\\t<td>\\n\\t\\t<select name=\\\"size_id\\\" id=\\\"size_id_6318018\\\" onchange=\\\"set_goods_price('6318018', 'size');\\\">\\n\\t\\t\\t\\t\\t\\t<option value=\\\"52\\\" selected>10.5(10.5)<\\/option>\\n\\t\\t\\t\\t\\t\\t<option value=\\\"32\\\" >6.5(6.5)<\\/option>\\n\\t\\t\\t\\t\\t\\t<option value=\\\"37\\\" >7(7)<\\/option>\\n\\t\\t\\t\\t\\t\\t<option value=\\\"46\\\" >7.5(7.5)<\\/option>\\n\\t\\t\\t\\t\\t\\t<option value=\\\"42\\\" >8(8)<\\/option>\\n\\t\\t\\t\\t\\t\\t<option value=\\\"49\\\" >8.5(8.5)<\\/option>\\n\\t\\t\\t\\t\\t\\t<option value=\\\"48\\\" >9(9)<\\/option>\\n\\t\\t\\t\\t\\t\\t<option value=\\\"40\\\" >9.5(9.5)<\\/option>\\n\\t\\t\\t\\t\\t<\\/select>\\n\\t<\\/td>\\n\\t\\t<td><input type=\\\"text\\\" id=\\\"goods_number_6318018\\\" name=\\\"goods_number\\\" value=\\\"1\\\" size=\\\"2\\\" onkeyup=\\\"set_goods_price('6318018');\\\" \\/>\\n\\t<a href=\\\"###\\\" class=\\\"one_goods_icon\\\"  title=\\\"\\\"><span>\\n\\t(0\\/0\\/0)<\\/span><\\/a><\\/td>\\n\\t<\\/td>\\n    \\t<td id=\\\"market_price_6318018\\\">599.00<\\/td>\\n    \\n           \\t<td>\\n\\t\\t\\t<input type=\\\"text\\\"   id=\\\"shop_price_6318018\\\" value=\\\"599.00\\\" size=\\\"5\\\" \\/>\\n\\t\\t\\t\\t<\\/td>\\n    \\t\\t<td>\\n\\t\\t\\t\\t\\t<input type=\\\"text\\\"  id=\\\"discount_6318018\\\" value=\\\"0.3973\\\" size=\\\"4\\\"\\n\\tonkeyup=\\\"set_discount_goods_price('6318018');\\\" \\/><\\/td>\\n\\t\\t\\t\\t\\t<td>\\n\\t\\t\\t<input type=\\\"text\\\"       id=\\\"goods_price_6318018\\\" value=\\\"238.00\\\" size=\\\"5\\\"\\n\\tonkeyup=\\\"set_goods_price('6318018');\\\" \\/>\\n\\t\\t\\t<\\/td>\\n\\t\\t\\t<td> <input type=\\\"text\\\"   id=\\\"xj_price_6318018\\\" value=\\\"238.00\\\" size=\\\"2\\\"\\n\\t onkeyup = \\\"set_xj_price('6318018');\\\" \\/><\\/td>\\n\\t            <!-- \\u5b89\\u8e0f\\u8981\\u6c42\\u53bb\\u6389<td>\\n\\t\\u5173\\u7a0e\\uff1a0.00<br\\/>\\n\\t\\u589e\\u503c\\uff1a0.00<br\\/>\\n\\t\\u6d88\\u8d39\\uff1a0.00<br\\/>\\n\\t\\u7efc\\u5408\\uff1a0.00<br\\/>\\n\\t<\\/td>-->\\n\\t        \\t\\t<td>\\n\\n\\t\\t\\t\\t\\t<select name=\\\"is_gift\\\"   id=\\\"is_gift_6318018\\\" onchange=\\\"set_goods_is_gift('6318018',this.value);\\\">\\n\\t\\t\\t\\t<option value=\\\"0\\\" selected>\\u5426<\\/option>\\n\\t\\t\\t\\t<option value=\\\"1\\\" >\\u662f<\\/option>\\n\\t\\t\\t<\\/select>\\n\\t\\t\\t<\\/td>\\n    <td><\\/td>\\n\\t\\t<td>0<\\/td>\\n\\t    \\t\\n\\t<td>\\n        <div class=\\\"goods_but1\\\">\\n            <div class=\\\"goods_but_01\\\"><\\/div>\\n            <div class=\\\"goods_but_02\\\"><a href=\\\"###\\\" title=\\\"\\\" onclick=\\\"goods_remark(this, '6318018');\\\">\\u5907\\u6ce8<\\/a><\\/div>\\n            <div class=\\\"goods_but_03\\\"><\\/div>\\n        <\\/div>\\n\\t\\t<div class=\\\"goods_but1\\\">\\n\\t\\t\\t<div class=\\\"goods_but_01\\\"><\\/div>\\n\\t\\t\\t<div class=\\\"goods_but_02\\\"><a href=\\\"###\\\" onclick=\\\"delete_goods('6318018');\\\">\\u5220\\u9664<\\/a><\\/div>\\n\\t\\t\\t<div class=\\\"goods_but_03\\\"><\\/div>\\n\\t\\t<\\/div>\\n\\t\\t\\t\\t\\t<div class=\\\"goods_but1\\\">\\n\\t\\t\\t\\t<div class=\\\"goods_but_01\\\"><\\/div>\\n\\t\\t\\t\\t<div class=\\\"goods_but_02\\\"><a href=\\\"###\\\" onclick=\\\"save_goods('6318018');\\\">\\u4fdd\\u5b58<\\/a><\\/div>\\n\\t\\t\\t\\t<div class=\\\"goods_but_03\\\"><\\/div>\\n\\t\\t\\t<\\/div>\\n\\t\\t\\t<\\/td>\\n<\\/tr>\\n<script>\\n\\t$(function(){\\n\\t\\t$('.one_goods_icon').each(function(){\\n\\t\\t\\t$(this).bind('mouseover', function(e){titleToNote.showNote(this, e);});\\n\\t\\t\\t$(this).bind('mousemove', function(e){titleToNote.moveNote(this, e);});\\n\\t\\t\\t$(this).bind('mouseout', function(e){titleToNote.hideNote(this, e);});\\n\\t\\t});\\n\\t});\\n<\\/script>\",\"old_order_goods\":null,\"notice\":0},\"global_request_info\":{\"request_id\":\"432200916132848.4312.25025\",\"app_name\":\"webopm\",\"app_act\":\"order\\/order_goods\\/edit\",\"context_start\":1600234128428,\"context_end\":1600234128443,\"app_start\":1600234128428,\"app_end\":1600234128486,\"db_start\":1600234128463,\"db_end\":1600234128463,\"action_start\":1600234128473,\"action_end\":1600234128486,\"render_start\":1600234128486,\"render_end\":1600234128486,\"data_start\":0,\"data_end\":0,\"datarender_start\":0,\"datarender_end\":0,\"duration\":{\"context\":15,\"app\":58,\"db\":0,\"action\":13,\"render\":0,\"data\":0,\"datarender\":0},\"ext\":[],\"memory\":{\"memory_start\":650544,\"memory_boot\":9050272,\"memory_boot_peak\":9757944,\"memory_end\":9905152,\"memory_peak\":10783080,\"memory_use\":8.825882,\"memory_use_peak\":9.663139,\"memory_use_boot\":8.685493}}}";
        System.out.println("value=" + Goods.getSizeId(data));
        System.out.println("value=" + Goods.getColorId(data));
    }
}