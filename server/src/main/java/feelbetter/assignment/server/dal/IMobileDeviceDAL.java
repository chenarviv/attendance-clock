package feelbetter.assignment.server.dal;

import feelbetter.assignment.server.dal.model.MobileDeviceEntity;
import feelbetter.assignment.server.dal.model.MobileDeviceNameAndFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

@org.springframework.stereotype.Repository
public interface IMobileDeviceDAL extends JpaRepository<MobileDeviceEntity, String> {

    Set<MobileDeviceEntity> findByFeatures_Feature(String feature);

    @Query("SELECT new MobileDeviceNameAndFeature(m.name, f.feature)" +
            "FROM MobileDeviceEntity m JOIN m.features f WHERE feature LIKE 'RES'||'%'")
    List<MobileDeviceNameAndFeature> getMobileDevicesWithResolutionFeatureAndResolution();
}
