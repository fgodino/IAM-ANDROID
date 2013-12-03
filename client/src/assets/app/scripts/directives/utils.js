
assetsApp.directive("ngPlaceholder", function($log, $timeout) {
    var txt;
    return {
        restrict: "A",
        link: function(scope, elem, attrs) {
            var txt = attrs.ngPlaceholder;

            elem.bind("focus", function() {
                if(elem.val() === txt) {
                    elem.val("");
                }
                scope.$apply()
            })

            elem.bind("blur", function() {
                if(elem.val() === "") {
                    elem.val(txt);
                }
                scope.$apply()
            })

            // Initialise placeholder
            $timeout(function() {
                elem.val(txt)
                scope.$apply();
            })
        }
    }
})


