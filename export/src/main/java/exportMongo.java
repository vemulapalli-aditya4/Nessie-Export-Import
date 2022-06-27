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
import org.projectnessie.versioned.persist.nontx.NonTransactionalOperationContext;
import org.projectnessie.versioned.persist.serialize.AdapterTypes;

import java.util.stream.Stream;

public class exportMongo extends exportNonTxBackend{
    public void getTablesInMongoRepo(String connectionString) throws ReferenceNotFoundException {

        MongoClientConfig mongoClientConfig = ImmutableMongoClientConfig.builder()
                .connectionString(connectionString).databaseName("nessie").build();

        MongoDatabaseClient mongoDBClient = new MongoDatabaseClient();
        mongoDBClient.configure(mongoClientConfig);
        mongoDBClient.initialize();

        // System.out.println("Mongo Database Client Initialized");

        StoreWorker<Content, CommitMeta, Content.Type> storeWorker = new TableCommitMetaStoreWorker();

        MongoDatabaseAdapter mongoDatabaseAdapter = (MongoDatabaseAdapter) new MongoDatabaseAdapterFactory()
                .newBuilder()
                .withConnector(mongoDBClient)
                .withConfig(ImmutableAdjustableNonTransactionalDatabaseAdapterConfig.builder().build())
                .build(storeWorker);


        // System.out.println("Mongo DatabaseAdapter Initialized");

        // Getting the Tables

        //Is This Right??
        NonTransactionalOperationContext ctx = NonTransactionalOperationContext.NON_TRANSACTIONAL_OPERATION_CONTEXT;

        //commit log Table
        Stream<CommitLogEntry> commitLogTable = getCommitLogTable(mongoDatabaseAdapter);

        // Ref Log Table
        Stream <RefLog> refLogTable = getRefLogTable(mongoDatabaseAdapter);

        // Repo Desc Table
        RepoDescription repoDescTable = getRepoDesc(mongoDatabaseAdapter);

        // Global State Log Table
        Stream<AdapterTypes.GlobalStateLogEntry> globalStateLogTable = getGlobalStateLogTable(mongoDatabaseAdapter, ctx);

        // Global Pointer Table
        AdapterTypes.GlobalStatePointer globalPointerTable = getGlobalPointerTable(mongoDatabaseAdapter, ctx);

        // Key Lists Table
        //Doubtful
        Stream<KeyListEntity> keysListsTable = getKeyListsTable(mongoDatabaseAdapter, ctx);

        // ref log head Table


        //Ref names Table
        Stream<AdapterTypes.NamedReference> namedRefTable = getNamedRefsTable(mongoDatabaseAdapter, ctx);

        //ref heads table

        Stream<ReferenceInfo<ByteString>> refs = mongoDatabaseAdapter.namedRefs(GetNamedRefsParams.DEFAULT);

    }


}
