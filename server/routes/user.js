
/*
 * GET users listing.
 */
var mongoose = require('mongoose');
var User = mongoose.model('User');

exports.list = function(req, res){
  res.send("respond with a resource");
};

exports.get = function(req, res){
  User.findOne({username : req.params.id}, function(err, user){
    if(err){
      return res.send(500);
    }
    if(!user){
      return res.send(404);
    }
    return res.send(user.toJSON());
  });
}

exports.getMe = function(req, res){
  User.findById(req.user.id, function(err, user){
    if(err){
      res.send(500);
    } else {
      res.send(user.toJSON());
    }
  });
}

exports.getFriends = function(req, res){
  User
  .findOne({username : req.user.username})
  .populate('following', 'username likes name picture current_status')
  .populate('following.current_status')
  .exec(function (err, users) {
    console.log(users);
    users.populate('current_status', function(err, doc){
      res.send(doc.following);
    });
  });
}

exports.addFriend = function(req, res){
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
