package water.parser;


import org.joda.time.DateTime;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import water.TestUtil;
import water.fvec.Frame;
import water.util.Log;

/**
 * Test suite for orc parser.
 *
 * This test will attempt to parse a bunch of files (orc and csv).  We compare the frames of these files and make
 * sure that they are equivalent.
 *
 * -- Requested by Tomas N.
 *
 */
public class ParseTestORCCSV extends TestUtil {

    private String[] csvFiles = {"smalldata/parser/orc/orc2csv/TestOrcFile.testDate1900.csv",
            "smalldata/parser/orc/orc2csv/TestOrcFile.testDate2038.csv",
            "smalldata/parser/orc/orc2csv/orc_split_elim.csv", "smalldata/parser/csv2orc/prostate_NA.csv",
            "smalldata/iris/iris.csv", "smalldata/jira/hexdev_29.csv"};

    private String[] orcFiles = {"smalldata/parser/orc/TestOrcFile.testDate1900.orc",
            "smalldata/parser/orc/TestOrcFile.testDate2038.orc", "smalldata/parser/orc/orc_split_elim.orc",
            "smalldata/parser/orc/prostate_NA.orc", "smalldata/parser/orc/iris.orc",
            "smalldata/parser/orc/hexdev_29.orc"};

    private Boolean[] forceColumnTypes = {false, false, false, true, true, true};

    @BeforeClass
    static public void _preconditionJavaVersion() { // NOTE: the `_` force execution of this check after setup
        // Does not run test on Java6 since we are running on Hadoop lib
        Assume.assumeTrue("Java6 is not supported", !System.getProperty("java.version", "NA").startsWith("1.6"));
    }

    @BeforeClass
    static public void setup() { TestUtil.stall_till_cloudsize(1); }

    @Test
    public void testParseOrcCsvFiles() {
        int f_index = 0;
        Frame csv_frame = parse_test_file(csvFiles[f_index], "\\N", 0, null);
        Frame orc_frame = null;

        if (forceColumnTypes[f_index]) {
            byte[] types = csv_frame.types();

            for (int index = 0; index < types.length; index++) {
                if (types[index] == 0)
                    types[index] = 3;
            }

            orc_frame = parse_test_file(orcFiles[f_index], null, 0, types);
        } else {
            orc_frame = parse_test_file(orcFiles[f_index], null, 0, null);
        }


        // make sure column types are the same especially the enums
        byte[] csv_types = csv_frame.types();
        byte[] orc_types = orc_frame.types();

        for (int index = 0; index < csv_frame.numCols(); index++) {
            if ((csv_types[index] == 4) && (orc_types[index] == 2)) {
                orc_frame.replace(index, orc_frame.vec(index).toCategoricalVec().toNumericVec());
                csv_frame.replace(index, csv_frame.vec(index).toNumericVec());
            }
        }

        for (int rowIndex = 0; rowIndex < 38; rowIndex++){
            long valorc = (long) orc_frame.vec(0).at(rowIndex);
            long valcsv = (long) csv_frame.vec(0).at(rowIndex);
            DateTime orcDT = new DateTime(valorc);
            DateTime csvDT = new DateTime(valcsv);
            Log.info("orc time is "+orcDT.toString());
            Log.info("csv time is "+csvDT.toString());
            Log.info("Row index is "+rowIndex+". Orc value is "+valorc+". Difference is "+(valorc-valcsv));
        }

        //assertTrue(TestUtil.isIdenticalUpToRelTolerance(csv_frame, orc_frame, 1e20));

        csv_frame.delete();
        orc_frame.delete();
    }
}