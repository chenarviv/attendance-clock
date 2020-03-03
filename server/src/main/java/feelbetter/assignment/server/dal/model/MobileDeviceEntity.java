package feelbetter.assignment.server.dal.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MobileDevices")
public class MobileDeviceEntity {

    @Id
    @Column(
            name = "id",
            unique = true
    )
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MobileDevicesFeatures", joinColumns = {@JoinColumn(name = "mobileDeviceIdName", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "featureId", referencedColumnName = "id")})
    private Set<FeatureEntity> features = new HashSet<>();

    public MobileDeviceEntity() {
    }

    public MobileDeviceEntity(String name, Set<FeatureEntity> features) {
        this.name = name;
        this.features = features;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<FeatureEntity> getFeatures() {
        return features;
    }

    public void setFeatures(Set<FeatureEntity> features) {
        this.features = features;
    }
}
