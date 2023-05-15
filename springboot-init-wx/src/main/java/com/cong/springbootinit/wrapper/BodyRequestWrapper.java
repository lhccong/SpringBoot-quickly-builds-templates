package com.cong.springbootinit.wrapper;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author lhcong
 */
public class BodyRequestWrapper extends ParameterRequestWrapper {
    /**
     * 用于保存读取body中数据
     */
    private byte[] requestBody;

    public BodyRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        //读取请求的数据保存到本类当中
        requestBody = StreamUtils.copyToByteArray(request.getInputStream());
    }

    /**
     * 覆盖（重写）父类的方法
     * @return
     * @throws IOException
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    /**
     * 覆盖（重写）父类的方法
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {return false;}
            @Override
            public boolean isReady() {return false;}
            @Override
            public void setReadListener(ReadListener readListener) {}
            @Override
            public int read() throws IOException {
                int read = byteArrayInputStream.read();
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return read;
            }
        };
    }


    public void setRequestBody(byte[] requestBody) {
        this.requestBody = requestBody;
    }
}
