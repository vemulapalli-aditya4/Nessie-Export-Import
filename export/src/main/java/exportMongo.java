import com.google.protobuf.ByteString;
import org.projectnessie.model.CommitMeta;
import org.projectnessie.model.Content;
import org.projectnessie.server.store.TableCommitMetaStoreWorker;
import org.projectnessie.versioned.GetNamedRefsParams;
import org.projectnessie.versioned.ReferenceInfo;
import org.projectnessie.versioned.ReferenceNotFoundException;
import org.projectnessie.versioned.StoreWorker;
import org.projectnessie.versioned.persist.adapter.CommitLogEntry;
import org.projectnessie.versioned.persist.adapter.KeyListEntity;
import org.projectnessie.versioned.persist.adapter.RefLog;
import org.projectnessie.versioned.persist.adapter.RepoDescription;
import org.projectnessie.versioned.persist.mongodb.*;
import org.projectnessie.versioned.persist.nontx.ImmutableAdjustableNonTransactionalDatabaseAdapterConfig;
import org.projectnessie.versioned.persist.nontx.NonTransactionalDatabaseAdapterConfig;
import org.projectnessie.versioned.persist.nontx.NonTransactionalOperationContext;
import org.projectnessie.versioned.persist.serialize.AdapterTypes;

import java.util.stream.Stream;

public class exportMongo extends exportNonTxBackend{

    public void getTablesInMongoRepo(String connectionString){

        //Initializing MongoDatabaseAdapter

        MongoClientConfig mongoClientConfig = ImmutableMongoClientConfig.builder()
                .connectionString(connectionString).databaseName("nessie").build();

        MongoDatabaseClient mongoDBClient = new MongoDatabaseClient();
        mongoDBClient.configure(mongoClientConfig);
        mongoDBClient.initialize();

        NonTransactionalDatabaseAdapterConfig dbAdapterConfig;
        dbAdapterConfig = ImmutableAdjustableNonTransactionalDatabaseAdapterConfig.builder().build();

        // System.out.println("Mongo Database Client Initialized");

        MongoDatabaseAdapter mongoDatabaseAdapter = (MongoDatabaseAdapter) new MongoDatabaseAdapterFactory()
                .newBuilder()
                .withConnector(mongoDBClient)
                .withConfig(dbAdapterConfig)
                .build(storeWorker);

        // System.out.println("Mongo DatabaseAdapter Initialized");

        // Stream<ReferenceInfo<ByteString>> refs = mongoDatabaseAdapter.namedRefs(GetNamedRefsParams.DEFAULT);

        // Getting the Tables

        //Is This Right??
        ctx = NonTransactionalOperationContext.NON_TRANSACTIONAL_OPERATION_CONTEXT;

        commitLogTable = getCommitLogTable(mongoDatabaseAdapter);

        refLogTable = getRefLogTable(mongoDatabaseAdapter);

        repoDescTable = getRepoDesc(mongoDatabaseAdapter);

        globalStateLogTable = getGlobalStateLogTable(mongoDatabaseAdapter, ctx);

        globalPointerTable = getGlobalPointerTable(mongoDatabaseAdapter, ctx);

        keysListsTable = getKeyListsTable(mongoDatabaseAdapter, ctx);

        namedRefsTable = getNamedRefsTable(mongoDatabaseAdapter, ctx);

        // ref log head Table ??


        //ref heads table ??


    }


    public void exportMongoRepo(String connectionString)
    {
        getTablesInMongoRepo(connectionString);


        //getTablesInDynamoRepo function must be ensured to complete before this function starts executing
        exportIntoFiles();

    }

}
