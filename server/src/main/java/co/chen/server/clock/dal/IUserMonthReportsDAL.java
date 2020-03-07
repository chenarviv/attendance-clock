package co.chen.server.clock.dal;

import co.chen.server.clock.dal.model.UserMonthReport;
import org.springframework.data.mongodb.repository.MongoRepository;

@org.springframework.stereotype.Repository
public interface IUserMonthReportsDAL extends MongoRepository<UserMonthReport, String> {

    UserMonthReport findByUserIdAndMonth(String userId, int month);
}