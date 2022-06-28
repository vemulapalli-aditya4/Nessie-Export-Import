import com.google.protobuf.ByteString;
import org.projectnessie.model.CommitMeta;
import org.projectnessie.model.Content;
import org.projectnessie.server.store.TableCommitMetaStoreWorker;
import org.projectnessie.versioned.GetNamedRefsParams;
import org.projectnessie.versioned.ReferenceInfo;
import org.projectnessie.versioned.StoreWorker;
import org.projectnessie.versioned.persist.adapter.*;
import org.projectnessie.versioned.persist.tx.*;
import org.projectnessie.versioned.persist.tx.postgres.PostgresDatabaseAdapter;
import org.projectnessie.versioned.persist.tx.postgres.PostgresDatabaseAdapterFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Stream;

public class exportPostgres extends exportTxBackend{

    public void getTablesInPostgresRepo(String url) throws Exception {

        //Initializing Postgres DatabaseAdapter
        StoreWorker<Content, CommitMeta, Content.Type> storeWorker = new TableCommitMetaStoreWorker();

        TxDatabaseAdapterConfig dbAdapterConfig = ImmutableAdjustableTxDatabaseAdapterConfig.builder().build();

        TxConnectionConfig txConnectionConfig = ImmutableDefaultTxConnectionConfig.builder().build();

        //Should initialize postgresDBAdapter properly
        PostgresDatabaseAdapter postgresDBAdapter;
        TxConnectionProvider<TxConnectionConfig> connector = new TxConnectionProvider<TxConnectionConfig>() {
            @Override
            public Connection borrowConnection() throws SQLException {

                try(Connection conn = DriverManager.getConnection(url);)
                {
                    return conn;
                }
                 catch( SQLException e)
                 {
                     System.out.println(" SQL Exception and NULL connection is returned " + e.getMessage());

                     return null;
                 }

            }

            @Override
            public void close() throws Exception {

            }
        };

        connector.configure(txConnectionConfig);

        postgresDBAdapter = (PostgresDatabaseAdapter) new PostgresDatabaseAdapterFactory()
                    .newBuilder()
                    .withConfig(dbAdapterConfig)
                    .withConnector(connector)
                    .build(storeWorker);

        // System.out.println("Postgres DatabaseAdapter Initialized");

        //Is this right?
        conn = postgresDBAdapter.borrowConnection();

        commitLogTable =  getCommitLogTable(postgresDBAdapter);

        refLogTable = getRefLogTable(postgresDBAdapter);

        repoDescTable = getRepoDesc(postgresDBAdapter);

        GetNamedRefsParams params = GetNamedRefsParams.DEFAULT;
        namedReferencesTable = getNamedReferencesTable(postgresDBAdapter, params);

        globalStateTable = getGlobalStateTable(postgresDBAdapter, conn);

        keysListsTable = getKeyListsTable(postgresDBAdapter, conn);

        refLogHeadTable = getRefLogHeadTable(postgresDBAdapter, conn);


    }
}
