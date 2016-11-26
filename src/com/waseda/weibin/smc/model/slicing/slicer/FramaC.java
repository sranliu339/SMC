package com.waseda.weibin.smc.model.slicing.slicer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import com.waseda.weibin.smc.model.slicing.Slicer;
import com.waseda.weibin.smc.util.Command;
import com.waseda.weibin.smc.util.Log4j2;

/**
 * @author  Weibin Luo 
 * @version Created on Nov 23, 2016 12:24:17 PM
 */
public class FramaC extends Slicer {
	
	private String input;
	private List<String> files;
	private List<String> values;
	private String targetDirectory = "examples";
	
	public FramaC(String input) {
		// TODO Auto-generated constructor stub
		this.input = input;
	}
	
	@Override
	public void slice() {
		// TODO Auto-generated method stub
		String command = createCommand();
		Command.execute(command);
		
	}
	
	private void doSlice() {
		
	}
	
	private void changeCurrentDirectory() {
		// Switch to targetDirectory 
		String cmd = "cd" + targetDirectory;
		Command.execute(cmd);
	}
	
	private String createCommand() {
		String cmd = "";
		// Put input into Arraylist files and values
		processInput();
		String str = processFileNames() + "-slice-value " + processValueNames();
		String targetFileName = files.get(0).substring(0, files.get(0).length() - 2) + "_sliced.c";
		// Generate the slicing command
		// EG. of command: $ frama-c <source files> <desired slicing mode and appropriate options> -then-on 'Slicing export' -print
		// Create a new file
		cmd = "frama-c " + str + "-then-on 'Slicing export' -print -ocode " + targetFileName ;
		// Does not create a new file
//		cmd = "frama-c " + str + "-then-on 'Slicing export' -print";
		
//		Log4j2.logger.trace("===== Print the slicing command =====");
//		Log4j2.logger.trace(cmd);
		
		System.out.println(cmd);
		
		return cmd;
	}
	
	// Convert input to Arraylist files and values
	private void processInput() {
		files = new ArrayList<String>();
		values = new ArrayList<String>();
		// Convert the input to array by the separator " "
		String[] inputs = input.split(" ");
		Pattern p = Pattern.compile("(\\w)+\\.c");
		for (String string : inputs) {
			if (p.matcher(string).matches()) {
				files.add(string);
			} else {
				values.add(string);
			}
		}
	}
	
	// Convert Arraylist files into a single string 
	private String processFileNames() {
		String str = "";
		for (String string : files) {
			str += string + " ";
		}
		return str;
	}
	// Convert Arraylist values into a single string 
	private String processValueNames() {
		String str = "";
		for (String string : values) {
			str += string + " ";
		}
		return str;
	}
	
}
