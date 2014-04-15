import com.luxoft.dao.CategoryDao;
import com.luxoft.dao.ContactDao;
import com.luxoft.dao.MRDao;
import com.luxoft.model.Category;
import com.luxoft.model.Contact;
import com.luxoft.model.MRResult;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by aeliseev on 08.04.2014
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext.xml"})
public class TestMongo {

    @Autowired
    ContactDao cDao;

    @Autowired
    CategoryDao catDao;

    @Autowired
    MRDao mrDao;

    @Test
    @Ignore
    public void testInsert() {

        long start = System.nanoTime();
        long count = 1_000_000;

        for (int i=1; i<=count; i++) {
            Contact c = new Contact("antonio" + i, 12L + i, "eli@mm.ru", null);
            cDao.save(c);
        }

        long stop = System.nanoTime();

        System.out.println("added " + count + " records  in " + (stop - start) / 1000000 + " ms");
    }

    @Test
    @Ignore
    public void testSafeInsert() {

        Category catSpammers = catDao.getAllCats().get(0);
        Category catGF = catDao.getAllCats().get(1);

        Contact c1 = new Contact("Freaky", 22L, "fre@mm.ru", Arrays.asList(new ObjectId(catSpammers.getId())));
        cDao.save(c1);
        System.out.println("saved - " + c1.getId());

        Contact c2 = new Contact("lawful", 34L, "lawme@gov.ru", Arrays.asList(new ObjectId(catGF.getId())));
        cDao.save(c2);
        System.out.println("saved - " + c2.getId());
    }

    @Test
    @Ignore
    public void testBulkInsert() {

        long start = System.nanoTime();
        int count = 5_000_000;

        Collection<Contact> insertedData = new ArrayList<>(count);

        for (int i=4_000_001; i<=count; i++) {
            Contact c = new Contact("antonio" + i, 12L + i, "eli@mm.ru", null);
            insertedData.add(c);
        }

        cDao.bulkInsert(insertedData);

        long stop = System.nanoTime();

        System.out.println("BULK added " + count + " records  in " + (stop - start) / 1000000 + " ms");

        System.out.println("Size now = " + cDao.getCount());
    }

    @Test
    @Ignore
    public void testFind() {

        cDao.ensureIndex("number");

        long start1 = System.nanoTime();
        Collection<Contact> res1 = cDao.getByNumberComparison(true, 1000L, 104659, 37);
        long stop1 = System.nanoTime();
        System.out.println("executed in " + (stop1 - start1) / 1000000 + " ms");

        long start2 = System.nanoTime();
        Collection<Contact> res2 = cDao.getByNumberComparison(true, 3000000L, 104, 37);
        long stop2 = System.nanoTime();
        System.out.println("executed in " + (stop2 - start2) / 1000000 + " ms");
    }

    @Test
    @Ignore
    public void testMR() {
        for (MRResult vo: mrDao.doMR()) {
            System.out.println(vo);
        }
    }

    @Test
    public void testMR2() {
        for (DBObject dbo: mrDao.doMR2().results()) {
            System.out.println(dbo.toString());
        }
    }
}
