/**
 * 
 */
package net.backlogic.persistence.client.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import net.backlogic.persistence.client.annotation.Command;
import net.backlogic.persistence.client.annotation.Dao;
import net.backlogic.persistence.client.annotation.Query;
import net.backlogic.persistence.client.annotation.Read;
import net.backlogic.persistence.client.annotation.Repository;

/**
 * @author Ken
 *
 * Responsible for scanning packages for persistence interfaces.
 * 
 * Internally, it relies on LukeHutch FastClasspathScanner to do the job. 
 * FastClasspathScanner API doc is here:
 * http://static.javadoc.io/io.github.lukehutch/fast-classpath-scanner/2.0.18/io/github/lukehutch/fastclasspathscanner/package-summary.html
 */
public class InterfaceScanner {
	/*
	 * base package
	 */
	String basePackage;
	
	
	public InterfaceScanner(String basePackage) {
		this.basePackage = basePackage;
	}
	
	
	/*
	 * Scan the give packages and return lists of persistence interface.
	 * Key: interface type, value: interface list
	 */
	public HashMap<String, List<Class<?>>> scan() {
		HashMap<String, List<Class<?>>> interfaceLists = new HashMap<String, List<Class<?>>>();
		
		//scan for DAO interface
		interfaceLists.put("dao", scan(Dao.class));
		
		//scan for Repository interface
		interfaceLists.put("repository", scan(Repository.class));
		
		//scan for Query interface
		interfaceLists.put("query", scan(Query.class));
		
		//scan for Command interface
		interfaceLists.put("command", scan(Command.class));
		
		return interfaceLists ;
	}
	
	
	private List<Class<?>> scan(Class<?> annotation) {
		List<Class<?>> list = new ArrayList<Class<?>>();
		
		//scanner
		FastClasspathScanner scanner = new FastClasspathScanner(basePackage);
		
		//scan
		scanner.matchClassesWithAnnotation(annotation, 			
			(matchingClass) -> {
				
//				scanner.matchClassesWithMethodAnnotation(Read.class,
//					(matchingClass, matchingMethod) -> {
				
				list.add(matchingClass);
		} ).scan();
		
		return list;
	}
	
}
