package feelbetter.assignment.server.dal.model;

public class MobileDeviceNameAndFeature {
    private String name;
    private String feature;

    public MobileDeviceNameAndFeature(String name, String feature) {
        this.name = name;
        this.feature = feature;
    }

    public MobileDeviceNameAndFeature() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
