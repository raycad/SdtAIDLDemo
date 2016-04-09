Here are samples to show how to use AIDL as client and server. These example include 2 modules run with Android Studio IDE are:
1. SdtAIDLServer:
- Defines interfaces.
- Provides utils of the server via SdtUtilsService class.
The service information are configured in the AndroidManifest.xml file of the sdtaidlserver module.
<service
    android:name=".SdtUtilsService"
    android:process=":remote" >
    <intent-filter>
        <action android:name="service.SdtUtils" />
    </intent-filter>
</service>

2. SdtAIDLClient:
This uses the service to perform some actions/utils.
When it is launched it will be bind with the server (bindService(it, mUtilsServiceConnection, Service.BIND_AUTO_CREATE);) from the function initConnection.
To use the service you have to copy the ISdtUtils.aidl from the service to the client then bind the service.
The service must be installed before running the client. The client will bind and request system to launch the server to make requests.