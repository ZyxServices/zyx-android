//package com.gymnast.utils;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpDelete;
//import org.apache.http.cookie.Cookie;
//import org.apache.http.entity.mime.Header;
//import org.apache.http.impl.client.DefaultHttpClient;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.CookieManager;
//import java.net.URL;
//
///**
// * Created by Cymbi on 2016/9/4.
// */
//public class DeleteUtil {
//
//    /**
//     * Delete基础请求
//     *
//     * @param url 请求地址
//     * @return 请求成功后的结果
//     */
//    public static byte[] doDelete(String url) {
//
//        InputStream in;
//        byte[] bre = null;
//        HttpResponse response;
//        CookieManager manager = CookieManager.getInstance();
//        if (url != null && url.length() != 0) {
//            URL myurl = URL.parseString(url);
//            Cookie[] cookies = manager.getCookies(myurl);
//            HttpDelete delete = new HttpDelete(url);
//            if (cookies != null && cookies.length > 0) {
//                StringBuilder sb = new StringBuilder();
//                for (Cookie ck : cookies) {
//                    sb.append(ck.name).append('=').append(ck.value).append(";");
//                }
//                String sck = sb.toString();
//                if (sck.length() > 0) {
//                    delete.setHeader("Cookie", sck);
//                }
//            }
//            delete.setHeader("Accept-Encoding", "gzip, deflate");
//            delete.setHeader("Accept-Language", "zh-CN");
//            delete.setHeader("Accept", "application/json, application/xml, text/html, text/*, image/*, */*");
//            try {
//                response = new DefaultHttpClient().execute(delete);
//                if (response != null) {
//                    int statusCode = response.getStatusLine().getStatusCode();
//                    if (statusCode == 200 || statusCode == 403) {
//                        Header[] headers = response.getHeaders("Set-Cookie");
//                        if (headers != null && headers.length > 0) {
//                            for (Header header : headers) {
//                                manager.setCookie(myurl, header.getValue());
//                            }
//                        }
//                        in = response.getEntity().getContent();
//                        if (in != null) {
//                            bre = ResourceUtil.readStream(in);
//                        }
//
//                    }
//                }
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        return bre;
//    }
//
//}
