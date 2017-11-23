//package cn.lhzs.common.util;
//
//import org.apache.http.*;
//import org.apache.http.client.HttpRequestRetryHandler;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpRequestBase;
//import org.apache.http.client.protocol.HttpClientContext;
//import org.apache.http.config.Registry;
//import org.apache.http.config.RegistryBuilder;
//import org.apache.http.conn.ConnectTimeoutException;
//import org.apache.http.conn.routing.HttpRoute;
//import org.apache.http.conn.socket.ConnectionSocketFactory;
//import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
//import org.apache.http.conn.socket.PlainConnectionSocketFactory;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
//import javax.net.ssl.SSLException;
//import javax.net.ssl.SSLHandshakeException;
//import java.io.IOException;
//import java.io.InterruptedIOException;
//import java.io.UnsupportedEncodingException;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// * HttpClient工具类
// *
// * @author SHANHY
// * @author sonic.liu
// */
//public class HttpClientUtil {
//
//    /**
//     * 设置5s超时
//     */
//    static final int TIME_OUT = 5;
//
//    private static CloseableHttpClient httpClient = null;
//
//    private final static Object syncLock = new Object();
//
//    private static void config(HttpRequestBase httpRequestBase, int connectRequestTimeout, int connectTimeout,int socketTimeout) {
//        // 设置Header等
//        // httpRequestBase.setHeader("User-Agent", "Mozilla/5.0");
//        // httpRequestBase
//        // .setHeader("Accept",
//        // "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        // httpRequestBase.setHeader("Accept-Language",
//        // "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");// "en-US,en;q=0.5");
//        // httpRequestBase.setHeader("Accept-Charset",
//        // "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7");
//
//        // 配置请求的超时设置
//        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectRequestTimeout*1000)
//                .setConnectTimeout(connectTimeout*1000).setSocketTimeout(socketTimeout*1000).build();
//        httpRequestBase.setConfig(requestConfig);
//    }
//
//    private static void config(HttpRequestBase httpRequestBase, int allTimeout) {
//        config(httpRequestBase, allTimeout, allTimeout, allTimeout);
//    }
//
//    private static void config(HttpRequestBase httpRequestBase) {
//        config(httpRequestBase, TIME_OUT);
//    }
//
//    /**
//     * 获取HttpClient对象
//     */
//    public static CloseableHttpClient getHttpClient(String url) {
//        String hostname = url.split("/")[2];
//        int port = 80;
//        if (hostname.contains(":")) {
//            String[] arr = hostname.split(":");
//            hostname = arr[0];
//            port = Integer.parseInt(arr[1]);
//        }
//        if (httpClient == null) {
//            synchronized (syncLock) {
//                if (httpClient == null) {
//                    httpClient = createHttpClient(200, 40, 100, hostname, port);
//                }
//            }
//        }
//        return httpClient;
//    }
//
//    /**
//     * 创建CloseableHttpClient对象，采用重试机制，默认重试三次
//     * @param maxTotal 最大连接数增加
//     * @param maxPerRoute 将每个路由基础的连接增加
//     * @param maxRoute 将目标主机的最大连接数增加
//     * @param hostname 主机IP
//     * @param port 主机端口
//     * @return
//     */
//    public static CloseableHttpClient createHttpClient(int maxTotal, int maxPerRoute, int maxRoute, String hostname, int port) {
//        return createHttpClient(maxTotal, maxPerRoute, maxRoute, hostname, port, 3);
//    }
//
//    /**
//     * 创建HttpClient对象
//     * @param maxTotal 最大连接数增加
//     * @param maxPerRoute 将每个路由基础的连接增加
//     * @param maxRoute 将目标主机的最大连接数增加
//     * @param hostname 主机IP
//     * @param port 主机端口
//     * @param times 重试次数
//     * @return
//     */
//    public static CloseableHttpClient createHttpClient(int maxTotal, int maxPerRoute, int maxRoute, String hostname,
//                                                       int port, int times) {
//        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
//        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
//        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
//                .register("http", plainsf).register("https", sslsf).build();
//        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
//        cm.setMaxTotal(maxTotal);
//        cm.setDefaultMaxPerRoute(maxPerRoute);
//        HttpHost httpHost = new HttpHost(hostname, port);
//        cm.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);
//
//        // 请求重试处理
//        HttpRequestRetryHandler httpRequestRetryHandler = (exception, executionCount, context) -> {
//            if (executionCount >= times) {// 如果已经重试了次数，就放弃
//                return false;
//            }
//            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
//                return true;
//            }
//            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
//                return false;
//            }
//            if (exception instanceof InterruptedIOException) {// 超时
//                return false;
//            }
//            if (exception instanceof UnknownHostException) {// 目标服务器不可达
//                return false;
//            }
//            if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
//                return false;
//            }
//            if (exception instanceof SSLException) {// SSL握手异常
//                return false;
//            }
//
//            HttpClientContext clientContext = HttpClientContext.adapt(context);
//            HttpRequest request = clientContext.getRequest();
//            // 如果请求是幂等的，就再次尝试
//            if (!(request instanceof HttpEntityEnclosingRequest)) {
//                return true;
//            }
//            return false;
//        };
//
//        return HttpClients.custom().setConnectionManager(cm)
//                .setRetryHandler(httpRequestRetryHandler).build();
//
//    }
//
//    private static void setPostParams(HttpPost httpost, Map<String, Object> params) {
//        List<NameValuePair> nvps = new ArrayList<>();
//        Set<String> keySet = params.keySet();
//        for (String key : keySet) {
//            nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
//        }
//        try {
//            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * POST请求URL获取内容
//     *
//     * @param url
//     * @return
//     */
//    public static String post(String url, Map<String, Object> params) throws IOException {
//        HttpPost httppost = new HttpPost(url);
//        config(httppost);
//        setPostParams(httppost, params);
//        return execute(url, httppost);
//    }
//
//    /**
//     * POST请求URL获取内容
//     * @param url 请求地址
//     * @param json 传递参数json
//     * @param connectRequestTimeout 从连接池获取连接的timeout,单位秒
//     * @param connectTimeout 和服务器建立连接的timeout,单位秒
//     * @param socketTimeout 从服务器读取数据的timeout,单位秒
//     * @return String结果
//     * @throws IOException
//     */
//    public static String postJson(String url, String json, int connectRequestTimeout, int connectTimeout,int socketTimeout) throws IOException {
//        HttpPost httppost = new HttpPost(url);
//        config(httppost, connectRequestTimeout, connectTimeout, socketTimeout);
//        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
//        // 将请求实体设置到httpPost对象中
//        httppost.setEntity(stringEntity);
//        return execute(url, httppost);
//    }
//
//    /**
//     * POST请求URL获取内容
//     * @param url 请求地址
//     * @param json 传递参数json
//     * @param allTimeout 超时秒数
//     * @return String结果
//     * @throws IOException
//     */
//    public static String postJson(String url, String json, int allTimeout) throws IOException {
//        return postJson(url, json, allTimeout, allTimeout, allTimeout);
//    }
//
//    /**
//     * POST请求URL获取内容
//     * @param url 请求地址
//     * @param json 传递参数json
//     * @return
//     * @throws IOException
//     */
//    public static String postJson(String url, String json) throws IOException {
//        return postJson(url, json, TIME_OUT);
//    }
//
//
//    private static String execute(String url, HttpRequestBase requestBase) throws IOException{
//        try (CloseableHttpResponse response = getHttpClient(url).execute(requestBase, HttpClientContext.create())) {
//            HttpEntity entity = response.getEntity();
//            String result = EntityUtils.toString(entity, "utf-8");
//            EntityUtils.consume(entity);
//            return result;
//        } catch (IOException e) {
//            throw e;
//        }
//    }
//
//    /**
//     * GET请求URL获取内容
//     *
//     * @param url
//     * @return
//     */
//    public static String get(String url) {
//        HttpGet httpget = new HttpGet(url);
//        config(httpget);
//        try (CloseableHttpResponse response = getHttpClient(url).execute(httpget, HttpClientContext.create())) {
//            HttpEntity entity = response.getEntity();
//            String result = EntityUtils.toString(entity, "utf-8");
//            EntityUtils.consume(entity);
//            return result;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}