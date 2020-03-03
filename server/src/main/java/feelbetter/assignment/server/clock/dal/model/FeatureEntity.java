package feelbetter.assignment.server.clock.dal.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Features")
public class FeatureEntity {
    @Id
    @GeneratedValue(
            generator = "system-uuid"
    )
    @GenericGenerator(
            name = "system-uuid",
            strategy = "uuid2"
    )
    @Column(
            name = "id",
            unique = true
    )
    private String id;

    @Column
    private String feature;

    public FeatureEntity() {
    }

    public FeatureEntity(String feature) {
        this.feature = feature;
    }

    public String getId() {
        return id;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
