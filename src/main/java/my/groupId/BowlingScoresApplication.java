package my.groupId;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import my.groupId.core.ScoringService;
import my.groupId.db.PlayerScoreDao;
import my.groupId.resources.ScoreResource;
import org.jdbi.v3.core.Jdbi;

public class BowlingScoresApplication extends Application<BowlingScoresConfiguration> {

    public static void main(final String[] args) throws Exception {
        new BowlingScoresApplication().run(args);
    }

    @Override
    public String getName() {
        return "BowlingScores";
    }

    @Override
    public void initialize(final Bootstrap<BowlingScoresConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<BowlingScoresConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(BowlingScoresConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(final BowlingScoresConfiguration configuration,
            final Environment environment) {
        migrate(configuration, environment);
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        PlayerScoreDao dao = jdbi.onDemand(PlayerScoreDao.class);

        final ScoreResource scoreResouce = new ScoreResource(new ScoringService(dao));
        environment.jersey().register(scoreResouce);

    }

    /**
     * Applies the database changes listed in the changelog.xml using liquibase
     */
    private void migrate(BowlingScoresConfiguration configuration, Environment environment) {
        try {
            ManagedDataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), "");

            JdbcConnection conn = new JdbcConnection(dataSource.getConnection());

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(conn);
            Liquibase liquibase = new Liquibase("liquibase/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("");

        } catch (SQLException | LiquibaseException ex) {
            Logger.getLogger(BowlingScoresApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
