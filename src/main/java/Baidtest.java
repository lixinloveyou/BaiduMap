import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLOutput;

/**
 * @author Lx
 *
 * @通过地址首先先获取精度和维度 getCoordinate ak dR9SjyBtlRcbLGKGmbtcrdiYMM5CKKxw
 *
 *
 * @再通过纬度和精度获取城市地址 getAddr
 */

public class Baidtest {

    public  String [] getCoordinate (String addr, String ak) throws IOException{
       String jingdu =null;
       String weidu=null;
       String encode_Address= null;

       encode_Address = URLEncoder.encode(addr,"UTF-8");

       String url = "http://api.map.baidu.com/geocoder/v2/?output=json&ak="+ak+"&address="+encode_Address;
       URL myurl=null;
       URLConnection httpsConn =null;

       try {
           myurl = new URL(url);
       }catch (Exception e){
           e.printStackTrace();
       }
        InputStreamReader insr = null;
        BufferedReader br =null;
       try {

           httpsConn = (URLConnection) myurl.openConnection();
           if (httpsConn!=null){
                insr = new InputStreamReader(httpsConn.getInputStream(),"UTF-8");
                br = new BufferedReader(insr);
                String data =null;
                while ((data = br.readLine())!= null){
                    JSONObject json = JSONObject.parseObject(data);
                    jingdu = json.getJSONObject("result").getJSONObject("location").getString("lng");
                    weidu = json.getJSONObject("result").getJSONObject("location").getString("lat");
                    System.out.println(json.getJSONObject("result").getString("level"));
                }

           }

       }catch (IOException e){
           e.printStackTrace();
       }finally {
           if (insr!=null){
               insr.close();
           }
           if (br!=null){
               br.close();
           }

       }
       return  new String[]{jingdu,weidu};
    }

    public String [] getAddr(String jingdu ,String weidu, String ak) throws IOException {
        String url = "http://api.map.baidu.com/geocoder/v2/?output=json&ak="+ak+"&location="+weidu+","+jingdu;
       URL url1 = null;
        try {
          url1 = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection urlConnection =null;
        try {
            urlConnection = (URLConnection)url1.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader =null;
        try {
            inputStreamReader=new InputStreamReader(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader=null;
        bufferedReader = new BufferedReader(inputStreamReader);
        String data=null;
        String provenice ="";
        String city="";
        String district="";
        while (( data = bufferedReader.readLine())!=null){

                JSONObject jsonObject = JSONObject.parseObject(data);
                 provenice =jsonObject.getJSONObject("result").getJSONObject("addressComponent").getString("province");
                 city = jsonObject.getJSONObject("result").getJSONObject("addressComponent").getString("city");
                 district = jsonObject.getJSONObject("result").getJSONObject("addressComponent").getString("district");




        }
        String data1 [] ={provenice,city,district};
        return data1;

    }

    public static void main (String args[]) throws IOException{
        
//        String ak ="dR9SjyBtlRcbLGKGmbtcrdiYMM5CKKxw";
//        Baidtest baidtest = new Baidtest();
//        String  o [] =baidtest.getCoordinate("华为西安研究所",ak);
//        String [] p =baidtest.getAddr(o[0],o[1],ak);
//        System.out.println("精度--->"+o[0]);
//        System.out.println("维度--->"+o[1]);
//        System.out.println("省份--->"+p[0]);
//        System.out.println("城市--->"+p[1]);
//        System.out.println("县/区--->"+p[2]);
        new AdressTojd().doAddressToid("dR9SjyBtlRcbLGKGmbtcrdiYMM5CKKxw","岐山县枣林镇政府");



    }
}
