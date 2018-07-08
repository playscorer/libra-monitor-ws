var stompClient = null;

function setConnected(connected) {
	document.getElementById('disconnect').disabled = !connected;
	document.getElementById('logs').style.visibility = connected ? 'visible'
			: 'hidden';
	document.getElementById('logs').innerHTML = '';
}

/*function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}*/

function connect() {
    var socket = new SockJS('/monitor');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/logs', function (messageOutput) {
            showMessageOutput(messageOutput.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


function showMessageOutput(message) {
/*    $("#greetings").append("<tr><td>" + message + "</td></tr>");*/
    $("#logs").append(message);
/*	var div = document.getElementById('logs');
	div.insertAdjacentHTML('beforeend', message);*/
}

/*$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});*/
