import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

/**
 * @ http://api.map.baidu.com/geocoder/v2/?output=json&ak="+ak+"&address="+encode_Address
 */

public class AdressTojd {

    public void doAddressToid(String ak, String address) {
        String encode_Address = "";
        try {
            encode_Address = URLEncoder.encode(address, "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        URI uri = null;
//


       // HttpGet httpGet = new HttpGet("http://api.map.baidu.com/geocoder/v2/?output=json&ak="+ak+"&address="+"西安钟楼");

        CloseableHttpResponse response =null;
        try {
            try {
                uri=new URIBuilder().setScheme("http").setHost("api.map.baidu.com/geocoder/v2/").setParameter("output","json").setParameter("ak",ak).setParameter("address",address).build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            HttpGet httpGet = new HttpGet(uri);
            response=httpClient.execute(httpGet);

            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();
            httpGet.setConfig(requestConfig);

            HttpEntity httpEntity = response.getEntity();
            System.out.println("响应状态——>"+response.getStatusLine());
            String data ="";
            if (httpEntity!=null){
                data=EntityUtils.toString(httpEntity);
                System.out.println("响应内容为-->"+ data);
                JSONObject jsonObject = JSONObject.parseObject(data);
                System.out.println("精度"+jsonObject.getJSONObject("result").getJSONObject("location").getString("lng"));
                System.out.println("维度"+jsonObject.getJSONObject("result").getJSONObject("location").getString("lat"));

                //System.out.println("响应内容长度为:" + httpEntity.getContentLength());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
