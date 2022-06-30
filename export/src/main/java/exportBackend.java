import org.projectnessie.model.CommitMeta;
import org.projectnessie.model.Content;
import org.projectnessie.server.store.TableCommitMetaStoreWorker;
import org.projectnessie.versioned.Hash;
import org.projectnessie.versioned.StoreWorker;
import org.projectnessie.versioned.persist.adapter.*;

import java.util.stream.Stream;

public class exportBackend {

    //commit log Table
    public Stream<CommitLogEntry> commitLogTable;

    // Ref Log Table
    public Stream <RefLog> refLogTable;

    // Repo Desc Table
    public RepoDescription repoDescTable ;


    // Key Lists Table
    //Doubtful
    Stream<KeyListEntity> keysListsTable;

    //Initializing Postgres DatabaseAdapter
    public StoreWorker<Content, CommitMeta, Content.Type> storeWorker = new TableCommitMetaStoreWorker();

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
    public void exportCommitLogTableIntoFile()
    {

    }

    //Total Logic can be here
    public void exportRefLogTableIntoFile()
    {

    }

    //Total Logic can be here
    public void exportRepoDescIntoFile()
    {

    }

    //Total Logic can be here
    public void exportKeyListsTableIntoFile( )
    {

    }

}
