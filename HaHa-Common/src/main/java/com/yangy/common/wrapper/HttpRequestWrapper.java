package com.yangy.common.wrapper;

import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@Slf4j
public class HttpRequestWrapper extends HttpServletRequestWrapper {
	
	private byte[] data;

	public HttpRequestWrapper(HttpServletRequest request) {
		super(request);
		ServletInputStream inputStream = null;
		try {
			inputStream = request.getInputStream();
			if (inputStream != null) {
				data = IOUtils.toByteArray(inputStream);
			}else {
				data = new byte[]{};
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("catch an exception:", e);
				}
			}
		}
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
		ServletInputStream servletInputStream = new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
			}

			@Override
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}
		};
		return servletInputStream;

	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream(), Charsets.UTF_8));
	}

	public String getBody() {
		return Objects.isNull(this.data) ? StringUtils.EMPTY : new String(this.data,Charsets.UTF_8);
	}

}
