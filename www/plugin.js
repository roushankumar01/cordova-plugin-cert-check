let exec;
try {
  exec = require('cordova/exec');
} catch (error) {
  console.log('Cordova exec not found');
  exec = function (cb, err, PLUGIN_NAME, RootCheck, args) {
    console.log('Invoked RootCheck:' + RootCheck);
    cb();
  };
}


var PLUGIN_NAME = 'certCheck';

var certCheck = {
  isRooted: function (cb, err) {
    exec(cb, err, PLUGIN_NAME, 'isRooted', []);
  }
};

module.exports = certCheck;