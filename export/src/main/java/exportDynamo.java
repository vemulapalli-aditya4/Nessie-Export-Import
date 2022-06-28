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
import org.projectnessie.versioned.persist.dynamodb.DynamoDatabaseAdapter;
import org.projectnessie.versioned.persist.nontx.NonTransactionalOperationContext;
import org.projectnessie.versioned.persist.serialize.AdapterTypes;

import java.util.stream.Stream;

public class exportDynamo extends exportNonTxBackend{

    public void getTablesInDynamoRepo() throws ReferenceNotFoundException {

        //Initializing DynamoDatabaseAdapter

        StoreWorker<Content, CommitMeta, Content.Type> storeWorker = new TableCommitMetaStoreWorker();


        //Should initialize dynamoDatabaseAdapter properly
        DynamoDatabaseAdapter dynamoDatabaseAdapter;

        // System.out.println("Dynamo DatabaseAdapter Initialized");

        Stream<ReferenceInfo<ByteString>> refs = dynamoDatabaseAdapter.namedRefs(GetNamedRefsParams.DEFAULT);

        // Getting the Tables

        //Is This Right??
        ctx = NonTransactionalOperationContext.NON_TRANSACTIONAL_OPERATION_CONTEXT;

        commitLogTable = getCommitLogTable(dynamoDatabaseAdapter);

        refLogTable = getRefLogTable(dynamoDatabaseAdapter);

        repoDescTable = getRepoDesc( dynamoDatabaseAdapter );

        globalStateLogTable = getGlobalStateLogTable( dynamoDatabaseAdapter , ctx);

        globalPointerTable = getGlobalPointerTable(dynamoDatabaseAdapter , ctx);

        keysListsTable = getKeyListsTable( dynamoDatabaseAdapter , ctx);

        namedRefsTable = getNamedRefsTable(dynamoDatabaseAdapter , ctx);

        // ref log head Table ??


        //ref heads table ??
    }


}
