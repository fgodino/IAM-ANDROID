
assetsApp.directive("drawing", function($window){
  return {
    restrict: "A",
    scope: {
      myPixel: '='
    },
    template: '<div class="layer-component"><canvas id="picker"></canvas><canvas id="circle"></canvas></div>',
    replace : true,
    link: function(scope, element){
      var me = element[0];
      var canvas = document.getElementById("picker");
      var circle = document.getElementById("circle");
      var ctx = canvas.getContext('2d');
      var ctxCircle = circle.getContext('2d');


      var width = $window.document.width;
      var height = width;

      me.style.width = width + "px";
      me.style.height = height + "px";

      console.log(me.style.width);

      canvas.width = me.offsetWidth;
      canvas.height = me.offsetHeight;
      circle.width = me.offsetWidth;
      circle.height = me.offsetHeight;

      var circle = function(x,y,r) {
        ctxCircle.beginPath();
        ctxCircle.arc(x, y, r, 0, Math.PI*2, true);
        ctxCircle.fill();
      }

      var rectan = function(x,y,w,h) {
        ctxCircle.beginPath();
        ctxCircle.rect(x,y,w,h);
        ctxCircle.closePath();
      }

      function clear() {
        ctxCircle.clearRect(0, 0, width, height);
      }

      var draw = function(x, y, color) {
        clear();
        rectan(0,0,width,height);
        ctxCircle.fillStyle = color;
        circle(x, y, 10);
      }

      console.log("LLAMA");

      draw(10, 10);

      var image = new Image();
      image.onload = function () {
        ctx.drawImage(image, 0, 0, canvas.width, canvas.height); // draw the image on the canvas
      }

      var imageSrc = 'images/colorpicker_square.bmp';
      image.src = imageSrc;

      scope.move = function(event){

        var rect =  event.gesture.srcEvent.touches[0].target.getBoundingClientRect();
        var canvasX = Math.floor(event.gesture.srcEvent.touches[0].pageX - rect.left);
        var canvasY = Math.floor(event.gesture.srcEvent.touches[0].pageY - rect.top);

        console.log(canvasX, canvasY);
        if(canvasX >= 0 && canvasY >=0){
          var imageData = ctx.getImageData(canvasX, canvasY, 1, 1);
          var pixel = imageData.data;



          // update preview color
          var pixelColor = "rgb("+pixel[0]+", "+pixel[1]+", "+pixel[2]+")";
                    draw(canvasX, canvasY, pixelColor);
          scope.myPixel = pixelColor;
        }
      }
    }
  };
});

