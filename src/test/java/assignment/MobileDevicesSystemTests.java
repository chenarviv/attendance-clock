/*
package assignment;

import assignment.devices.BaseMobileDevice;
import assignment.devices.MobileDevicesFactory;
import assignment.devices.MobileDevicesMapper;
import assignment.global.exceptions.UnsupportedMobileDeviceException;
import feelbetter.assignment.server.clock.dal.IFeatureDAL;
import feelbetter.assignment.server.clock.dal.IMobileDeviceDAL;
import feelbetter.assignment.server.clock.dal.model.FeatureEntity;
import feelbetter.assignment.server.clock.dal.model.MobileDeviceEntity;
import feelbetter.assignment.server.clock.dal.model.MobileDeviceNameAndFeature;
import intango.assignment.devices.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@ActiveProfiles("test")
public class MobileDevicesSystemTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MobileDevicesFactory mobileDevicesFactory;

    @Autowired
    private MobileDevicesMapper mobileDevicesMapper;

    @Autowired
    private IFeatureDAL featureDAL;

    @Autowired
    private IMobileDeviceDAL mobileDeviceDAL;


    @Before
    public void insertValidFeaturesToDb() {
        Set<String> validFeaturesStrings = new HashSet<>(Arrays.asList(
                "Tablet", "Apple", "RES_1080x1920",
                "SmartPhone", "Samsung", "RES_720x1280", "RES_750x1334", "Front camera",
                "Small_Screen_Size", "Gyroscope", "iOS", "Mobile phone", "RES_640x1136",
                "LG", "Smart TV", "Android", "RES_1440x2560", "Medium_Screen_Size",
                "Back camera", "Big_Screen_Size", "XL_Screen_Size", "FeaturePhone"
        ));

        Set<FeatureEntity> validFeatures = validFeaturesStrings.stream()
                .map(FeatureEntity::new)
                .collect(Collectors.toSet());

        featureDAL.save(validFeatures);
    }

    @After
    public void tearDown() throws Exception { //Truncate all H2 tables after each test
        Connection connection = dataSource.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SHOW TABLES");
        while (resultSet.next()) {
            connection.setAutoCommit(false);
            String tableName = resultSet.getString(1);
            Statement statement = connection.createStatement();
            statement.execute("ALTER TABLE " + tableName + " SET REFERENTIAL_INTEGRITY FALSE; ");
            statement.execute("truncate table " + tableName);
            statement.execute("ALTER TABLE " + tableName + " SET REFERENTIAL_INTEGRITY TRUE; ");
            connection.commit();
        }
    }

    @Test
    public void createDevices() throws UnsupportedMobileDeviceException {
        // Create devices
        BaseMobileDevice galaxyS9Edge = mobileDevicesFactory.createMobileDevice("GalaxyS9Edge");
        BaseMobileDevice galaxyS10Plus = mobileDevicesFactory.createMobileDevice("GalaxyS10Plus");
        BaseMobileDevice iphone11 = mobileDevicesFactory.createMobileDevice("Iphone11");
        BaseMobileDevice xiaomiMi5 = mobileDevicesFactory.createMobileDevice("XiaomiMi5");

        // Save to DB
        mobileDeviceDAL.save(mobileDevicesMapper.mapMobileDevicesToEntities(galaxyS9Edge, galaxyS10Plus, iphone11, xiaomiMi5));
    }

    @Test
    public void getAllDevicesWithMediumSizeScreen() throws UnsupportedMobileDeviceException {
        createDevices();
        Set<MobileDeviceEntity> devicesWithMediumSizeScreen = mobileDeviceDAL.findByFeatures_Feature("Medium_Screen_Size");
        Set<BaseMobileDevice> mobileDevices = mobileDevicesMapper.mapEntitiesToMobileDevices(devicesWithMediumSizeScreen);

        Assert.assertEquals(2, mobileDevices.size());
        Assert.assertTrue(mobileDevices.stream().map(BaseMobileDevice::getModelName).collect(Collectors.toSet()).containsAll(
                Arrays.asList("GalaxyS9Edge", "XiaomiMi5")
        ));
    }

    @Test
    public void groupDevicesByResolution() throws UnsupportedMobileDeviceException {
        createDevices();
        List<MobileDeviceNameAndFeature> mobileDevicesAndResFeature = mobileDeviceDAL.getMobileDevicesWithResolutionFeatureAndResolution();
        Map<String, List<String>> mobileDevicesGroupedByResolution =
                mobileDevicesAndResFeature.stream()
                        .collect(Collectors.groupingBy(
                                MobileDeviceNameAndFeature::getFeature,
                                Collectors.mapping(MobileDeviceNameAndFeature::getName, Collectors.toList())));

        Assert.assertEquals(3, mobileDevicesGroupedByResolution.keySet().size());
        Assert.assertEquals(1, mobileDevicesGroupedByResolution.get("RES_1440x2560").size());
        Assert.assertEquals("GalaxyS10Plus", mobileDevicesGroupedByResolution.get("RES_1440x2560").get(0));
        Assert.assertEquals(1, mobileDevicesGroupedByResolution.get("RES_1080x1920").size());
        Assert.assertEquals("GalaxyS9Edge", mobileDevicesGroupedByResolution.get("RES_1080x1920").get(0));
        Assert.assertEquals(2, mobileDevicesGroupedByResolution.get("RES_720x1280").size());
        Assert.assertTrue(mobileDevicesGroupedByResolution.get("RES_720x1280").containsAll(
                Arrays.asList("Iphone11", "XiaomiMi5")
        ));
    }

    @Test
    public void getAllDevicesWithGyroscope() throws UnsupportedMobileDeviceException {
        createDevices();
        Set<MobileDeviceEntity> devicesWithGyroscope = mobileDeviceDAL.findByFeatures_Feature("Gyroscope");
        Set<BaseMobileDevice> mobileDevices = mobileDevicesMapper.mapEntitiesToMobileDevices(devicesWithGyroscope);
        Assert.assertEquals(2, mobileDevices.size());
        Assert.assertTrue(mobileDevices.stream().map(BaseMobileDevice::getModelName).collect(Collectors.toSet()).containsAll(
                Arrays.asList("Iphone11", "XiaomiMi5")
        ));
    }

}
*/
