// Video.js

import PropTypes from 'prop-types'
import React from 'react'
import { requireNativeComponent } from 'react-native'

class Video extends React.Component {
  render () {
    return <RNTVideo
      {...this.props}
    />
  }
}

Video.propTypes = {
  host: PropTypes.string,
  token: PropTypes.string,
  displayName: PropTypes.string,
  resourceId: PropTypes.string,
  onConnect: PropTypes.func,
  onDisconnect: PropTypes.func,
  onFailure: PropTypes.func
}

// requireNativeComponent automatically resolves 'RNTVideo' to 'RNTVideoManager'
var RNTVideo = requireNativeComponent('RNTVideo', Video)

module.exports = Video
