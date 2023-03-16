package net.backlogic.persistent.client.proxy;

import net.backlogic.persistence.client.proxy.UrlUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlUtilTest {
	@Test
	public void testFormatUrl() {
		assertEquals("null should be empty string", "", UrlUtil.formatUrl(null) );
		assertEquals("empty string should be empty string", "", UrlUtil.formatUrl("") );
		assertEquals("test should be /test", "/test", UrlUtil.formatUrl("test") );
		assertEquals("test/ should be /test", "/test", UrlUtil.formatUrl("test/") );
		assertEquals("/test/ should be /test", "/test", UrlUtil.formatUrl("/test/") );
		assertEquals("/module/test should be /module/test", "/module/test", UrlUtil.formatUrl("/module/test") );
		assertEquals("/module/test/ should be /module/test", "/module/test", UrlUtil.formatUrl("/module/test/") );
	}

	@Test
	public void testGetUrl() {
		assertEquals("null and test/ should be /test", "/test", UrlUtil.getUrl(null, "test/") );
		assertEquals("empty string and test/ should be /test", "/test", UrlUtil.getUrl("", "test/") );
		assertEquals("module and test/ should be /module/test", "/module/test", UrlUtil.getUrl("module", "test/") );
		assertEquals("/module/ and /test/ should be /module/test", "/module/test", UrlUtil.getUrl("/module/", "/test/") );
	}
	
	
}
