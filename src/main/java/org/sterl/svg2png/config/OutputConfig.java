package org.sterl.svg2png.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.sterl.svg2png.util.FileUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class OutputConfig {
    private String inputDirectory;
    private String inputFile;
    private String outputName;
    private String outputDirectory;

    private List<FileOutput> files = new ArrayList<>();
    
    public static OutputConfig fromPath(String file) {
        OutputConfig result = new OutputConfig();
        File f = FileUtil.newFile(file);
        if (!f.exists()) throw new IllegalArgumentException(file + " not found!");
        
        if (f.isFile()) result.setInputFile(f.getAbsolutePath());
        else result.setInputDirectory(f.getAbsolutePath());

        
        result.setOutputDirectory("./"); // all into the current directory
        result.addOutput(); // one result
        return result;
    }

    public void setInputDirectory(String inputDirectory) {
		this.inputDirectory = inputDirectory;
	}

    public String getInputDirectory() {
		return this.inputDirectory;
	}

    public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String getInputFile() {
		return this.inputFile;
	}

	public void addOutput() {
        files.add(new FileOutput());
    }
    
    public boolean hasDirctoryOrFile() {
        return inputFile != null || inputDirectory != null;
    }

	public String getOutputName() {
		return outputName;
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public List<FileOutput> getFiles() {
		return files;
	}

}