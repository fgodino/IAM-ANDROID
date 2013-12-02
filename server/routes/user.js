
/*
 * GET users listing.
 */
var mongoose = require('mongoose');
var User = mongoose.model('User');


exports.get = get = function(req, res){
  User
  .findOne({username : req.params.id})
  .populate('current_status')
  .exec(function(err, user){
    if(err){
      return res.send(500);
    }
    if(!user){
      return res.send(404);
    }
    return res.send(user.toJSON());
  });
}

exports.getMe = getMe = function(req, res){
  req.params.id = req.user.username;
  get(req, res);
}

exports.getFriends = getFriends = function(req, res){
  User
  .findOne({username : req.user.username})
  .populate('following', 'username likes name picture current_status')
  .exec(function (err, users) {
    console.log(users);
    User.populate(users.following, {path : 'current_status', select : "status color"}, function(err, doc){
      res.send(doc);
    });
  });
}

exports.addFriend = addFriend = function(req, res){
  if(!req.body.id){
    return res.send(400);
  }

  var option = req.query.option || 'number';
  var condition = {};
  condition[option] = req.body.id;
  User
  .findOne(condition)
  .exec(function (err, user) {
    if(err || !user){
      return res.send(400);
    }
    User.findByIdAndUpdate(req.user.id, {$addToSet: { following: user._id}}, function(err, update){
      if(err){
        return res.send(500);
      }
      return res.send("Ok");
    });
  });
}
