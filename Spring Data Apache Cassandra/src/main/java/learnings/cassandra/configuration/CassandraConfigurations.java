package learnings.cassandra.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

@Configuration
@Slf4j
public class CassandraConfigurations extends AbstractCassandraConfiguration {

    @Autowired
    Environment environment;

    @Override
    protected String getKeyspaceName() {
        return "localhostkeyspace";
    }

    @Override
    protected String getLocalDataCenter() {
        return "datacenter1";
    }
}
