<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
	<link rel="stylesheet" href="<%=path %>/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=path %>/css/style.css">
	<script src="<%=path %>/js/jquery-2.1.4.min.js"></script>
	<script src="<%=path %>/js/bootstrap.min.js"></script>
	<script src="<%=path %>/js/term.js"></script>
	<!--[if lt IE 9]>
	  <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
	  <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
<title>Web SSH Terminal</title>
<script type="text/javascript">
document.onkeydown = function (e) {
    var ev = window.event || e;
    var code = ev.keyCode || ev.which;
    if (code == 116) {
        if(ev.preventDefault) {
            ev.preventDefault();
        } else {
            ev.keyCode = 0;
            ev.returnValue = false;
        }
    }
}
</script>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="../common/header.jsp"></jsp:include>
		<div class="blank"></div>
		<div class="container">
			<div class="col-md-9">
				<div class="panel panel-success">
					<div class="panel-body" style="color: red;min-height:600px;background: black;padding: 0;overflow: hidden">
						<div id="content">
							<div id="term"></div>
							<div style="alert alert-success">
								<textarea class="form-control" id="cmd" style="background:#FFFF99;resize:none;border-radius: 0;"></textarea>
								<span class="pull-left" style="margin-top: 6px"><s:text name="openshellterminal_special_character"></s:text>↑↑↑&nbsp;</span>
								<button type="button" style="border-radius: 0;" class="btn btn-success pull-right" id="send"><s:text name="openshellterminal_btn_send"></s:text></button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="push"></div>
	</div>
	<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
<s:if test="error==null">
<script type="text/javascript">
	$('#send').click(function(){
        $cmd = $('#cmd').val();
        send($cmd);
	})

    function send (cmd){
        term.write(cmd + "\r");
        pendingCmd="";
        webSocket.send(cmd);
        $('#cmd').val("");
    }
	
	$('#stop').click(function(){
		webSocket.send("exit");
		term.write("\nConnection closed.");
	})
	
	var webSocket = 
		new WebSocket("ws://<%=request.getServerName()+":"+request.getServerPort()+path%>/term");
	
	webSocket.onerror = function(event) {
		onError(event);
	};
	
	webSocket.onopen = function(event) {
		onOpen(event)
	};
	
	webSocket.onmessage = function(event) {
		onMessage(event)
	};
	
	webSocket.onclose = function(event) {
		onClose(event)
	};
	
	var rows = -Math.floor(346/14);
	
	function onMessage(event) {
		term.write(event.data);
	}
	
	function onOpen(event) {
		
	}
	
	function onError(event) {
		$.get('stopConnection');
	}
	
	function onClose(event) {
		$.get('stopConnection');
	}

    $("#cmd").keyup(function (event) {
        switch (event.keyCode) {
            case 13:     //enter key
                $cmd = $('#cmd').val();
                send($cmd);
                break;
        }
    });
	
	$width = $('.panel-body').width();
	$height = $('.panel-body').height();
	var term = new Terminal({
	    cols: Math.floor($width / 7.25),
	    rows: Math.floor($height / 17.42),
	    screenKeys: false,
        useStyle: true,
        cursorBlink: true,
        convertEol: true
	  });
	term.open($("#term").empty()[0]);
    var pendingCmd="";
	term.on('data', function(data) {
//        if (data == '\x08'){
//            pendingCmd = pendingCmd.substring(0,pendingCmd.length-1);
//            return;
//        }
        if (data =="\r"){
            term.write('\n');
            webSocket.send(pendingCmd);
            pendingCmd="";
            return;
        }
        term.write(data);
        pendingCmd = pendingCmd + data;

	});
</script>
</s:if>
<s:else>
	<script>
		$('#content').html("<s:property value="error"/>");
	</script>
</s:else>
</html>