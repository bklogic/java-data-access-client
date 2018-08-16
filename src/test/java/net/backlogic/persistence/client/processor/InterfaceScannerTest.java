package net.backlogic.persistence.client.processor;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class InterfaceScannerTest {
	
	InterfaceScanner scanner;

	@Before
	public void setUp() throws Exception {
		this.scanner = new InterfaceScanner("net.backlogic.persistence.client.sample");
	}

	
	@Test
	public void testScan() {
		HashMap<String, List<Class<?>>> listMap = scanner.scan();
		
		List<Class<?>> list;
		
		list = listMap.get("repository");
		assertEquals("Repository interface list size should be 1", 1, list.size());		 
		displayList(list, "Repository");
		
		list = listMap.get("query");
		assertEquals("Query interface list size should not be 1", 1, list.size());		 
		displayList(list, "Query");		
		
		list = listMap.get("command");
		assertEquals("Command interface list size should not be 1", 1, list.size());		 
		displayList(list, "Command");				

		list = listMap.get("dao");
		assertEquals("Command interface list size should not be 0", 0, list.size());		 
		displayList(list, "Dao");				
		
	}

	
	private void displayList(List<Class<?>> list, String listName) {
		System.out.println(listName);
		for (Class<?> item : list ) {
			System.out.println( "    " + item.getName() );
		}
	}
	
}
