package uk.co.ostmodern.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test for {@link Util}.
 *
 * @author rahulsingh
 */
public class UtilTest {

    private static final String VALID_API_IMAGE_PATH = "/api/images/img_asbfd34bnasd";
    private static final String EXPECTED_VALID_RELATIVE_API_PATH = "api/images/img_asbfd34bnasd";

    /**
     * Given a valid api path, when it is passed into the Util method, then it returns a valid relative
     * api path without a forward-slash at the beginning.
     */
    @Test
    public void testGetRelativeImageApiPathGivenValidApiPath() {
        String relativeApiPath = Util.getRelativeImageApiPath(VALID_API_IMAGE_PATH);

        assertEquals( "The actual and expected Strings did not match", EXPECTED_VALID_RELATIVE_API_PATH,
               relativeApiPath);
    }

    /**
     * Given a null api path, when it is passed into the Util method, then it returns null.
     */
    @Test
    public void testGetRelativeImageApiPathGivenNullApiPath() {
        String relativeApiPath = Util.getRelativeImageApiPath(null);

        assertNull("Return from the getRelativeImageApiPath should have returned null", relativeApiPath);
    }
}
