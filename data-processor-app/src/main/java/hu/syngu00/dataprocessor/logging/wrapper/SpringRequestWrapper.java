package hu.syngu00.dataprocessor.logging.wrapper;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SpringRequestWrapper extends HttpServletRequestWrapper {
    private byte[] body;

    public SpringRequestWrapper(HttpServletRequest request) {
        super(request);

        try {
            this.body = IOUtils.toByteArray(request.getInputStream());
        } catch (IOException e) {
            this.body = new byte[0];
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        return new ServletInputStream() {
            final ByteArrayInputStream byteArray = new ByteArrayInputStream(SpringRequestWrapper.this.body);

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int read() {
                return this.byteArray.read();
            }
        };
    }
}
