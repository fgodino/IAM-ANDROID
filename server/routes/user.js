
/*
 * GET users listing.
 */
var User = require('../models/user.js');

exports.list = function(req, res){
  res.send("respond with a resource");
};

exports.getUser = function(req, res){
  console.log(User);
  User.findOne({id : req.params.id}, function(err, res){
    res.send(res)
  });
}
