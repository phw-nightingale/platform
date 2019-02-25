package cn.giit.platform.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
@ServerEndpoint("/websocket/{sid}")
public class WebSocketServer {
    /**
     * 接受的sid
     */
    private String sid;
    /**
     * 与某个客户端的链接会话
     * 通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 当前在线人数
     */
    private static int onlineCount = 0;
    /**
     * ConCurrent包的线程安全Set
     * 用来存放每个客户端对应的MyWebsocket对象
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        this.sid = sid;
        webSocketSet.add(this);
        addOnlineCount();
        sendInfo(null, "有新链接加入，当前在线人数: " + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info("收到来自窗口" + sid + "的消息:" + message);
        for (WebSocketServer item : webSocketSet) {
            item.sendMessage(message);
        }
    }

    @OnClose
    public void onClose() throws IOException {
        webSocketSet.remove(this);
        subOnlineCount();
        sendInfo(null, "有1个链接已关闭，当前在线人数为: " + getOnlineCount());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("链接发生错误！");
        error.printStackTrace();
    }

    /**
     * 服务器主动推送
     *
     * @param message 消息内容
     * @throws IOException io exception
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     *
     * @param message 自定义消息
     * @param sid     窗口id
     */
    public static void sendInfo(String sid, String message) {
        for (WebSocketServer item : webSocketSet) {
            try {
                if (sid == null || Integer.parseInt(sid) == 0) {
                    item.sendMessage(message);  //如果sid为null，则全部发送
                } else {
                    if (item.sid.equals(sid)) {
                        log.info("推送消息到窗口" + sid + "，推送内容:" + message);
                        item.sendMessage(message);  //否则只发送对应窗口的消息
                    }
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        onlineCount--;
    }

}
