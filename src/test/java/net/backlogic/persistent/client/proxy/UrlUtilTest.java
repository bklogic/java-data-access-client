package net.backlogic.persistent.client.proxy;

import org.junit.Assert;
import org.junit.Test;

import net.backlogic.persistence.client.proxy.UrlUtil;

public class UrlUtilTest {
	@Test
	public void testFormatUrl() {
		Assert.assertEquals("null should be empty string", "", UrlUtil.formatUrl(null) );
		Assert.assertEquals("empty string should be empty string", "", UrlUtil.formatUrl("") );
		Assert.assertEquals("test should be /test", "/test", UrlUtil.formatUrl("test") );
		Assert.assertEquals("test/ should be /test", "/test", UrlUtil.formatUrl("test/") );
		Assert.assertEquals("/test/ should be /test", "/test", UrlUtil.formatUrl("/test/") );
		Assert.assertEquals("/module/test should be /module/test", "/module/test", UrlUtil.formatUrl("/module/test") );
		Assert.assertEquals("/module/test/ should be /module/test", "/module/test", UrlUtil.formatUrl("/module/test/") );
	}

	@Test
	public void testGetUrl() {
		Assert.assertEquals("null and test/ should be /test", "/test", UrlUtil.getUrl(null, "test/") );
		Assert.assertEquals("empty string and test/ should be /test", "/test", UrlUtil.getUrl("", "test/") );
		Assert.assertEquals("module and test/ should be /module/test", "/module/test", UrlUtil.getUrl("module", "test/") );
		Assert.assertEquals("/module/ and /test/ should be /module/test", "/module/test", UrlUtil.getUrl("/module/", "/test/") );
	}
	
	
}
