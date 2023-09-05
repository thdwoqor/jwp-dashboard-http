package org.apache.coyote.http11.response;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpResponseHeaders {

    private final Map<String, String> headers;

    private HttpResponseHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpResponseHeaders empty() {
        return new HttpResponseHeaders(new LinkedHashMap<>());
    }

    public void add(final String key, final String value) {
        headers.put(key, value);
    }

    public Set<Entry<String, String>> getEntrySet() {
        return headers.entrySet();
    }
}
