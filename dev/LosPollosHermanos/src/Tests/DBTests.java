package Tests;

import DB.DBManager;
import Tests.losPollosAnnotations.UseTestingDatabase;
import Tests.losPollosAnnotations.UseTestingDatabaseExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(UseTestingDatabaseExtension.class)
public class DBTests {

    @UseTestingDatabase
    @Test
    public void testCreateDeliveryFile() {
        //DBManager.getInstance().getDeliveryFileController()
    }
}
