package com.performancehorizon.measurementkit;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;
import android.test.suitebuilder.annotation.SmallTest;

import com.performancehorizon.measurementkit.Event;
import com.performancehorizon.measurementkit.Matchers;
import com.performancehorizon.measurementkit.MeasurementService;
import com.performancehorizon.measurementkit.TrackingRequestFactory;
import com.performancehorizon.measurementkit.TrackingRequestQueue;

import org.junit.Before;
import org.mockito.ArgumentMatcher;

import java.util.HashMap;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by owainbrown on 20/03/15.
 */
public class Test_MobileTrackingService_Events extends InstrumentationTestCase{

    private MeasurementService serviceundertest;
    private Context mockedContext;
    private ConnectivityManager mockedConnectivity;
    private NetworkInfo mockedNetworkInfo;
    private SharedPreferences mockedpreferences;

    private TrackingRequestQueue registrationQueue;
    private TrackingRequestQueue eventQueue;

    @Before
    public void setUp() {

        System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

        mockedContext = mock(MockContext.class);
        mockedConnectivity = mock(ConnectivityManager.class);
        mockedNetworkInfo = mock(NetworkInfo.class);

        registrationQueue = mock(TrackingRequestQueue.class);
        eventQueue = mock(TrackingRequestQueue.class);

        serviceundertest = new MeasurementService(registrationQueue, eventQueue, new TrackingRequestFactory());
        when(mockedContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(mockedConnectivity);
        when(mockedConnectivity.getActiveNetworkInfo()).thenReturn(mockedNetworkInfo);
        when(mockedNetworkInfo.isAvailable()).thenReturn(true);

        mockedpreferences = mock(SharedPreferences.class);
        when(mockedContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockedpreferences);

        String clickref = "clickreference";

        when(mockedpreferences.getString(anyString(), anyString())).thenReturn(clickref);
        when(mockedpreferences.getBoolean(anyString(), anyBoolean())).thenReturn(true);
    }

    @SmallTest
    public void testAddEvent() {

        String adref = "ad reference";
        String camref = "campaign reference";
        serviceundertest.initialise(mockedContext,null, adref, camref);

        Event event= mock(Event.class);

        HashMap<String, Object> data = new HashMap<>();
        data.put("whatever", "whatever");
        String tag = "purchase";

        when(event.getEventData()).thenReturn(data);
        when(event.getSalesData()).thenReturn(data);
        when(event.getEventTag()).thenReturn("whatever");

        serviceundertest.trackEvent(event);

        verify(event).getEventData();
        verify(event).getSalesData();
        verify(event).getEventTag();

        //an event request should have been queued.
        verify(eventQueue).enqueueRequest(argThat(new Matchers.IsEventRequest()));
    }

    @SmallTest
    public void testEventNotConfigured() {

        String adref = "ad reference";
        String camref = "campaign reference";

        Event event= mock(Event.class);

        HashMap<String, Object> data = new HashMap<>();
        data.put("whatever", "whatever");
        String tag = "purchase";

        when(event.getEventData()).thenReturn(data);
        when(event.getSalesData()).thenReturn(data);
        when(event.getEventTag()).thenReturn("whatever");

        serviceundertest.trackEvent(event);

        verify(event, times(0)).getEventData();
        verify(event, times(0)).getSalesData();
        verify(event, times(0)).getEventTag();

        verify(eventQueue, times(0)).enqueueRequest(argThat(new Matchers.IsEventRequest()));
    }
}
