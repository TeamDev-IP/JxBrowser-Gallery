package com.teamdev.jxbrowser.production;

import com.teamdev.jxbrowser.net.HttpHeader;
import com.teamdev.jxbrowser.net.HttpStatus;
import com.teamdev.jxbrowser.net.UrlRequestJob;
import com.teamdev.jxbrowser.net.callback.InterceptUrlRequestCallback;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.teamdev.jxbrowser.production.ApplicationContents.*;

public final class UrlRequestInterceptor implements InterceptUrlRequestCallback {

    private static final String CONTENT_TYPE = "Content-Type";

    @Override
    public Response on(Params params) {
        String url = params.urlRequest().url();
        System.out.println("InterceptUrlRequestCallback " + url);
        if (url.contains(APP_URL)) {
            URI uri = URI.create(url);
            String filePath;
            if (uri.getPath().equals("/")) {
                filePath = CONTENT_ROOT + INDEX_HTML;
            } else {
                filePath = CONTENT_ROOT + uri.getPath();
            }
            Path pathOnDisk = Paths.get(APPLICATION_LOCATION, filePath);
            UrlRequestJob job = getUrlRequestJob(params, filePath);
            try {
                readFile(pathOnDisk, job);
                job.complete();
                return InterceptUrlRequestCallback.Response.intercept(job);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return InterceptUrlRequestCallback.Response.proceed();
    }

    private static UrlRequestJob getUrlRequestJob(InterceptUrlRequestCallback.Params params, String file) {
        UrlRequestJob.Options.Builder builder = UrlRequestJob.Options.newBuilder(HttpStatus.OK);
        builder.addHttpHeader(getContentType(file));
        return params.newUrlRequestJob(builder.build());
    }

    private static void readFile(Path filePath, UrlRequestJob job) throws IOException {
        try (FileInputStream stream = new FileInputStream(filePath.toFile())) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) > 0) {
                if (bytesRead != buffer.length) {
                    buffer = Arrays.copyOf(buffer, bytesRead);
                }
                job.write(buffer);
            }
        }
    }

    private static HttpHeader getContentType(String file) {
        return HttpHeader.of(CONTENT_TYPE, MimeTypes.mimeType(file).value());
    }
}
