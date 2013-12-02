var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var statusSchema = new Schema({
	owner : {type : Schema.Types.ObjectId, ref : 'User'},
  status: String,
  color: String,
  media: {format : String, url : String},
  date : {type: Date, default: Date.now },
  likes : [{follower : { type : Schema.Types.ObjectId, ref : 'User' }, text : String}]
});

if (!statusSchema.options.toJSON) statusSchema.options.toJSON = {};
statusSchema.options.toJSON.transform = function (doc, ret, options) {
  delete ret._id;
  delete ret.__v;
}

var Status = mongoose.model('Status', statusSchema);

module.exports = statusSchema;