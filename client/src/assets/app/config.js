
if (!String.format) {
  String.format = function(format) {
    var args = Array.prototype.slice.call(arguments, 1);
    return format.replace(/{(\d+)}/g, function(match, number) {
      return typeof args[number] != 'undefined'
        ? args[number]
        : match
      ;
    });
  };
}

var HOST = "http://localhost:6001/";
//var HOST = "http://10.0.2.2:6001/";
//var HOST = "http://192.168.1.52:6001/";
//var HOST = "http://192.161.1.36:6001/";
