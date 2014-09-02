
angular.module('trader', []).factory('socket', function($rootScope) {
  var socket = new ReconnectingWebSocket("ws://"+location.host+"/socket");

  var observers = {};

  function pub(channel, message) {
    if (observers[channel]) {
      $rootScope.$apply(function() {
        observers[channel](message);
      });
    }
  }

  socket.onmessage = function(event) {
    var msg = JSON.parse(event.data);
    //console.log(msg);
    pub(msg.channel, msg.message);
  }
  socket.onopen = function(event) {
    console.log("Web Socket opened!");
    pub("connect", {});
  };
  socket.onclose = function(event) {
    console.log("Web Socket closed.");
    pub("disconnect", {});
  };

  return {
    on : function(channel, callback) {
      observers[channel] = callback;
    },
    emit : function(channel, data) {
      //console.log(data);
      socket.send(JSON.stringify({
        channel : channel,
        msg : data
      }));
    },
    isConnected : function() {
      return socket.readyState == WebSocket.OPEN;
    }
  };
  
  
}).controller('TraderCtrl', function($scope, socket, $interval,$http) {
  "use strict";

  
/////////////////////////Charting Disaster Area//////////////////////////////
  var canvas = document.getElementById("c");
  var ctx = canvas.getContext("2d");
  var lastCharting = new Date().getTime();
  var lastPrices = {};
  function doChart(prices){
    if(new Date().getTime() - lastCharting > 1000){
      lastCharting = new Date().getTime();
    } else {
      return;
    }
    var width = ctx.canvas.width;
    var height = ctx.canvas.height;
    
    var imageData = ctx.getImageData(1, 0, width - 1, height);
    ctx.putImageData(imageData, 0, 0);
    ctx.clearRect(width - 1, 0, 1, height);
    
    ctx.lineWidth = 1;
    ctx.fillStyle = "silver";
    ctx.fillRect(width - 2, height, 1, 1)
    ctx.fillRect(width - 2, height - 100, 1, 1)
    ctx.fillRect(width - 2, height - 200, 1, 1)
    ctx.fillRect(width - 2, height - 300, 1, 1)
    ctx.fillRect(width - 2, height - 400, 1, 1)
    
    ctx.lineWidth = 2;
    var currentPrices = {};
    for(var key in prices){
      var price = prices[key];
      var color = $scope.emoticonMap[price.emoticon].color
      if(color == undefined){
        color = "white";
      } 
      ctx.strokeStyle = color;
      ctx.beginPath();
      ctx.moveTo(width - 2, height - lastPrices[price.emoticon]);
      ctx.lineTo(width - 1, height - price.price);
      ctx.stroke();
      
      currentPrices[price.emoticon] = price.price;
    }
    lastPrices = currentPrices;
    
  }
///////////////////////////////////////////////////////////////////////////
  
  
  
  
  
  $http.get('/emoticons.json').success(function(data){
    var emoticonMap = {};
    for(var key in data.emoticons){
      var emoticon = data.emoticons[key];
      if(emoticon.images[0].emoticon_set == undefined){
        emoticonMap[emoticon.regex.toLowerCase()] = emoticon;
      }
    }
    $scope.emoticonMap = emoticonMap;
  });

  var count = 0;
  var time = new Date().getTime();
  $interval(function(){
    if(socket.isConnected()){
      socket.emit("action","getPrices");
      count++;
      if(new Date().getTime() - time > 1000){
        console.log(count);
        count = 0;
        time = new Date().getTime();
      }
    }
  }, 0);

  socket.on('marketStatus', function(status) {
    $scope.marketStatus = status;
  });
  
  socket.on('prices', function(prices) {
    $scope.allPrices = prices;
    $scope.prices = prices.slice(0,25);
    doChart(prices.slice(0,25));
    if(prices[0].price > 250){
      // http://danielstern.github.io/ngAudio/
      $scope.spiker = prices[0];
    } else {
      delete $scope.spiker;
    }
  });
  
  socket.on('channelDatas', function(channelDatas) {
    $scope.channelDatas = channelDatas;
  });
  
  socket.on('alert',function(msg){
    alert(msg);
  });
});

