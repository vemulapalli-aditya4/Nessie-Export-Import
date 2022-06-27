import com.google.protobuf.ByteString;
import org.projectnessie.versioned.GetNamedRefsParams;
import org.projectnessie.versioned.Hash;
import org.projectnessie.versioned.ReferenceInfo;
import org.projectnessie.versioned.ReferenceNotFoundException;
import org.projectnessie.versioned.persist.adapter.ContentId;
import org.projectnessie.versioned.persist.adapter.KeyListEntity;
import org.projectnessie.versioned.persist.tx.ConnectionWrapper;
import org.projectnessie.versioned.persist.tx.TxDatabaseAdapter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class exportTxBackend extends exportBackend{
    public Stream<ReferenceInfo<ByteString>> getNamedReferencesTable(TxDatabaseAdapter txDBAdapter, GetNamedRefsParams params) throws ReferenceNotFoundException {
        Stream<ReferenceInfo<ByteString>> namedReferencesTable = txDBAdapter.namedRefs(params); ;

        return namedReferencesTable;
    }

    public Map<ContentId, ByteString> getGlobalStateTable(TxDatabaseAdapter txDatabaseAdapter, ConnectionWrapper conn)
    {
        //should find content ids
        Set<ContentId> contentIds;
        // function fetchGlobalStates is protected in AbstractDatabaseAdapter
        Map<ContentId, ByteString> globalStateTable = txDatabaseAdapter.fetchGlobalStates(conn, contentIds);

        return globalStateTable;
    }

    public Stream<KeyListEntity> getKeyListsTable( TxDatabaseAdapter txDatabaseAdapter, ConnectionWrapper conn )
    {
        //Not sure correct or not
        //Should get this
        List<Hash> keyListsIds;

        Stream<KeyListEntity> keysListsTable = txDatabaseAdapter.fetchKeyLists(conn, keyListsIds );
        return keysListsTable ;

    }

    public RefLogHead getRefLogHeadTable (TxDatabaseAdapter txDatabaseAdapter, ConnectionWrapper conn  )
    {
        //2 problems

        // RefLogHead and ImmutableRefLoGHead are Package Private
        // And the function getRefLogHead is protected
        RefLogHead refLogHeadTable = txDatabaseAdapter.getRefLogHead(conn);

        return  refLogHeadTable;
    }

    //Total Logic can be here
    public void exportNamedReferencesTableIntoFile( Stream<ReferenceInfo<ByteString>> namedReferencesTable)
    {

    }

    //Total Logic can be here
    public void exportGlobalStateTableIntoFile( Map<ContentId, ByteString> globalStateTable)
    {

    }

    //Total Logic can be here
    //RefLog is private interface
    public void exportRefLogHeadTableIntoFile(RefLogHead refLogHeadTable)
    {

    }

}
