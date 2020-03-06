package feelbetter.assignment.server.clock.dal;

import feelbetter.assignment.server.clock.dal.model.UserMonthReport;
import org.springframework.data.mongodb.repository.MongoRepository;

@org.springframework.stereotype.Repository
public interface IUserMonthReportsDAL extends MongoRepository<UserMonthReport, String> {

    UserMonthReport findByUserIdAndMonth(String userId, int month);
}