package com.athensease.dataretrieval;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ApiHandlerTest {

    @Test
    public void testGetResponse() throws IOException {
        // Mock OkHttpClient and its related classes
        OkHttpClient mockClient = mock(OkHttpClient.class);
        Call mockCall = mock(Call.class);
        Response mockResponse = mock(Response.class);
        ResponseBody mockResponseBody = ResponseBody.create(
                "{ \"key\": \"value\" }",
                okhttp3.MediaType.parse("application/json")
        );

        // Set up the mocked response behavior
        when(mockResponse.isSuccessful()).thenReturn(true);
        when(mockResponse.body()).thenReturn(mockResponseBody);

        // Mock the Call object behavior
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);

        // Subclass ApiHandler to override getResponse for testing
        ApiHandler apiHandler = new ApiHandler(List.of("origin1"), List.of("destination1")) {
            @Override
            public String getResponse(String url) throws IOException {
                try (Response response = mockClient.newCall(new Request.Builder().url(url).build()).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }
                    return response.body().string();
                }
            }
        };

        // Create a test URL and get the response
        String testUrl = apiHandler.createURL();
        String response = apiHandler.getResponse(testUrl);

        // Assertions
        assertEquals("{ \"key\": \"value\" }", response);

        // Verify interactions
        verify(mockClient).newCall(any(Request.class));
        verify(mockCall).execute();
    }
}
