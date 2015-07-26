angular.module('trader', [ 'AngularStomp' ]).controller('TraderCtrl', function($scope, $interval, $http, ngstomp) {
  "use strict";

  var allPrices = [];
  var priceMap = {};


  $scope.messages = [];
  $scope.client = ngstomp('/socket');
  $scope.client.connect("guest", "guest", function() {
    $scope.client.subscribe("/topic/price.stock.*", function(message) {
      var priceData = JSON.parse(message.body);
      var exists = true
      if (!priceMap[priceData.emoticon]) {
        exists = false;
        priceMap[priceData.emoticon] = priceData;
      }
      priceMap[priceData.emoticon].price = priceData.price;
      var newPrice = priceMap[priceData.emoticon];

      if(exists){
        for(var i = 0; i < allPrices.length; i++){
          var price = allPrices[i];
          if(price.emoticon == newPrice.emoticon){
            allPrices.splice(i,1);
            break;
          }
        }
      }

      if (allPrices.length == 0) {
        allPrices[0] = (newPrice);
      } else {
        // TODO: binary search
        for (var i = 0; i < allPrices.length; i++) {
          var price = allPrices[i];
          if (price.price < newPrice.price) {
            allPrices.splice(i, 0, newPrice);
            break
          }
          if (i == allPrices.length - 1) {
            allPrices[allPrices.length] = (newPrice);
            break;
          }
        }
      }

      $scope.prices = allPrices.slice(0, 10);

    });
    
    $scope.client.subscribe("/topic/channelstats", function(message) {
    	var channelStats = JSON.parse(message.body);
    	$scope.channelStats = channelStats;
    });
    
    
    /*
    $scope.messages = [];
    $scope.client.subscribe("/topic/chat.*.message", function(message) {
      $scope.messages.push(JSON.parse(message.body));
      if($scope.messages.length > 10){
        $scope.messages.splice(0,1);
      }
    });
    */
    
  }, function() {
  }, '/');


  $http.get('/emoticons.json').success(function(data) {
    var emoticonMap = {};
    for ( var key in data.emoticons) {
      var emoticon = data.emoticons[key];
      if (emoticon.images[0].emoticon_set == undefined) {
        emoticonMap[emoticon.regex.toLowerCase()] = emoticon;
      }
    }
    $scope.emoticonMap = emoticonMap;
  });

  $interval(function() {
    if ($scope.prices) {
      doChart($scope.prices);
    }
  }, 1000);
    
  $scope.records = {};
  
  $http.get('/api/emotes/kappa').success(function(data) {
  	$scope.records.kappa = data;
  });  
  $http.get('/api/emotes/biblethump').success(function(data) {
  	$scope.records.biblethump = data;
  });  
  $http.get('/api/emotes/pogchamp').success(function(data) {
  	$scope.records.pogchamp = data;
  });  
  $http.get('/api/emotes/kreygasm').success(function(data) {
  	$scope.records.kreygasm = data;
  });  
  $http.get('/api/emotes/pjsalt').success(function(data) {
  	$scope.records.pjsalt = data;
  });
  $http.get('/api/emotes/smorc').success(function(data) {
  	$scope.records.smorc = data;
  });
  
  

  // ///////////////////////Charting Disaster Area//////////////////////////////
  var canvas = document.getElementById("c");
  var ctx = canvas.getContext("2d");
  var lastPrices = {};
  function doChart(prices) {
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
    for ( var key in prices) {
      var price = prices[key];
      var color = $scope.emoticonMap[price.emoticon].color
      if (color == undefined) {
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
  // /////////////////////////////////////////////////////////////////////////
});
