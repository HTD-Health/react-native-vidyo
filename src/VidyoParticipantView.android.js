/**
 * Component for Twilio Video participant views.
 *
 * Authors:
 *   Jonathan Chang <slycoder@gmail.com>
 */

import {
  requireNativeComponent,
  View
} from 'react-native'
import React from 'react'

const propTypes = {
  ...View.propTypes
}

class VidyoRemotePreview extends React.Component {
  render () {
    return (
      <NativeVidyoRemotePreview {...this.props} />
    )
  }
}

VidyoRemotePreview.propTypes = propTypes

const NativeVidyoRemotePreview = requireNativeComponent(
  'RNVidyoRemotePreview',
  VidyoRemotePreview
)

module.exports = VidyoRemotePreview
