var passport = require('passport'),
  mongoose = require('mongoose'),
  User = mongoose.model('User'),
  BasicStrategy = require('passport-http').BasicStrategy;


passport.use(new BasicStrategy({},
  function(username, password, done) {
    // asynchronous verification, for effect...
    process.nextTick(function(){      
      // Find the user by username.  If there is no user with the given
      // username, or the password is not correct, set the user to `false` to
      // indicate failure.  Otherwise, return the authenticated `user`.
      User.findOne({username : username}, function(err, user) {
        if (err) { return done(err); }
        if (!user) { return done(null, false); }
        if (!user.authenticate(password)) { return done(null, false); }
        return done(null, user);
      })
    });
  }
));

module.exports = passport;