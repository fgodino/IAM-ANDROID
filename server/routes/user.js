var Busboy = require('busboy');
var path = require('path');
var inspect = require('util').inspect;
var fs = require('fs');

var crypto = require('crypto');

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
    User.populate(users.following, {path : 'current_status', select : "status color date"}, function(err, doc){
      res.send(doc);
    });
  });
}

exports.addFriend = addFriend = function(req, res){
  if(!req.body.id){
    return res.send(400);
  }

  var option = req.query.option || 'username';
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

exports.getMultipart = getMultipart = function(req, res, next){

  console.log(req);

  var busboy = new Busboy({ headers: req.headers});
  busboy.on('file', function(fieldname, file, filename, encoding, mimetype) {

    var files = {};
    var id = crypto.randomBytes(5).toString('hex');
    var saveTo = path.join(__dirname + '/../tmp', id);

    console.log(saveTo);

    files[fieldname] = saveTo;
    file.pipe(fs.createWriteStream(saveTo));
  });
  busboy.on('field', function(fieldname, val, valTruncated, keyTruncated) {
      console.log('Field [' + fieldname + ']: value: ' + inspect(val));
    });
  busboy.on('end', function() {
    next();
  });
  busboy.on('error', function(err) {
    console.log(err);
  })
  return req.pipe(busboy);
}

exports.modify = modify = function(req, res){
  res.send(200);
}
