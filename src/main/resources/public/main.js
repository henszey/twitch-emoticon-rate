angular.module('trader', [ 'AngularStomp' ]).controller('TraderCtrl', function($scope, $interval, $http, ngstomp) {
  "use strict";

  var allPrices = [];
  var priceMap = {};

  var count = 0;
  
  $scope.messages = [];
  $scope.client = ngstomp('/socket');
  $scope.client.connect("guest", "guest", function() {
    $scope.client.subscribe("/topic/price.stock.*", function(message) {
      count++;
      
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
      
      
      

      $scope.prices = allPrices.slice(0, 25);

    });
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

    console.log(count);
    count = 0;
    
  }, 1000);

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
