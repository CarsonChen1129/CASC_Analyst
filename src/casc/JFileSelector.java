package casc;

import java.awt.Frame;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

public class JFileSelector {
    private FileFilter acceptFileType ;
    private Frame frame = new Frame();
    public JFileChooser fileChooser;
    
    public JFileSelector(){
    	
        acceptFileType = new javax.swing.filechooser.FileFilter() {
            public boolean accept(File file) { 
                String  name = file.getName().toLowerCase();
                return  name.endsWith(".xls")||
                         file.isDirectory();
            }
            public String getDescription() { 
                return "xls file";
            }
        };
        
        fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
    
       
    }
    
    File openFile(){
    	
    	
    	File currentFile = null;
    	
    	fileChooser.setFileFilter(acceptFileType);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogTitle("Please choose the file");
        
        int result = fileChooser.showOpenDialog(frame);
        
        
        if(result == JFileChooser.APPROVE_OPTION){
        	
        	currentFile = fileChooser.getSelectedFile();
        	
        }
        
        return currentFile;
    }
       
    
}
	

