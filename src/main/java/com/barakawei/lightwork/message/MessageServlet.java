package com.barakawei.lightwork.message;

import com.barakawei.lightwork.util.UserContextUtil;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class MessageServlet extends WebSocketServlet {
    private static final long serialVersionUID = 1L;
    private final Set<DemoMessageInbound> connections = new CopyOnWriteArraySet<DemoMessageInbound>();

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,
                                                   HttpServletRequest request) {
        return new DemoMessageInbound();
    }

    private final class DemoMessageInbound extends MessageInbound {
        @Override
        protected void onOpen(WsOutbound outbound) {
            connections.add(this);
        }

        @Override
        protected void onClose(int status) {
            connections.remove(this);
        }

        @Override
        protected void onBinaryMessage(ByteBuffer message) throws IOException {
            //在这里处理二进制数据
        }

        @Override
        protected void onTextMessage(CharBuffer message) throws IOException {
            // 这里处理的是文本数据
            String filteredMessage = message.toString();
            while(true){
                String msg = UserContextUtil.notifyMessage();
                broadcast(msg);
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        //将数据传回客户端
        private void broadcast(String message) {
            for (DemoMessageInbound connection : connections) {
                try {
                    CharBuffer buffer = CharBuffer.wrap(message);
                    connection.getWsOutbound().writeTextMessage(buffer);
                } catch (IOException ignore) {
                    // Ignore
                }
            }
        }
    }
}

