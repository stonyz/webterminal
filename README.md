Web Terminal
====================
The tool is to facilitate the java developer to explorer the files on the server by the shell command, and also can execute some commands. NOT all, only some, such as *top*, which isn't supported. We may add more later.

It's a web project built by Maven. Spring and Struts are the main parts. The front-end is built by bootstrap. We use websocket to communicate, so please make sure your web container support websocket.

The core code to execute shell command is below,

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

From the code above, we also know why can't some command like *top*. If you have any good idea, please let me know.

The frond end use the terminal javascript library https://github.com/chjj/term.js, which is very cool and easy to use. 

	var term = new Terminal({
	    cols: Math.floor($width / 7.25),
	    rows: Math.floor($height / 17.42),
	    screenKeys: false,
        useStyle: true,
        cursorBlink: true,
        convertEol: true
	  });
To Know more detail about term.js, please go the officail website.
How to use
-------------
The default username and password is stonyz@gmail.com/123456, which is defined in the file WEB-INF/users.properties. You can change it, or add more users if you want.

Prerequisites
-------------
* Java JDK 1.7 or greater
http://www.oracle.com/technetwork/java/javase/overview/index.html

* Tomcat server 7.0 or greater
https://tomcat.apache.org

* Browser with Web Socket support
http://caniuse.com/websockets

Screenshots
-----------
![Login](https://github.com/stonyz/webterminal/raw/master/screenshots/login.png)

![Terminal](https://github.com/stonyz/webterminal/raw/master/screenshots/terminal.png)

