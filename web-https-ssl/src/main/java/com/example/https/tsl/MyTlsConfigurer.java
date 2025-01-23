package com.example.https.tsl;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

// 自定义TLS(传输层安全性)协议的实现
public class MyTlsConfigurer implements WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> {

    // 从ServerProperties中获取SSL
    private final ServerProperties serverProperties;

    public MyTlsConfigurer(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Override
    public void customize(ConfigurableTomcatWebServerFactory factory) {
        final Ssl ssl = this.serverProperties.getSsl();
        if (ssl != null) {
            boolean changed = false;
            if (ssl.getKeyStore() != null && !ssl.isEnabled()) {
                ssl.setEnabled(true);
                changed = true;
            }
            if (null != ssl.getTrustStore() && null == ssl.getClientAuth()) {
                ssl.setClientAuth(Ssl.ClientAuth.NEED);
                changed = true;
            }
            if (changed) {
                factory.setSsl(ssl);
            }
        }
    }
}
