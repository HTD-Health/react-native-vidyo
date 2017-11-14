import PropTypes from 'prop-types'
import React from 'react'
import { requireNativeComponent, View, NativeModules, Platform, NativeEventEmitter, DeviceEventEmitter } from 'react-native'

class Video extends React.Component {
  constructor(props){
    super(props)

    this.eventEmiter = Platform.OS === 'ios' ? new NativeEventEmitter(NativeModules.RNTVideoManager) : DeviceEventEmitter
    this.onConnectEvent = null
    this.onDisconnectEvent = null
    this.onFailureEvent = null
  }

  componentWillMount() {
    this.onConnectEvent = this.eventEmiter.addListener('RNTVidyoOnConnect', this.handleOnConnect)
    this.onDisconnectEvent = this.eventEmiter.addListener('RNTVidyoOnDisconnect', this.handleOnDisconnect)
    this.onFailureEvent = this.eventEmiter.addListener('RNTVidyoOnFailure', this.handleOnFailure)
  }

  componentWillUnmount() {
    this.onConnectEvent.remove()
    this.onDisconnectEvent.remove()
    this.onFailureEvent.remove()
  }


  static connect () {
    NativeModules.RNTVideoManager.connectToRoom()
  }
  
  static disconnect () {
    NativeModules.RNTVideoManager.disconnect()
  }

  static disconnect () {
    NativeModules.RNTVideoManager.disableCamera()
  }

  handleOnConnect = (e) => {
    if (typeof this.props.onConnect === 'function') { this.props.onConnect(e) }
  }
  handleOnDisconnect = (e) => {
    if (typeof this.props.onDisconnect === 'function') { this.props.onDisconnect(e) }
  }
  handleOnFailure = (e) => {
    if (typeof this.props.onFailure === 'function') { this.props.onFailure(e) }
  }

  render () {
    return <RNTVideo
    {...this.props} />
  }
}

Video.propTypes = {
  host: PropTypes.string,
  token: PropTypes.string,
  roomId: PropTypes.string,
  displayName: PropTypes.string,
  hudHidden: PropTypes.bool,
  onConnect: PropTypes.func,
  onDisconnect: PropTypes.func,
  onFailure: PropTypes.func,
  ...View.propTypes
}

var RNTVideo = requireNativeComponent('RNTVideo', Video)

module.exports = Video
