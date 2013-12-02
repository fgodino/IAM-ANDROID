
/**
 * Module dependencies.
 */

var fs = require('fs');
var models_path = __dirname + '/models'
fs.readdirSync(models_path).forEach(function (file) {
  console.log(file);
  if (~file.indexOf('.js')) require(models_path + '/' + file)
});

var express = require('express');
var http = require('http');
var path = require('path');
var mongoose = require('mongoose');
var auth = require('./auth.js');
var validate = require('./lib/validate.js');

mongoose.connect('mongodb://localhost/test');

var allowCrossDomain = function(req, res, next) {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
    res.header('Access-Control-Allow-Headers', 'Authorization');

    // intercept OPTIONS method
    if ('OPTIONS' == req.method) {
      res.send(200);
    }
    else {
      next();
    }
};

var app = express();

// all environments
app.set('port', process.env.PORT || 6001);
app.use(express.logger('dev'));
app.use(express.json());
app.use(express.urlencoded());
app.use(express.methodOverride());
app.use(allowCrossDomain);
app.use(auth.initialize());
app.use(app.router);

// development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}

var user = require('./routes/user.js');
var status = require('./routes/status.js');

app.post('/login',
  auth.authenticate('basic', { session: false }),
  function(req, res){
    res.send(req.user);
  });

app.post('/validate', validate.send);

app.post('/registry', validate.registry);

app.get('/friends/:id',
  auth.authenticate('basic', { session: false }),
  user.get);

app.get('/friends',
  auth.authenticate('basic', { session: false }),
  user.getFriends);

app.post('/friends',
  auth.authenticate('basic', { session: false }),
  user.addFriend);

app.get('/me',
  auth.authenticate('basic', { session: false }),
  user.getMe);

app.post('/status',
  auth.authenticate('basic', { session: false }),
  status.new);

app.get('/status/:id',
  auth.authenticate('basic', { session: false }),
  status.get);

http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
