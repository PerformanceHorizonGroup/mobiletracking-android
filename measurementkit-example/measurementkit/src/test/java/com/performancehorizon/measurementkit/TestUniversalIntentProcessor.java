package com.performancehorizon.measurementkit;

import android.content.Intent;
import android.net.Uri;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by owainbrown on 22/01/16.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.DEFAULT, sdk = 21)
public class TestUniversalIntentProcessor {

    @Test
    public void testWithSchemeLink() {

        Intent notauniversalintent = new Intent(Intent.ACTION_DEFAULT, Uri.parse("exactview://open"));

        UniversalIntentProcessor intentprocessor = new UniversalIntentProcessor(notauniversalintent, new TrackingURLHelper(false));

        Assert.assertNull(intentprocessor.getCamref());
    }

    @Test
    public void testWithWrongDomainLink() {

        Intent notauniversalintent = new Intent(Intent.ACTION_DEFAULT, Uri.parse("http://somewhere/someday"));

        UniversalIntentProcessor intentprocessor = new UniversalIntentProcessor(notauniversalintent, new TrackingURLHelper(false));

        Assert.assertNull(intentprocessor.getCamref());
    }

    @Test
    public void testWithUniversal() {

        Intent universalintent = new Intent(Intent.ACTION_DEFAULT, Uri.parse("https://m.prf.hn/click/camref:camref/"));

        UniversalIntentProcessor intentprocessor = new UniversalIntentProcessor(universalintent, new TrackingURLHelper(false));

        Assert.assertEquals(intentprocessor.getCamref(), "camref");

        //no alternate destination is provided.
        Assert.assertEquals(intentprocessor.getFilteredIntent().getData().toString(), "https://m.prf.hn/click/camref:camref/");
    }

    @Test
    public void testWithDestination() {

        Intent universalintent = new Intent(Intent.ACTION_DEFAULT, Uri.parse("https://m.prf.hn/click/camref:camref/destination:http%3A%2F%2Fwww.google.com/"));

        UniversalIntentProcessor intentprocessor = new UniversalIntentProcessor(universalintent, new TrackingURLHelper(false));

        Assert.assertEquals(intentprocessor.getCamref(), "camref");

        //no alternate destination is provided.
        Assert.assertEquals(intentprocessor.getFilteredIntent().getData().toString(), "http://www.google.com");
    }

    @Test
    public void testWithDeepLink() {
        Intent universalintent = new Intent(Intent.ACTION_DEFAULT, Uri.parse("https://m.prf.hn/click/camref:camref/?deep_link=http%3A%2F%2Fwww.google.com"));

        UniversalIntentProcessor intentprocessor = new UniversalIntentProcessor(universalintent, new TrackingURLHelper(false));

        Assert.assertEquals(intentprocessor.getCamref(), "camref");

        //no alternate destination is provided.
        Assert.assertEquals(intentprocessor.getFilteredIntent().getData().toString(), "http://www.google.com");
    }
}
