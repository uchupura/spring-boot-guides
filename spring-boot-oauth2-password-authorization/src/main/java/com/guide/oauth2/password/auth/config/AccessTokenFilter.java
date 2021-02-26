package com.guide.oauth2.password.auth.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class AccessTokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("[[[[[[[[[[[[STARTED]]]]]]]]]]]]]]");

        CharResponseWrapper wrappedResponse = new CharResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, wrappedResponse);
        byte[] bytes = wrappedResponse.getByteArray();
        String out = new String(bytes);
        log.info("Response String: {}", out);

        response.getOutputStream().write(out.getBytes());

        log.info("[[[[[[[[[[[[ENDED]]]]]]]]]]]]]]");
    }

    private static class ByteArrayServletStream extends ServletOutputStream
    {
        ByteArrayOutputStream baos;

        ByteArrayServletStream(ByteArrayOutputStream baos)
        {
            this.baos = baos;
        }

        public void write(int param) throws IOException
        {
            baos.write(param);
        }

        @Override
        public boolean isReady()
        {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener)
        {}
    }

    private static class ByteArrayPrintWriter
    {
        private ByteArrayOutputStream baos    = new ByteArrayOutputStream();
        private PrintWriter pw      = new PrintWriter(baos);
        private ServletOutputStream     sos     = new ByteArrayServletStream(baos);

        public PrintWriter getWriter()
        {
            return pw;
        }

        public ServletOutputStream getStream()
        {
            return sos;
        }

        byte[] toByteArray()
        {
            return baos.toByteArray();
        }
    }

    public class CharResponseWrapper extends HttpServletResponseWrapper
    {
        private ByteArrayPrintWriter    output;
        private boolean                 usingWriter;

        public CharResponseWrapper(HttpServletResponse response)
        {
            super(response);
            usingWriter = false;
            output = new ByteArrayPrintWriter();
        }

        public byte[] getByteArray()
        {
            return output.toByteArray();
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException
        {
            if (usingWriter)
            {
                super.getOutputStream();
            }
            usingWriter = true;
            return output.getStream();
        }

        @Override
        public PrintWriter getWriter() throws IOException
        {
            if (usingWriter)
            {
                super.getWriter();
            }
            usingWriter = true;
            return output.getWriter();
        }

        public String toString()
        {
            return output.toString();
        }
    }
}
