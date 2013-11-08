var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userSchema = new Schema({
  id:  String,
  number: String,
  name: String,
  picture: String,
  statuses: [Schema.Types.ObjectId],
  current_status: Schema.Types.ObjectId,
  following : [Schema.Types.ObjectId],
  likes : {type : Number, default : 0}
});

var User = mongoose.model('User', userSchema);

module.exports.User = User;
