package com.athensease.dataretrieval;

import com.athensease.sights.Sight;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RouteDataFetcherTest {

    @Test
    void testFetchRouteData_withMockResponse() throws Exception {
        // Prepare mock data
        List<Sight> sights = Arrays.asList(
                new Sight("Place A", "12.34,56.78", 0, 0, "", false),
                new Sight("Place B", "23.45,67.89", 0, 0, "", false),
                new Sight("Place C", "34.56,78.90", 0, 0, "", false)
        );

        // Mock the API response
        String mockJsonResponse = """
            {
                "routes": [
                    {
                        "overview_polyline": {
                            "points": "mockPolyline"
                        },
                        "legs": [
                            {
                                "start_location": { "lat": 12.34, "lng": 56.78 },
                                "end_location": { "lat": 23.45, "lng": 67.89 }
                            },
                            {
                                "start_location": { "lat": 23.45, "lng": 67.89 },
                                "end_location": { "lat": 34.56, "lng": 78.90 }
                            }
                        ]
                    }
                ]
            }
        """;

        // Mock URL and HttpURLConnection
        URL mockUrl = mock(URL.class);
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        when(mockUrl.openConnection()).thenReturn(mockConnection);
        when(mockConnection.getInputStream()).thenReturn(
                new java.io.ByteArrayInputStream(mockJsonResponse.getBytes())
        );

        // Spy on MapURLBuilder
        MapURLBuilder mockUrlBuilder = Mockito.spy(new MapURLBuilder(sights));
        doReturn("http://mock.url").when(mockUrlBuilder).getUrl();

        // Test RouteDataFetcher
        RouteDataFetcher dataFetcher = new RouteDataFetcher(sights);
        RouteDataFetcher.RouteData routeData = dataFetcher.fetchRouteData();

        // Validate the route data
        assertNotNull(routeData, "Route data should not be null");
        assertEquals("12.34,56.78", routeData.getOrigin(), "Origin should match");
        assertEquals("34.56,78.90", routeData.getDestination(), "Destination should match");
        assertEquals(Arrays.asList("23.45,67.89"), routeData.getWaypoints(), "Waypoints should match");
        assertEquals("mockPolyline", routeData.getOverviewPolyline(), "Polyline should match");
    }
}