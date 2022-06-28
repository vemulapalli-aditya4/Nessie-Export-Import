import org.projectnessie.versioned.GetNamedRefsParams;
import org.projectnessie.versioned.persist.tx.ConnectionWrapper;
import org.projectnessie.versioned.persist.tx.h2.H2DatabaseAdapter;
import org.projectnessie.versioned.persist.tx.postgres.PostgresDatabaseAdapter;

public class exportH2 extends exportTxBackend{

    public void getTablesInH2Repo(String url) throws Exception {

        //Initializing H2 DatabaseAdapter

        //Should initialize postgresDBAdapter properly
        H2DatabaseAdapter h2DatabaseAdapter;

        // System.out.println("Postgres DatabaseAdapter Initialized");

        //Is this right?
        conn = h2DatabaseAdapter.borrowConnection();

        commitLogTable =  getCommitLogTable(h2DatabaseAdapter);

        refLogTable = getRefLogTable(h2DatabaseAdapter);

        repoDescTable = getRepoDesc(h2DatabaseAdapter);

        GetNamedRefsParams params = GetNamedRefsParams.DEFAULT;
        namedReferencesTable = getNamedReferencesTable(h2DatabaseAdapter, params);

        globalStateTable = getGlobalStateTable(h2DatabaseAdapter, conn);

        keysListsTable = getKeyListsTable(h2DatabaseAdapter, conn);

        refLogHeadTable = getRefLogHeadTable(h2DatabaseAdapter, conn);

    }
}
