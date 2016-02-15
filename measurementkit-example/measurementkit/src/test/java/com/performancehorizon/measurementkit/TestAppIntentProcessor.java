package com.performancehorizon.measurementkit;

import android.content.Intent;
import android.net.Uri;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by owainbrown on 14/01/16.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.DEFAULT, sdk = 21)
public class TestAppIntentProcessor {

    @Test
    public void testWithClickedIntent() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_ANSWER);
        intent.putExtra(MeasurementService.TrackingConstants.TRACKING_INTENT_CAMREF, "camref");

        AppClickIntentProcessor processor = new AppClickIntentProcessor(intent);

        Intent filteredintent = processor.getFilteredIntent();

        Assert.assertEquals(processor.getCamref(), "camref");
        Assert.assertNull(filteredintent.getStringExtra(MeasurementService.TrackingConstants.TRACKING_INTENT_CAMREF));
        Assert.assertEquals(intent.getAction(), filteredintent.getAction());
    }

    @Test
    public void testWithUnclickedIntent() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_ANSWER);

        AppClickIntentProcessor processor = new AppClickIntentProcessor(intent);

        Intent filteredintent = processor.getFilteredIntent();

        Assert.assertNull(processor.getCamref());
        Assert.assertNull(processor.getFilteredIntent());

    }

    @Test
    public void testWithClickedIntentAndCamrefKey() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_ANSWER);
        intent.putExtra("key", "camref");

        AppClickIntentProcessor processor = new AppClickIntentProcessor(intent, "key");

        Intent filteredintent = processor.getFilteredIntent();

        Assert.assertEquals(processor.getCamref(), "camref");
        Assert.assertNull(filteredintent.getStringExtra("key"));
        Assert.assertEquals(intent.getAction(), filteredintent.getAction());
    }

    @Test
    public void testWithUnclickedIntentAndCamrefKey() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_ANSWER);

        AppClickIntentProcessor processor = new AppClickIntentProcessor(intent, "key");

        Intent filteredintent = processor.getFilteredIntent();

        Assert.assertNull(processor.getCamref());
        Assert.assertNull(processor.getFilteredIntent());

    }
}

