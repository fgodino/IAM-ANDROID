
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
      res.send(500);
    } else {
      res.send(user.toJSON());
    }
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
  .exec(function (err, users) {
    User.populate(users, {path : 'current_status'}, function(err, doc){
      console.log(err, doc)
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
    db.students.update({ _id: req.user._id }, {$addToSet: { following: user._id}});
  });
}
