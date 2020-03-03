package feelbetter.assignment.server.dal;

import feelbetter.assignment.server.dal.model.FeatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

@org.springframework.stereotype.Repository
public interface IFeatureDAL extends JpaRepository<FeatureEntity, String> {

    Set<FeatureEntity> findByFeatureInIgnoreCase(Set<String> features);
}
