//package com.barakawei.lightwork.message;
//
//import org.apache.catalina.websocket.MessageInbound;
//import org.apache.catalina.websocket.StreamInbound;
//import org.apache.catalina.websocket.WebSocketServlet;
//import org.apache.catalina.websocket.WsOutbound;
//
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.nio.CharBuffer;
//import java.util.Set;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//public class MessageServlet extends WebSocketServlet {
//    private static final long serialVersionUID = 1L;
//    private final Set<DemoMessageInbound> connections = new CopyOnWriteArraySet<DemoMessageInbound>();
//    @Override
//    protected StreamInbound createWebSocketInbound(String subProtocol,
//                                                   HttpServletRequest request) {
//        return new DemoMessageInbound();
//    }
//    private final class DemoMessageInbound extends MessageInbound {
//        @Override
//        protected void onOpen(WsOutbound outbound) {
//            connections.add(this);
//            String message = "websocket已经打开";
//            broadcast(message);
//        }
//        @Override
//        protected void onClose(int status) {
//            connections.remove(this);
//            String message = "webSocket关闭";
//            broadcast(message);
//        }
//        @Override
//        protected void onBinaryMessage(ByteBuffer message) throws IOException {
//            //在这里处理二进制数据
//        }
//        @Override
//        protected void onTextMessage(CharBuffer message) throws IOException {
//            // 这里处理的是文本数据
//            String filteredMessage = message.toString();
//            broadcast(filteredMessage);
//        }
//
//        //将数据传回客户端
//        private void broadcast(String message) {
//            for (DemoMessageInbound connection : connections) {
//                try {
//                    System.out.println(message);
//                    CharBuffer buffer = CharBuffer.wrap(message);
//                    connection.getWsOutbound().writeTextMessage(buffer);
//                } catch (IOException ignore) {
//                    // Ignore
//                }
//            }
//        }
//    }
//}

