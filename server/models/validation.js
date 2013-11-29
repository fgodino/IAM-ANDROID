var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var validationSchema = new Schema({
	number : String,
	code : String
});

var Validation = mongoose.model('Validation', validationSchema);

module.exports = validationSchema;