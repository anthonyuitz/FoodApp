package com.khmkau.codeu.foodapp.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

/*
    Uncomment this class when you are ready to test your UriMatcher.  Note that this class utilizes
    constants that are declared with package protection inside of the UriMatcher, which is why
    the test must be in the same data package as the Android app code.  Doing the test this way is
    a nice compromise between data hiding and testability.
 */
public class TestUriMatcher extends AndroidTestCase {
    private static final long TEST_DATE = 1419033600L;  // December 20th, 2014
    private static final String TEST_FOODGROUP = "Meat";

    // info
    private static final Uri TEST_INFO_DIR = FoodContract.InfoEntry.CONTENT_URI;

    // current
    private static final Uri TEST_CURRENT_DIR = FoodContract.CurrentEntry.CONTENT_URI;
    private static final Uri TEST_CURRENT_WITH_CALORIES = FoodContract.CurrentEntry.buildCurrentCalories(0, 100);
    private static final Uri TEST_CURRENT_WITH_EXPIRATION = FoodContract.CurrentEntry.buildCurrentExpiration(TEST_DATE);
    private static final Uri TEST_CURRENT_WITH_FOODGROUP = FoodContract.CurrentEntry.buildCurrentFoodGroup(TEST_FOODGROUP);

    private static final Uri TEST_CONSUMED_DIR = FoodContract.ConsumedEntry.CONTENT_URI;
    private static final Uri TEST_CONSUMED_WITH_DATE = FoodContract.ConsumedEntry.buildConsumedDate(TEST_DATE);
    private static final Uri TEST_CONSUMED_WITH_DATERANGE = FoodContract.ConsumedEntry.buildConsumedDateRange(TEST_DATE, TEST_DATE);

    private static final Uri TEST_THROWN_DIR = FoodContract.ThrownEntry.CONTENT_URI;
    private static final Uri TEST_THROWN_DATE = FoodContract.ThrownEntry.buildThrownDate(TEST_DATE);
    private static final Uri TEST_THROWN_DATERANGE = FoodContract.ThrownEntry.buildThrownDateRange(TEST_DATE, TEST_DATE);

    /*
        Students: This function tests that your UriMatcher returns the correct integer value
        for each of the Uri types that our ContentProvider can handle.  Uncomment this when you are
        ready to test your UriMatcher.
     */
    public void testUriMatcher() {
        UriMatcher testMatcher = FoodProvider.buildUriMatcher();

        assertEquals("Error: The INFO URI was matched incorrectly.",
                testMatcher.match(TEST_INFO_DIR), FoodProvider.INFO);

        assertEquals("Error: The CURRENT URI was matched incorrectly.",
                testMatcher.match(TEST_CURRENT_DIR), FoodProvider.CURRENT);
        assertEquals("Error: The CURRENT WITH CALORIES URI was matched incorrectly.",
                testMatcher.match(TEST_CURRENT_WITH_CALORIES), FoodProvider.CURRENT_WITH_INFO_AND_CALORIES);
        assertEquals("Error: The CURRENT WITH EXPIRATION URI was matched incorrectly.",
                testMatcher.match(TEST_CURRENT_WITH_EXPIRATION), FoodProvider.CURRENT_WITH_EXPIRATION);
        assertEquals("Error: The CURRENT WITH FOODGROUP URI was matched incorrectly.",
                testMatcher.match(TEST_CURRENT_WITH_FOODGROUP), FoodProvider.CURRENT_WITH_INFO_AND_FOODGROUP);

        assertEquals("Error: The CONSUMED URI was matched incorrectly.",
                testMatcher.match(TEST_CONSUMED_DIR), FoodProvider.CONSUMED);
        assertEquals("Error: The CONSUMED WITH DATE URI was matched incorrectly.",
                testMatcher.match(TEST_CONSUMED_WITH_DATE), FoodProvider.CONSUMED_WITH_INFO_AND_DATE);
        assertEquals("Error: The CONSUMED WITH DATE RANGE URI was matched incorrectly.",
                testMatcher.match(TEST_CONSUMED_WITH_DATERANGE), FoodProvider.CONSUMED_WITH_INFO_AND_DATERANGE);

        assertEquals("Error: The THROWN URI was matched incorrectly.",
                testMatcher.match(TEST_THROWN_DIR), FoodProvider.THROWN);
        assertEquals("Error: The THROWN WITH DATE URI was matched incorrectly.",
                testMatcher.match(TEST_THROWN_DATE), FoodProvider.THROWN_WITH_DATE);
        assertEquals("Error: The THROWN WITH DATE RANGE URI was matched incorrectly.",
                testMatcher.match(TEST_THROWN_DATERANGE), FoodProvider.THROWN_WITH_DATERANGE);


    }
}
