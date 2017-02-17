package com.terminal.websocket;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("sshShellHandler")
public class SshShellHandler extends TextWebSocketHandler {

    public static final Map<Integer, List<WebSocketSession>> userSocketSessionMap;
    private List<WebSocketSession> sessionList = null;

    static {
        userSocketSessionMap = new HashMap<Integer, List<WebSocketSession>>();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);

        String cmd = message.getPayload();
        System.out.println("command:" + cmd);
        String result = exec(cmd);
        if (result != null && result.length() > 0) {
            session.sendMessage(new TextMessage(result));
        }
        session.sendMessage(new TextMessage(">"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        session.sendMessage(new TextMessage(exec("pwd")));
        session.sendMessage(new TextMessage(">"));

    }

    public static String exec(String cmd) {
        if (cmd == null || cmd.trim().length()==0){
            return "";
        }
        try {
            String[] cmdA = {"/bin/sh", "-c", cmd};
            Process process = Runtime.getRuntime().exec(cmdA);
            LineNumberReader br = new LineNumberReader(new InputStreamReader(
                    process.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line).append("\n");
            }
            System.out.println("result:" + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
