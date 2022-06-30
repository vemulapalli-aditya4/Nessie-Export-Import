import com.google.protobuf.ByteString;
import org.projectnessie.model.CommitMeta;
import org.projectnessie.model.Content;
import org.projectnessie.server.store.TableCommitMetaStoreWorker;
import org.projectnessie.versioned.GetNamedRefsParams;
import org.projectnessie.versioned.ReferenceInfo;
import org.projectnessie.versioned.StoreWorker;
import org.projectnessie.versioned.persist.inmem.*;
import org.projectnessie.versioned.persist.nontx.NonTransactionalDatabaseAdapterConfig;
import org.projectnessie.versioned.persist.nontx.NonTransactionalOperationContext;

import java.util.stream.Stream;

public class exportInMemory extends exportNonTxBackend{

    public void getTablesInInMemoryRepo(String nessieServerURL) {

        //Initializing Inmemory DatabaseAdapter

        InmemoryConfig inmemoryConfig = ImmutableInmemoryConfig.builder().build();

        InmemoryStore inmemoryStore ;

        //Should initialize inmemoryDatabaseAdapter properly
        InmemoryDatabaseAdapter inmemoryDatabaseAdapter = new InmemoryDatabaseAdapterFactory()
                .newBuilder()
                .withConfig((NonTransactionalDatabaseAdapterConfig) inmemoryConfig)
                .withConnector(inmemoryStore)
                .build(storeWorker);

        // System.out.println("Inmemory DatabaseAdapter Initialized");

        // Stream<ReferenceInfo<ByteString>> refs = inmemoryDatabaseAdapter.namedRefs(GetNamedRefsParams.DEFAULT);

        // Getting the Tables

        //Is This Right??
        ctx = NonTransactionalOperationContext.NON_TRANSACTIONAL_OPERATION_CONTEXT;

        commitLogTable = getCommitLogTable(inmemoryDatabaseAdapter);

        refLogTable = getRefLogTable(inmemoryDatabaseAdapter);

        repoDescTable = getRepoDesc( inmemoryDatabaseAdapter );

        globalStateLogTable = getGlobalStateLogTable( inmemoryDatabaseAdapter , ctx);

        globalPointerTable = getGlobalPointerTable( inmemoryDatabaseAdapter , ctx);

        keysListsTable = getKeyListsTable( inmemoryDatabaseAdapter , ctx);

        namedRefsTable = getNamedRefsTable( inmemoryDatabaseAdapter , ctx);

        // ref log head Table ??


        //ref heads table ??
    }

    public void exportInMemoryRepo()
    {
        getTablesInInMemoryRepo();
        //getTablesInDynamoRepo function must be ensured to complete before this function starts executing
        exportIntoFiles();

    }

}
