import PropTypes from 'prop-types'
import React from 'react'
import {
  requireNativeComponent,
  View,
  NativeModules
} from 'react-native'


const RNTVideoManager = NativeModules.RNTVideoManager

class Video extends React.Component {

  static connect () {
    RNTVideoManager.connectToRoom()
  }
  
  static disconnect () {
    RNTVideoManager.disconnect()
  }

  static toggleCamera(value) {
    RNTVideoManager.toggleCamera(value)
  }

  render () {
    return <RNTVideo
    {...this.props}
    />
  }
}

Video.propTypes = {
  ...View.propTypes,
  host: PropTypes.string,
  token: PropTypes.string,
  roomId: PropTypes.string,
  displayName: PropTypes.string,
  hudHidden: PropTypes.bool,
  onReady: PropTypes.func,
  onConnect: PropTypes.func,
  onDisconnect: PropTypes.func,
  onFailure: PropTypes.func,
}

var RNTVideo = requireNativeComponent('RNTVideo', Video)

module.exports = Video
