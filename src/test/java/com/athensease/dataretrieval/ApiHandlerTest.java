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

        // Use reflection to inject the mock client into ApiHandler
        ApiHandler apiHandler = new ApiHandler(List.of("origin1"), List.of("destination1"));
        injectMockHttpClient(apiHandler, mockClient);

        // Create a test URL and get the response
        String testUrl = apiHandler.createURL();
        String response = apiHandler.getResponse(testUrl);

        // Assertions
        assertEquals("{ \"key\": \"value\" }", response);

        // Verify interactions
        verify(mockClient).newCall(any(Request.class));
        verify(mockCall).execute();
    }

    // Helper method to inject the mocked OkHttpClient into ApiHandler
    private void injectMockHttpClient(ApiHandler apiHandler, OkHttpClient mockClient) {
        try {
            java.lang.reflect.Field clientField = ApiHandler.class.getDeclaredField("client");
            clientField.setAccessible(true);
            clientField.set(apiHandler, mockClient);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mock OkHttpClient", e);
        }
    }
}
