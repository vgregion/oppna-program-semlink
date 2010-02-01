package se.vgr.crawler.repository;

import java.io.File;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfig;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.repository.config.RepositoryImplConfig;
import org.openrdf.repository.manager.LocalRepositoryManager;

import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.sail.config.SailRepositoryConfig;
import org.openrdf.sail.config.SailImplConfig;

import org.openrdf.sail.inferencer.fc.config.DirectTypeHierarchyInferencerConfig;
import org.openrdf.sail.inferencer.fc.config.ForwardChainingRDFSInferencerConfig;
import org.openrdf.sail.nativerdf.NativeStore;
import org.openrdf.sail.nativerdf.config.NativeStoreConfig;

/**
 * <p>Handles repository CRUD operations</p>
 * @author Johan Säll Larsson
 */

public class RepositoryManager extends LocalRepositoryManager {

	private String index;
	
	public RepositoryManager(File baseDir) throws RepositoryException {
		super(baseDir);
		this.initialize();
	}
	
	/**
	 * <p>Adds a repository configuration in the Sesame server.
	 * Need to call <code>this.getRepository(String repositoryId)</code> to be created
	 * as a directory</p>
	 * 
	 * @param repositoryId
	 * 
	 * @throws RepositoryException
	 * @throws RepositoryConfigException
	 */
	
	public synchronized void addRepository(String repositoryId) throws RepositoryException, RepositoryConfigException {
		
		if ( !repositoryDirExist(repositoryId) ) {
			
			SailImplConfig sailConfiguration = new NativeStoreConfig(index);
			ForwardChainingRDFSInferencerConfig forwardChainingConfiguration = new ForwardChainingRDFSInferencerConfig (sailConfiguration);
			DirectTypeHierarchyInferencerConfig inferenceConfiguration = new DirectTypeHierarchyInferencerConfig(forwardChainingConfiguration);
			RepositoryImplConfig repositoryConfiguration = new SailRepositoryConfig(inferenceConfiguration);
			RepositoryConfig configuration = new RepositoryConfig(repositoryId, repositoryConfiguration);
			configuration.setTitle(repositoryId);
			//this.initialize();
			this.addRepositoryConfig(configuration);
			
		}
		
	}
	
	/**
	 * <p>Adds a non-visible repository to the Sesame server</p>
	 * 
	 * @param directoryName
	 * @param suffix
	 * 
	 * @return <code>Repository</code>
	 */
	
	public synchronized Repository addDataRepository(String directoryName, String suffix) {
	
		if ( suffix == null ) {
			suffix = "";
		}
		
		File directory = new File(this.getBaseDir() + File.separator + REPOSITORIES_DIR + File.separator + directoryName + suffix);
		NativeStore nativeStore = new NativeStore(directory, index);
		return new SailRepository(nativeStore);
	
	}
	
	/**
	 * <p>Checks if a repository exists</p>
	 * @param repositoryId
	 * @return <code>true</code> if the repository exists
	 */
	
	public boolean repositoryDirExist(String repositoryId) {
		
		File directory = new File(this.getBaseDir() + File.separator + REPOSITORIES_DIR + File.separator + repositoryId );
		return directory.exists();
		
	}

	/* Setters for Spring */
	
	public void setIndex(String index) {
		this.index = index;
	}

}
