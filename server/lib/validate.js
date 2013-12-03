var mongoose = require('mongoose');
var Validation = mongoose.model('Validation');
var utils = require('./utils.js');
var _ = require('underscore');

var User = mongoose.model('User');

var config = require('../config.js');

var send = function(req, res){
  if(!req.query.number){
    res.send(400, "No number");
  }
  else{
    var randomString =  (Math.random()+1).toString(36).substr(2,7);
    console.log(randomString);
    Validation.findOne({'number' : req.query.number}, function(err,result){
        if(result) {
          Validation.update({'number' : req.query.number},{code : randomString},{upsert:true}, function(){
            res.send(200, "OK");
          });
        } else {
          var val = new Validation({number : req.query.number, code : randomString});
          val.save(function(){
            res.send(200, "OK");
          });
        }
    });
  }
}

var registry = function(req, res){
  if(!req.query.number || !req.query.code){
    res.send(400, "No number or code");
  } else {
    Validation.findOne({'number' : req.query.number}, function(err,result){
      if(!result || err){
        res.send(401, "You must validate before");
      } else {
        console.log(result.code, req.query.code);
        if(result.code === req.query.code){
          completeRegistry(req, res);
        } else {
          res.send(401, "Invalid code");
        }
      }
    });
  }
}

var completeRegistry = function(req, res){
  if(!utils.hasAllKeys(req.body, config.registerValues)){
    res.send(400, "Missing values");
  } else {
    var dbData = _.pick(req.body, config.registerValuesComplete);
    dbData.number = req.query.number;
    var user = new User(dbData);
    user.save(function(err, newUser){
      if(err){
        return res.send(400, err);
      }
      return res.send(200, newUser);
    });

  }
}

var checkCode = function(req, res){
  Validation.findOne({'number' : req.query.number}, function(err,result){
    if(!result || err){
      return res.send(401, "You must validate before");
    }

    if(result.code === req.query.code){
      return res.send(200);
    } else {
      res.send(401, "Invalid code");
    }

  });
}

exports.send = send;
exports.registry = registry;
exports.checkCode = checkCode;
