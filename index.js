import PropTypes from 'prop-types'
import React from 'react'
import { requireNativeComponent, NativeModules, View } from 'react-native'
console.log('NativeModules', NativeModules)
class Video extends React.Component {
  render () {
    return <RNTVideo {...this.props} />
  }
}

Video.propTypes = {
  host: PropTypes.string,
  token: PropTypes.string,
  userName: PropTypes.string,
  roomId: PropTypes.string,
  displayName: PropTypes.string,
  resourceId: PropTypes.string,
  hudHidden: PropTypes.bool,
  onConnect: PropTypes.func,
  onDisconnect: PropTypes.func,
  onFailure: PropTypes.func,
  ...View.propTypes
}

var RNTVideo = requireNativeComponent('RNTVidyo', Video)

module.exports = Video
