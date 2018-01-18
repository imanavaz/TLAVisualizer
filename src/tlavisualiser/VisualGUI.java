package tlavisualiser;

import java.awt.EventQueue;

import javax.management.NotCompliantMBeanException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import stateextractor.StateExtractor;
import tlc2.tool.EvalException;
import tlc2.tool.ModelChecker;
import tlc2.tool.fp.FPSetConfiguration;
import tlc2.tool.management.ModelCheckerMXWrapper;
import tlc2.util.FP64;
import util.FilenameToStream;
import util.SimpleFilenameToStream;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JProgressBar;
import javax.swing.plaf.ProgressBarUI;

import com.sun.glass.events.KeyEvent;

public class VisualGUI{

	private JFrame frame;
	
	private ProjectLoader pLoader;
	private JProgressBar progressBar;
	private ExecutionArea executionPanel;
	private JPanel statusBar;
	private JLabel statusMsg;
	private JFileChooser fc;
	/**
	 * Launch the application.
	 */ 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
		            // Set cross-platform Java L&F (also called "Metal")
		        UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
			    } 
			    catch (UnsupportedLookAndFeelException e) {
			       // handle exception
			    }
			    catch (ClassNotFoundException e) {
			       // handle exception
			    }
			    catch (InstantiationException e) {
			       // handle exception
			    }
			    catch (IllegalAccessException e) {
			       // handle exception
			    }
			        
			    //new SwingApplication(); //Create and show the GUI.
				
				try {
					VisualGUI window = new VisualGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VisualGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		fc = new JFileChooser();
		
		pLoader = new ProjectLoader();
		
		frame = new JFrame();
		frame.setBounds(100, 50, 1280, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		statusBar = new JPanel();
		
		frame.getContentPane().add(statusBar,BorderLayout.SOUTH);
		
		statusMsg = new JLabel(" " + "Good Day!", JLabel.LEFT);
		statusMsg.setForeground(Color.black);
		statusMsg.setToolTipText("TLA+ file being processed.");
		statusBar.setLayout(new BorderLayout());
        //statusBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        statusBar.add(statusMsg, BorderLayout.WEST);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		//frame.getContentPane().setBackground(Color.RED);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		menuBar.add(fileMenu);
		
		
		JMenuItem mntmOpenFile = new JMenuItem("Open TLA File");
		mntmOpenFile.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
				int fcResult = fc.showOpenDialog(null);
				if (fcResult == JFileChooser.APPROVE_OPTION) {
					statusMsg.setText(fc.getSelectedFile().toString());
				}
				System.out.println("Selected TLA file: " + fcResult);
	        }
		});
		fileMenu.add(mntmOpenFile);
		
		
		JMenuItem mntmLoadProject = new JMenuItem("Load Project");
		mntmLoadProject.setMnemonic(KeyEvent.VK_L);
		mntmLoadProject.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	progressBar.setIndeterminate(true);
	        	int fcResult = fc.showOpenDialog(null);
				if (fcResult == JFileChooser.APPROVE_OPTION) {
					statusMsg.setText(fc.getSelectedFile().toString());
					pLoader.load(fc.getSelectedFile().toString(), executionPanel);
				}	        	
				statusMsg.setText(pLoader.projectLoadStatus);
				progressBar.setIndeterminate(false);
			}
		});
		fileMenu.add(mntmLoadProject);
		
		
		fileMenu.addSeparator();
		
		
		JMenuItem menuItemClose = new JMenuItem ("Exit");
		menuItemClose.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	            System.exit(0);
	        }
	    });
		fileMenu.add(menuItemClose);
		
		
		//JTabbedPane tabbedPane = new JTabbedPane();
		//frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		executionPanel = new ExecutionArea();
		frame.getContentPane().add(executionPanel, BorderLayout.CENTER);
		
		
		//tabbedPane.addTab("Project Properties", null, lp, null);
		
		//JPanel panel = new FileViewerPanel();
		//tabbedPane.addTab("File View", null, panel, null);
		
		//
		//tabbedPane.addTab(null, null, executionPanel, null);
		
		progressBar = new JProgressBar();
		statusBar.add(progressBar, BorderLayout.EAST);
		
	}

	
	


}
