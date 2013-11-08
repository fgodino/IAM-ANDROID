

assetsApp.directive(“myTap”, function () {
    var isTouchDevice = !!(“ontouchstart” in window);
    return function (scope, elm, attrs) {
        if (isTouchDevice) {
            var tapping = false;
            elm.bind(‘touchstart’, function (e) {
                if (attrs.touchMode === “start”) {
                    scope.$apply(attrs.myTap);
                    e.preventDefault();
                } else {
                    elm.addClass(attrs.touchClass);
                    tapping = true;
                }
            });
            elm.bind(‘touchmove’, function () {
                tapping = false;
            });
            elm.bind(‘touchend’, function (e) {
                tapping && scope.$apply(attrs.myTap);
                e.preventDefault();
            });
        } else {
            elm.bind(‘click’, function (e) {
                scope.$apply(attrs.myTap);
                e.preventDefault();
            });
        }
    };
});



