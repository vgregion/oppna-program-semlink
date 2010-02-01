package se.vgr.crawler.util;

import java.io.File;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfig;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.repository.config.RepositoryImplConfig;
import org.openrdf.repository.manager.LocalRepositoryManager;
import org.openrdf.repository.manager.RepositoryManager;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.sail.config.SailRepositoryConfig;
import org.openrdf.sail.config.SailImplConfig;
import org.openrdf.sail.inferencer.fc.DirectTypeHierarchyInferencer;
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.openrdf.sail.nativerdf.NativeStore;
import org.openrdf.sail.nativerdf.config.NativeStoreConfig;

import se.vgr.crawler.index.LuceneIndex;

public class RepositoryUtils {

	/**
	 * <p>Creates a repository which can be found by sesame workplace</p>
	 * 
	 * @throws RepositoryException
	 * @throws RepositoryConfigException
	 */
	
	public static void createSesameRepository(File directory, File systemDirectory, String RepositoryName, Repository repository, String sesameIndex) throws RepositoryException, RepositoryConfigException {
		
		if ( !directory.exists() ) {
			
			RepositoryManager manager = new LocalRepositoryManager(systemDirectory);		
			SailImplConfig nativeStoreConfig = new NativeStoreConfig(sesameIndex);
			RepositoryImplConfig sailRepoConfig = new SailRepositoryConfig(nativeStoreConfig);
			RepositoryConfig repoConfig = new RepositoryConfig(RepositoryName, sailRepoConfig);			
			repoConfig.setTitle(RepositoryName);
			
			manager.initialize();
			manager.addRepositoryConfig(repoConfig);
			
			repository = manager.getRepository(RepositoryName);
			repository.shutDown();
			manager.shutDown();
			
		}
		
	}
	
	public static Repository createNativeRepository(File directory, String sesameIndex, LuceneIndex luceneIndex, boolean inference ){
		
		NativeStore nativeStore = new NativeStore(directory, sesameIndex);
		//LuceneSail luceneSail = new LuceneSail();
		//luceneSail.setBaseSail(nativeStore);
		//luceneSail.setLuceneIndex(luceneIndex);
		DirectTypeHierarchyInferencer hierarchyInferencer = new DirectTypeHierarchyInferencer(new ForwardChainingRDFSInferencer(nativeStore));
		
		if(inference){
			return new SailRepository(hierarchyInferencer);
		} else {
			// return new SailRepository(luceneSail);
			return new SailRepository(nativeStore);
		}
		
	}
	
	public static Repository createNativeRepository(File directory, String sesameIndex){
		NativeStore nativeStore = new NativeStore(directory, sesameIndex);
		return new SailRepository(nativeStore);
	}
}
