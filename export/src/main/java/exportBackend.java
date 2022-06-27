import org.projectnessie.versioned.Hash;
import org.projectnessie.versioned.persist.adapter.*;

import java.util.stream.Stream;

public class exportBackend {

    public Stream<CommitLogEntry> getCommitLogTable(DatabaseAdapter dbAdapter)
    {
        Stream<CommitLogEntry> StreamOfAllCommitLogEntries = dbAdapter.scanAllCommitLogEntries();

        return  StreamOfAllCommitLogEntries;
    }

    // I think should be split into tx and non tx as
    // getting initialHash Logic might be difficult
    //like adding abstract method to get initialHash
    //which can be implemented in exportTx and exportNonTx
    public Stream <RefLog> getRefLogTable(DatabaseAdapter dbAdapter)
    {
        //Should get the initial Hash
        Hash initialHash;

        Stream <RefLog> StreamOfAllRefLogEntries;

        StreamOfAllRefLogEntries = dbAdapter.refLog(initialHash);

        return  StreamOfAllRefLogEntries;
    }

    public RepoDescription getRepoDesc(DatabaseAdapter dbAdapter)
    {
        RepoDescription repoDescTable = dbAdapter.fetchRepositoryDescription();

        return repoDescTable;
    }

    //Total Logic can be here
    public void exportCommitLogTableIntoFile(Stream<CommitLogEntry> commitLogTable )
    {

    }

    //Total Logic can be here
    public void exportRefLogTableIntoFile(Stream <RefLog> refLogTable )
    {

    }

    //Total Logic can be here
    public void exportRepoDescIntoFile(RepoDescription repoDescTable)
    {

    }

    //Total Logic can be here
    public void exportKeyListsTableIntoFile( Stream<KeyListEntity> keysListsTable )
    {

    }

}
