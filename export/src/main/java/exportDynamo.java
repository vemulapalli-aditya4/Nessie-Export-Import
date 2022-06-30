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
import org.projectnessie.versioned.persist.dynamodb.*;
import org.projectnessie.versioned.persist.nontx.ImmutableAdjustableNonTransactionalDatabaseAdapterConfig;
import org.projectnessie.versioned.persist.nontx.NonTransactionalDatabaseAdapterConfig;
import org.projectnessie.versioned.persist.nontx.NonTransactionalOperationContext;
import org.projectnessie.versioned.persist.serialize.AdapterTypes;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.stream.Stream;

public class exportDynamo extends exportNonTxBackend{

    public void getTablesInDynamoRepo() {

        //Should initialize these
        String  endpointURI;
        String region;
        AwsCredentialsProvider credentialsProvider ;
        DynamoDbClient dynamoDbClient;

        DynamoClientConfig dynamoClientConfig = ImmutableDefaultDynamoClientConfig
                .builder()
                .endpointURI(endpointURI)
                .region(region)
                .credentialsProvider(credentialsProvider)
                .dynamoDbClient(dynamoDbClient)
                .build();

        //Initializing DynamoDatabaseAdapter , error
        DynamoDatabaseClient dynamoDatabaseClient = new DynamoDatabaseClient();
        dynamoDatabaseClient.configure(dynamoClientConfig);
        dynamoDatabaseClient.initialize();

        NonTransactionalDatabaseAdapterConfig dbAdapterConfig;
        dbAdapterConfig = ImmutableAdjustableNonTransactionalDatabaseAdapterConfig.builder().build();

        // System.out.println("Dynamo Database Client Initialized");

        //Should initialize dynamoDatabaseAdapter properly

        DynamoDatabaseAdapter dynamoDatabaseAdapter = (DynamoDatabaseAdapter) new DynamoDatabaseAdapterFactory()
                .newBuilder()
                .withConnector(dynamoDatabaseClient)
                .withConfig(dbAdapterConfig)
                .build(storeWorker);

        // System.out.println("Dynamo DatabaseAdapter Initialized");

        // Stream<ReferenceInfo<ByteString>> refs = dynamoDatabaseAdapter.namedRefs(GetNamedRefsParams.DEFAULT);

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


    public void exportDynamoRepo()
    {
        getTablesInDynamoRepo();

        //getTablesInDynamoRepo function must be ensured to complete before this function starts executing
        exportIntoFiles();

    }
}
