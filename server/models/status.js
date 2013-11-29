var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var statusSchema = new Schema({
  id:  String,
  status: String,
  color: String,
  media: {format : String, url : String},
  date : {type: Date, default: Date.now },
  likes : [{follower : Schema.Types.ObjectId, text : String}]
});

var Status = mongoose.model('Status', statusSchema);

module.exports = statusSchema;