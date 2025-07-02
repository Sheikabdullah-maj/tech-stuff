package learnings.cassandra.repository;

import learnings.cassandra.dao.OrderSummary;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface OrderSummaryRepository extends CassandraRepository<OrderSummary, Integer> {
}
