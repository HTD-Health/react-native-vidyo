import PropTypes from 'prop-types'
import React from 'react'
import {
  requireNativeComponent,
  View,
  NativeModules,
  Platform,
  NativeEventEmitter,
  DeviceEventEmitter,
  findNodeHandle 
} from 'react-native'

const IS_IOS = Platform.OS === 'ios'
const RNTVideoManager = NativeModules.RNTVideoManager

if (IS_IOS) {
  const commands = NativeModules.UIManager.RNTVideo.Commands;
  Object.keys(commands).forEach(command => {
      RNTVideoManager[command] = (handle, ...rawArgs) => {
      const args = rawArgs.map(arg => {
        if (typeof arg === 'function') {
          callbackMap.set(nextCallbackId, arg);
          return nextCallbackId++;
        }
        return arg;
      });
      UIManager.dispatchViewManagerCommand(handle, commands[command], args);
    };
  });
}

class Video extends React.Component {
  constructor(props){
    super(props)

    this.eventEmiter = IS_IOS ? new NativeEventEmitter(NativeModules.RNTVideoManager) : DeviceEventEmitter
    this.onReadyEvent = null
    this.onConnectEvent = null
    this.onDisconnectEvent = null
    this.onFailureEvent = null
  }

  componentWillMount() {
    this.onReadyEvent = this.eventEmiter.addListener('RNTVidyoOnReady', this.handleOnReady)
    this.onConnectEvent = this.eventEmiter.addListener('RNTVidyoOnConnect', this.handleOnConnect)
    this.onDisconnectEvent = this.eventEmiter.addListener('RNTVidyoOnDisconnect', this.handleOnDisconnect)
    this.onFailureEvent = this.eventEmiter.addListener('RNTVidyoOnFailure', this.handleOnFailure)
  }

  componentWillUnmount() {
    this.onReadyEvent.remove()
    this.onConnectEvent.remove()
    this.onDisconnectEvent.remove()
    this.onFailureEvent.remove()
  }


  static connect () {
    if (IS_IOS) {
      RNTVideoManager.connectToRoom()
    } else {
      RNTVideoManager.connectToRoom(findNodeHandle(this))
    }
  }
  
  static disconnect () {
    if (IS_IOS) {
      RNTVideoManager.disconnect()
    } else {
      RNTVideoManager.disconnect(findNodeHandle(this))
    }
  }

  static toggleCamera(value) {
    if (IS_IOS) {
      RNTVideoManager.toggleCamera(value)
    } else {
      RNTVideoManager.toggleCamera(findNodeHandle(this), value)

    }
  }

  handleOnReady = (e) => {
    if (typeof this.props.OnReady === 'function') { this.props.OnReady(e) }
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
  onReady: PropTypes.func,
  onConnect: PropTypes.func,
  onDisconnect: PropTypes.func,
  onFailure: PropTypes.func,
  ...View.propTypes
}

var RNTVideo = requireNativeComponent('RNTVideo', Video)

module.exports = Video
