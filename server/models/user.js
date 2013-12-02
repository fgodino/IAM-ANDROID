var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var crypto = require('crypto');

var userSchema = new Schema({
  username:  {type : String, trim : true, required : true, unique: true},
  number: {type : String, trim : true, required : true, unique: true},
  name: String,
  picture: String,
  statuses: [{type : Schema.Types.ObjectId, ref : 'Status'}],
  current_status: {type : Schema.Types.ObjectId, ref : 'Status'},
  following : [{type : Schema.Types.ObjectId, ref : 'User'}],
  hashed_password: {type : String},
  salt: {type : String},
  likes : {type : Number, default : 0}
});

userSchema
  .virtual('password')
  .set(function(password) {
    this._password = password;
    this.salt = this.makeSalt();
    this.hashed_password = this.encryptPassword(password);
  })
  .get(function() { return this._password })

userSchema.methods = {

  authenticate: function (plainText) {
    return this.encryptPassword(plainText) === this.hashed_password
  },

  makeSalt: function () {
    return Math.round((new Date().valueOf() * Math.random())) + ''
  },

  encryptPassword: function (password) {
    if (!password) return ''
    var encrypred
    try {
      encrypred = crypto.createHmac('sha1', this.salt).update(password).digest('hex')
      return encrypred
    } catch (err) {
      return ''
    }
  }
}

if (!userSchema.options.toJSON) userSchema.options.toJSON = {};
userSchema.options.toJSON.transform = function (doc, ret, options) {
  delete ret._id;
  delete ret.__v;
  delete ret.hashed_password;
  delete ret.salt;
  delete ret.statuses;
}

var User = mongoose.model('User', userSchema);

module.exports = userSchema;
