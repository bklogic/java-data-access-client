package net.backlogic.persistent.client.proxy;

import net.backlogic.persistence.client.proxy.UrlUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlUtilTest {
	@Test
	public void testFormatUrl() {
		assertEquals("", UrlUtil.formatUrl(null) , "null should be empty string");
		assertEquals("", UrlUtil.formatUrl(""), "empty string should be empty string" );
		assertEquals("/test", UrlUtil.formatUrl("test"), "test should be /test" );
		assertEquals("/test", UrlUtil.formatUrl("test/"), "test/ should be /test" );
		assertEquals("/test", UrlUtil.formatUrl("/test/"), "/test/ should be /test" );
		assertEquals("/module/test", UrlUtil.formatUrl("/module/test"), "/module/test should be /module/test" );
		assertEquals("/module/test", UrlUtil.formatUrl("/module/test/"), "/module/test/ should be /module/test");
	}

	@Test
	public void testGetUrl() {
		assertEquals("/test", UrlUtil.getUrl(null, "test/"), "null and test/ should be /test" );
		assertEquals("/test", UrlUtil.getUrl("", "test/"),"empty string and test/ should be /test" );
		assertEquals("/module/test", UrlUtil.getUrl("module", "test/"),"module and test/ should be /module/test" );
		assertEquals("/module/test", UrlUtil.getUrl("/module/", "/test/"), "/module/ and /test/ should be /module/test");
	}

}
