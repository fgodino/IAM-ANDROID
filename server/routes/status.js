var mongoose = require('mongoose');
var Status = mongoose.model('Status');
var User = mongoose.model('User');
var utils = require('../lib/utils.js');
var _ = require('underscore');

var config = require('../config.js');

exports.new = function(req, res){
  var me = req.user._id;
  if(!utils.hasAllKeys(req.body, config.statusValues)){
    res.send(400, "Missing values");
  } else {
    var dbData = _.pick(req.body, config.statusValuesComplete);
    dbData.owner = me;
    var newStatus = new Status(dbData);
    newStatus.save(function(err, result){
      if(err){
        res.send(400, {error : err.code, details : err.err});
      } else {
        //TODO Refactor to so asyncronously
        User.findByIdAndUpdate(me, {$addToSet: { statuses: result._id}}, function(err, r){
          User.findByIdAndUpdate(me, {$set: {current_status: result}}, function(err, r){
             res.send(200, result);
          });
        });
      }
    });
  }
}
