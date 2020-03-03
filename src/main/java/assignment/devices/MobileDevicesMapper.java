/*
package assignment.devices;

import feelbetter.assignment.server.clock.dal.IFeatureDAL;
import feelbetter.assignment.server.clock.dal.model.FeatureEntity;
import feelbetter.assignment.server.clock.dal.model.MobileDeviceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MobileDevicesMapper {

    @Autowired
    private IFeatureDAL featureDAL;

    public Set<BaseMobileDevice> mapEntitiesToMobileDevices(Set<MobileDeviceEntity> mobileDeviceEntities) {
        return mobileDeviceEntities.stream().map(this::mapMobileDevice).collect(Collectors.toSet());
    }

    private BaseMobileDevice mapMobileDevice(MobileDeviceEntity mobileDeviceEntity) {
        return new BaseMobileDevice(mobileDeviceEntity.getName(),
                mobileDeviceEntity.getFeatures().stream().map(FeatureEntity::getFeature).collect(Collectors.toSet()));
    }

    private MobileDeviceEntity mapMobileDeviceToEntity(BaseMobileDevice mobileDevice) {
        return new MobileDeviceEntity(mobileDevice.getModelName(),
                featureDAL.findByFeatureInIgnoreCase(mobileDevice.getFeatures()));
    }

    public Set<MobileDeviceEntity> mapMobileDevicesToEntities(BaseMobileDevice... mobileDevices) {
        Set<MobileDeviceEntity> mobileDeviceEntities = new HashSet<>();
        for (BaseMobileDevice mobileDevice : mobileDevices) {
            mobileDeviceEntities.add(mapMobileDeviceToEntity(mobileDevice));
        }
        return mobileDeviceEntities;
    }
}
*/
