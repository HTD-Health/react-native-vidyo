# react-native-video
Plugin is under heavy development

## Installation

### iOS

1. `npm i --save https://github.com/HealthTechDevelopers/react-native-vidyo`
2. Add reference to `node_modules/react-native-vidyo/ios/VidyoClientIOS.framework` in **Embedded Binaries** section
3. Add path to framework in **Framework Search Paths** `$(SRCROOT)/../node_modules/react-native-vidyo/ios`
4. Add path in **Header Search Paths** `$(SRCROOT)/../node_modules/react-native-vidyo/ios/VidyoClientIOS.framework/Headers`
5. Run `react-native link`
6. Add This entitlements to your <application_name>/<application_name>.entitlements

```
    <key>keychain-access-groups</key>
    <array>
        <string>$(AppIdentifierPrefix)VidyoLicense</string>
        <string>$(AppIdentifierPrefix)<Your Bundle Identifier></string>
    </array>
```
7. Add this to **Info.plist**
```
	<key>NSMicrophoneUsageDescription</key>
 	<string>Video</string>
```
### Android

1. `npm i --save https://github.com/htdevelopers/react-native-vidyo`
2. Run `react-native link`


## USAGE

`import Vidyo from 'react-native-vidyo'`


```jsx
<Vidyo
    width={300}
    heigth={400}
    host={VIDYO_HOST}
    token={TOKEN}
    displayName={USER_DISPLAY_NAME}
    resourceId={ROOM_ID}
/>
```
