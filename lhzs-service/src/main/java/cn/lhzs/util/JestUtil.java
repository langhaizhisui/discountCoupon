package cn.lhzs.util;

/**
 * Created by IBA-EDV on 2017/11/23.
 */
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.client.config.ClientConstants;

import java.util.LinkedHashSet;

public class JestUtil {

    private static JestClient JestClient;

    private static ClientConfig clientConfig() {
        String connectionUrl = "http://119.23.54.39:9200";
        ClientConfig clientConfig = new ClientConfig();
        LinkedHashSet servers = new LinkedHashSet();
        servers.add(connectionUrl);
        clientConfig.getServerProperties().put(ClientConstants.SERVER_LIST, servers);
        clientConfig.getClientFeatures().put(ClientConstants.IS_MULTI_THREADED, false);
        return clientConfig;
    }

    public static JestClient getJestClient() {
        JestClientFactory factory = new JestClientFactory();
        factory.setClientConfig(clientConfig());
        if (JestClient == null) {
            JestClient = factory.getObject();
        }
        return JestClient;
    }
}