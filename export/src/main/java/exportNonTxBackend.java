import org.projectnessie.versioned.Hash;
import org.projectnessie.versioned.persist.adapter.KeyListEntity;
import org.projectnessie.versioned.persist.nontx.NonTransactionalDatabaseAdapter;
import org.projectnessie.versioned.persist.nontx.NonTransactionalOperationContext;
import org.projectnessie.versioned.persist.serialize.AdapterTypes;

import java.util.List;
import java.util.stream.Stream;

public class exportNonTxBackend extends exportBackend {

    public Stream<AdapterTypes.GlobalStateLogEntry> getGlobalStateLogTable(NonTransactionalDatabaseAdapter nonTxDbAdapter, NonTransactionalOperationContext ctx)
    {
        //globalLogFetcher is private in NonTxDatabaseAdapter
        ///** Reads from the global-state-log starting at the given global-state-log-ID. */
        // private Stream<AdapterTypes.GlobalStateLogEntry> globalLogFetcher(NonTransactionalOperationContext ctx)
        Stream<AdapterTypes.GlobalStateLogEntry> globalStateLogTable = nonTxDbAdapter.globalLogFetcher( ctx);

        return globalStateLogTable;
    }

    public AdapterTypes.GlobalStatePointer getGlobalPointerTable(NonTransactionalDatabaseAdapter nonTxDbAdapter, NonTransactionalOperationContext ctx )
    {
        //fetchGlobalPointer function is protected in NonTxDatabaseAdapter
        AdapterTypes.GlobalStatePointer globalPointerTable = nonTxDbAdapter.fetchGlobalPointer(ctx);
        return globalPointerTable;
    }

    public Stream<KeyListEntity> getKeyListsTable(NonTransactionalDatabaseAdapter nonTxDbAdapter, NonTransactionalOperationContext ctx )
    {
        //Not sure correct or not
        //Should get this
        List<Hash> keyListsIds;

        Stream<KeyListEntity> keysListsTable = nonTxDbAdapter.fetchKeyLists(ctx , keyListsIds );
        return keysListsTable ;

    }

    public Stream<AdapterTypes.NamedReference> getNamedRefsTable(NonTransactionalDatabaseAdapter nonTxDbAdapter, NonTransactionalOperationContext ctx)
    {
        //FetchNamedReferences is a Protected Function in the Non Tx Database Adapter
        Stream<AdapterTypes.NamedReference> namedRefsTable = nonTxDbAdapter.fetchNamedReferences(ctx);

        return namedRefsTable;
    }
}
