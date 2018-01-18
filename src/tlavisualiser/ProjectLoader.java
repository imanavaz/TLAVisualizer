package tlavisualiser;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.management.NotCompliantMBeanException;

import stateextractor.StateExtractor;
import tlc2.tool.EvalException;
import tlc2.tool.ModelChecker;
import tlc2.tool.fp.FPSetConfiguration;
import tlc2.tool.management.ModelCheckerMXWrapper;
import tlc2.util.FP64;
import util.SimpleFilenameToStream;

public class ProjectLoader {

	public String projectLoadStatus;
	private StateExtractor stateExtractor = null;
	
	/**
	 * Create the application.
	 */
	public ProjectLoader() {
		
		projectLoadStatus = "not initiated";
		
	}
	
	public ProjectLoader(String filePath, ExecutionArea execPanel){
		
		load(filePath, execPanel);
	}
	
	public void load(String fileStr, ExecutionArea execPanel) {
		
		String specFile = getFile(fileStr);
		String paths[] = getPaths(fileStr);
		
		SimpleFilenameToStream resolver = new SimpleFilenameToStream();
		resolver.addPath(paths);
		
		String configFile = getConfigFile(fileStr);
		
		FPSetConfiguration fpSetConfig = new FPSetConfiguration();
		FP64.Init(getFPInit());
		try {
			stateExtractor = new StateExtractor(specFile, configFile, null, false, null, resolver, null, fpSetConfig);
			new ModelCheckerMXWrapper((ModelChecker) stateExtractor);
			stateExtractor.modelCheck();
			
			execPanel.setStateExtractor(stateExtractor);
			
		} catch (EvalException e) {
			
			e.printStackTrace();
			projectLoadStatus = "file loading not successfull";//null;
			
		} catch (IOException e) {

			e.printStackTrace();
			projectLoadStatus =  "file loading not successfull";//null;
			
		} catch (NotCompliantMBeanException e) {
			
			e.printStackTrace();
			projectLoadStatus =  "file loading not successfull";//null;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			projectLoadStatus =  "file loading not successfull";//null;
			
		}
		
		projectLoadStatus = specFile;
	}
	
	
	
	public String getFile(String fileStr) {
		String file = fileStr;
		return file.substring(file.lastIndexOf(File.separator)+1,file.lastIndexOf("."));
	}
	
	public String[] getPaths(String fileStr) {
		String file = fileStr;
		Vector<String> paths = new Vector<String>();
		
		paths.add(file.substring(0, file.lastIndexOf(File.separator)));
		String[] pathsArray = new String[paths.size()];
		paths.toArray(pathsArray);
		
		return pathsArray;
	}

	public String getConfigFile(String fileStr) {
		return getFile(fileStr);
	}

	public int getFPInit() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
