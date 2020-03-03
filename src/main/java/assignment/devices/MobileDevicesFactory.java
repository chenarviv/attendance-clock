package assignment.devices;

import assignment.global.exceptions.UnsupportedMobileDeviceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MobileDevicesFactory {

    @Autowired
    private Map<String, BaseMobileDevice> mobileDevices;

    public BaseMobileDevice createMobileDevice(String deviceName) throws UnsupportedMobileDeviceException {
        if (mobileDevices.containsKey(deviceName)) {
            return mobileDevices.get(deviceName);
        } else {
            throw new UnsupportedMobileDeviceException();
        }
    }
}