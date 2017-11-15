import { Platform } from 'react-native'
import ios_module from './index.ios.js'
import android_module from './index.android.js'

module.exports = Platform.OS === 'ios' ? ios_module : android_module