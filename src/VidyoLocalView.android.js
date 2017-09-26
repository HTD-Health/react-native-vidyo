/**
 * Component for Twilio Video local views.
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

class VidyoPreview extends React.Component {
  render () {
    return (
      <NativeVidyoPreview {...this.props} />
    )
  }
}

VidyoPreview.propTypes = propTypes

const NativeVidyoPreview = requireNativeComponent(
  'RNVidyoPreview',
  VidyoPreview
)

module.exports = VidyoPreview
