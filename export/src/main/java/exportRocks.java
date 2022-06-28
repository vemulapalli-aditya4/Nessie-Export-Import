import com.google.protobuf.ByteString;
import org.projectnessie.model.CommitMeta;
import org.projectnessie.model.Content;
import org.projectnessie.server.store.TableCommitMetaStoreWorker;
import org.projectnessie.versioned.GetNamedRefsParams;
import org.projectnessie.versioned.ReferenceInfo;
import org.projectnessie.versioned.StoreWorker;
import org.projectnessie.versioned.persist.dynamodb.DynamoDatabaseAdapter;
import org.projectnessie.versioned.persist.nontx.NonTransactionalOperationContext;
import org.projectnessie.versioned.persist.rocks.RocksDatabaseAdapter;

import java.util.stream.Stream;

public class exportRocks extends exportNonTxBackend{

    public void getTablesInRocksRepo() {

        //Initializing Rocks DatabaseAdapter

        StoreWorker<Content, CommitMeta, Content.Type> storeWorker = new TableCommitMetaStoreWorker();

        //Should initialize rocksDatabaseAdapter properly
        RocksDatabaseAdapter rocksDatabaseAdapter;

        // System.out.println("Rocks DatabaseAdapter Initialized");

        Stream<ReferenceInfo<ByteString>> refs = rocksDatabaseAdapter.namedRefs(GetNamedRefsParams.DEFAULT);

        // Getting the Tables

        //Is This Right??
        ctx = NonTransactionalOperationContext.NON_TRANSACTIONAL_OPERATION_CONTEXT;

        commitLogTable = getCommitLogTable(rocksDatabaseAdapter);

        refLogTable = getRefLogTable(rocksDatabaseAdapter);

        repoDescTable = getRepoDesc( rocksDatabaseAdapter );

        globalStateLogTable = getGlobalStateLogTable( rocksDatabaseAdapter , ctx);

        globalPointerTable = getGlobalPointerTable(rocksDatabaseAdapter , ctx);

        keysListsTable = getKeyListsTable( rocksDatabaseAdapter , ctx);

        namedRefsTable = getNamedRefsTable(rocksDatabaseAdapter , ctx);

        // ref log head Table ??


        //ref heads table ??

    }
}
