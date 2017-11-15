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

const RNTVidyoManager = NativeModules.RNTVidyoManager

const callbackMap = new Map()
let nextCallbackId = 0

const commands = NativeModules.UIManager.RNTVideo.Commands;
Object.keys(commands).forEach(command => {
    RNTVidyoManager[command] = (handle, ...rawArgs) => {
    const args = rawArgs.map(arg => {
      if (typeof arg === 'function') {
        callbackMap.set(nextCallbackId, arg);
        return nextCallbackId++;
      }
      return arg;
    });
    NativeModules.UIManager.dispatchViewManagerCommand(handle, commands[command], args);
  };
});

class Video extends React.Component {
  constructor(props){
    super(props)

    this.eventEmiter = DeviceEventEmitter
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
  
  connect () {
    RNTVidyoManager.connectToRoom(findNodeHandle(this))
  }
  
  disconnect () {
    RNTVidyoManager.disconnect(findNodeHandle(this))
  }

  toggleCamera(value) {
    RNTVidyoManager.toggleCamera(findNodeHandle(this), value)
  }

  handleOnReady = (e) => {
    if (typeof this.props.onReady === 'function') { this.props.onReady(e) }
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

Video.defaultProps = {
  hubHidden: true
}

Video.propTypes = {
  host: PropTypes.string,
  token: PropTypes.string,
  roomId: PropTypes.string,
  displayName: PropTypes.string,
  userName: PropTypes.string,
  hubHidden: PropTypes.func,
  onReady: PropTypes.func,
  onConnect: PropTypes.func,
  onDisconnect: PropTypes.func,
  onFailure: PropTypes.func,
  ...View.propTypes
}

var RNTVideo = requireNativeComponent('RNTVideo', Video)

module.exports = Video
