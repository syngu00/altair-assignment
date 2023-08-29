package hu.syngu00.dataprocessor.logging.wrapper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class SpringResponseWrapper extends HttpServletResponseWrapper {
    private ServletOutputStream outputStream;
    private PrintWriter writer;
    private ServletOutputStreamWrapper copier;

    public SpringResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (this.writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        } else {
            if (this.outputStream == null) {
                this.outputStream = this.getResponse().getOutputStream();
                this.copier = new ServletOutputStreamWrapper(this.outputStream);
            }

            return this.copier;
        }
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (this.outputStream != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        } else {
            if (this.writer == null) {
                this.copier = new ServletOutputStreamWrapper(this.getResponse().getOutputStream());
                this.writer = new PrintWriter(new OutputStreamWriter(this.copier, this.getResponse().getCharacterEncoding()), true);
            }

            return this.writer;
        }
    }

    @Override
    public void flushBuffer() throws IOException {
        if (this.writer != null) {
            this.writer.flush();
        } else if (this.outputStream != null) {
            this.copier.flush();
        }
    }

    public byte[] getContentAsByteArray() {
        return this.copier != null ? this.copier.getCopy() : new byte[0];
    }
}
