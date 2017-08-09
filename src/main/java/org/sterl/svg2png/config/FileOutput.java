package org.sterl.svg2png.config;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.sterl.svg2png.util.FileUtil;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Meta information where to output one converted file.
 */
@Data @NoArgsConstructor
public class FileOutput {
    private int height = -1;
    private int width = -1;
	private float ratio;
    private String name;
    private String directory;
    private String nameSuffix;
    private String namePrefix;

    public File toOutputFile(File source, String basePath, String outName) {
        // either a set path or the one of the parent
        String path = basePath != null ? basePath : source.getParent();
        if (path == null) path = "";
        // if the configured path is absolute we take it, otherwise we append it
        if (directory != null && directory.startsWith("/")) {
            path = directory;
        } 
        if (directory != null) {
            if (path.length() > 0) {
                path += "/" + directory;
            } else {
                path += directory;
            }
        }
        // setting the name
        path += "/" + buildName(FilenameUtils.getBaseName(source.getName()), name != null ? name : outName, namePrefix, nameSuffix);
        
        return FileUtil.newFile(path);
    }
    
    static String buildName(String srcName, String outName, String prefix, String suffix) {
        String name = outName != null ? outName : srcName;
        String result;
        if (prefix != null && !name.startsWith(prefix)) result = prefix + name;
        else result = name;
        
        if (suffix != null && !result.endsWith(suffix)) result += suffix;
        if (!result.endsWith(".png")) result += ".png";
        return result;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getDirectory() {
		return this.directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public float getRatio() {
		return this.ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public String getNamePrefix() {
		return namePrefix;
	}

	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}

	public String getNameSuffix() {
		return nameSuffix;
	}

	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}
}
