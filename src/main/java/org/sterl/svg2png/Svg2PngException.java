package org.sterl.svg2png;

import org.sterl.svg2png.config.OutputConfig;

import lombok.Getter;

public class Svg2PngException extends Exception {
    @Getter
    private OutputConfig cfg;
	private Exception e;

    public Svg2PngException(Exception e, OutputConfig config) {
        super(e);
        this.setE(e);
        this.cfg = config;
    }

	public Exception getE() {
		return e;
	}

	public void setE(Exception e) {
		this.e = e;
	}
	
	public OutputConfig getCfg() {
		return cfg;
	}

	public void setCfg(OutputConfig cfg) {
		this.cfg = cfg;
	}
}
