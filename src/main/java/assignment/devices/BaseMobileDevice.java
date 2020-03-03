package assignment.devices;

import java.util.Objects;
import java.util.Set;

public class BaseMobileDevice {
    private String modelName;
    private Set<String> features;

    BaseMobileDevice(String modelName, Set<String> features) {
        this.modelName = modelName;
        this.features = features;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Set<String> getFeatures() {
        return features;
    }

    public void setFeatures(Set<String> features) {
        this.features = features;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseMobileDevice that = (BaseMobileDevice) o;
        return modelName.equals(that.modelName) &&
                features.equals(that.features);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelName, features);
    }
}
