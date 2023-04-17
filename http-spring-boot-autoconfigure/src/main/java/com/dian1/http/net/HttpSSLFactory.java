package com.dian1.http.net;

import cn.hutool.core.net.SSLContextBuilder;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ssl.SSLSocketFactoryBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author zhangzhi

 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HttpSSLFactory extends SSLSocketFactory {

    public static final HttpSSLFactory SSL_FACTORY = new HttpSSLFactory();

    private SSLSocketFactory base;

    private String[] protocols;

    public HttpSSLFactory(String... protocols) {
        this(protocols, null);
    }

    public HttpSSLFactory(String[] protocols, SSLSocketFactory base) {
        this.protocols = protocols;
        this.base = base;
        //兼容android低版本SSL连接
        if (null == protocols && StrUtil.equalsIgnoreCase("dalvik", System.getProperty("java.vm.name"))) {
            this.protocols = new String[]{SSLSocketFactoryBuilder.SSLv3, SSLSocketFactoryBuilder.TLSv1,
                    SSLSocketFactoryBuilder.TLSv11, SSLSocketFactoryBuilder.TLSv12
            };
        }
        if (null == base) {
            this.base = SSLContextBuilder.create().setTrustManagers(TrustManager.INSTANCE).buildQuietly().getSocketFactory();
        }
    }


    @Override
    public String[] getDefaultCipherSuites() {
        return base.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return base.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket() throws IOException {
        final SSLSocket sslSocket = (SSLSocket) base.createSocket();
        resetProtocols(sslSocket);
        return sslSocket;
    }

    @Override
    public SSLSocket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        final SSLSocket socket = (SSLSocket) base.createSocket(s, host, port, autoClose);
        resetProtocols(socket);
        return socket;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        final SSLSocket socket = (SSLSocket) base.createSocket(host, port);
        resetProtocols(socket);
        return socket;
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
        final SSLSocket socket = (SSLSocket) base.createSocket(host, port, localHost, localPort);
        resetProtocols(socket);
        return socket;
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        final SSLSocket socket = (SSLSocket) base.createSocket(host, port);
        resetProtocols(socket);
        return socket;
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        final SSLSocket socket = (SSLSocket) base.createSocket(address, port, localAddress, localPort);
        resetProtocols(socket);
        return socket;
    }

    /**
     * 重置可用策略
     *
     * @param socket SSLSocket
     */
    private void resetProtocols(SSLSocket socket) {
        if (ArrayUtil.isNotEmpty(this.protocols)) {
            socket.setEnabledProtocols(this.protocols);
        }
    }

}
