# react-native-video
Plugin is under heavy development

## Installation

### iOS

1. `npm i --save https://github.com/htdevelopers/react-native-vidyo`
2. Download Vidyo iOS Package from here `https://developer.vidyo.io/packages`
3. Extract the archive and search its contents for `VidyoClientIOS.framework`
4. Paste the framework to the `<appplication_name>/ios/`
5. Add VidyoClientIOS.framework to Embedded Binaries
6. Run `react-native link`
7. Add This entitlements to your <application_name>/<application_name>.entitlements

```
    <key>keychain-access-groups</key>
    <array>
        <string>$(AppIdentifierPrefix)VidyoLicense</string>
        <string>$(AppIdentifierPrefix)<Your Bundle Identifier></string>
    </array>
```
### Android

Work in progress


## USAGE

`import Vidyo from 'react-native-vidyo'`


```jsx
<Vidyo
    host={VIDYO_HOST}
    token={TOKEN}
    displayName={USER_DISPLAY_NAME}
    resourceId={ROOM_ID}
/>
```