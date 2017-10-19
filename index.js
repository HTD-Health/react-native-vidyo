import PropTypes from 'prop-types'
import React from 'react'
import { requireNativeComponent } from 'react-native'
import NativeEventEmitter from './Emitter';

class Video extends React.Component {
  componentWillMount() {
    this.props.listeners.forEach(listener => this.addListener(listener));
  }

  componentWillUnmount() {
    this.props.listeners.forEach(listener => this.removeListener(listener));
  }

  addListener = (name) => {
    if (!this.props.listeners[name]) {
      this.props.listeners[name] = NativeEventEmitter.addListener(name, e =>
        this.props[name](e)
      );
    }
  };

  removeListener = (name) => {
    if (this.props.listeners[name]) {
      this.props.listeners[name].remove();
      delete this.props.listeners[name];
    }
  };

  render () {
    return <RNTVideo {...this.props} />
  }
}

Video.defaultProps = {
  listeners: ['onConnect', 'onDisconnect', 'onFailure',]
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

var RNTVideo = requireNativeComponent('RNTVideo', Video)

module.exports = Video
