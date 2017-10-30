
const extract = require('extract-zip')
const pathAbsolute = require('path-absolute')

extract('../VidyoClientIOS.framework.zip', {dir: pathAbsolute('../ios')}, function (err) {
  if(err) {
    throw err
  } 
  console.log('VidyoClientIOS.framework - decompressed')
})