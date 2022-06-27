import org.projectnessie.model.CommitMeta;
import org.projectnessie.model.Content;
import org.projectnessie.server.store.TableCommitMetaStoreWorker;
import org.projectnessie.versioned.StoreWorker;
import org.projectnessie.versioned.persist.inmem.ImmutableInmemoryConfig;
import org.projectnessie.versioned.persist.inmem.InmemoryConfig;
import org.projectnessie.versioned.persist.inmem.InmemoryStore;

public class exportInMemory extends exportNonTxBackend{

    public void getTablesInInMemoryRepo(String nessieServerURL) {
        InmemoryConfig inmemoryConfig = ImmutableInmemoryConfig.builder().build();

        StoreWorker<Content, CommitMeta, Content.Type> storeWorker = new TableCommitMetaStoreWorker();

        InmemoryStore inmemoryStore ;
//        DatabaseAdapter inmemoryDatabaseAdapter = new InmemoryDatabaseAdapterFactory()
//                .newBuilder()
//                .withConfig((NonTransactionalDatabaseAdapterConfig) inmemoryConfig)
//                .withConnector(inmemoryStore)
//                .build(storeWorker);


    }


}
