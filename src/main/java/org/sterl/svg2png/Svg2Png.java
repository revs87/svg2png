package org.sterl.svg2png;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.FileUtils;
import org.sterl.svg2png.config.FileOutput;
import org.sterl.svg2png.config.OutputConfig;
import org.sterl.svg2png.util.FileUtil;
import org.xml.sax.SAXException;

public class Svg2Png {

    private final OutputConfig outCfg;
    
    public Svg2Png(OutputConfig outCfg) {
        super();
        this.outCfg = outCfg;
    }

    public List<File> convert() throws IOException, TranscoderException {
        List<File> generated;
        if (outCfg.getInputFile() != null) {
            File input = FileUtil.newFile(outCfg.getInputFile());
            generated = convertFile(input, outCfg);
        } else {
            File dir = FileUtil.newFile(outCfg.getInputDirectory());
            @SuppressWarnings("unchecked")
            Collection<File> listFiles = (Collection<File>)FileUtils.listFiles(dir, new String[]{"svg"}, true);
            generated = new ArrayList<>();
            for (File file : listFiles) {
                generated.addAll(convertFile(file, outCfg));
            }
        }
        return generated;
    }

    private static List<File> convertFile(File input, OutputConfig cfg) throws IOException, TranscoderException, FileNotFoundException {
        TranscoderInput ti = new TranscoderInput(input.toURI().toString());

        // RATIO
        // ##########################################################################################################
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        org.w3c.dom.Document document = null;
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(input.toURI().toString());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			System.out.println("ParserConfigurationException");
			System.exit(1);
		} catch (SAXException e) {
			e.printStackTrace();
			System.out.println("SAXException");
			System.exit(1);
		}

        String split = "" +
                "(?<=[a-z])(?=\\d)" +    // space between letter and digit
                "|(?<=\\d)(?=[a-z])" +   // space between digit and letter
                "";
		String[] width = document.getDocumentElement().getAttribute("width").split(split);
		String[] height = document.getDocumentElement().getAttribute("height").split(split);
		if(!"px".equalsIgnoreCase(width[1])
				|| !"px".equalsIgnoreCase(height[1])) {
			System.out.println("Ratio can't be used against non px units. Check your svg default width and height.");
			System.exit(1);
		}
        int defaultWidth = Integer.valueOf(width[0]);
        int defaultHeight = Integer.valueOf(height[0]);
        // ##########################################################################################################

        PNGTranscoder t = new PNGTranscoder();
        List<File> generated = new ArrayList<>();

        StringBuilder info = new StringBuilder();
        for (FileOutput out : cfg.getFiles()) {
            info.setLength(0);
            info.append(input.getName());

//            System.out.println(defaultWidth);
//            System.out.println(defaultHeight);
//            System.out.println(out.getRatio());

            // RATIO
            if (out.getRatio() > 0) {
                out.setWidth((int)(defaultWidth * out.getRatio()));
                out.setHeight((int)(defaultHeight * out.getRatio()));
            };

            if (out.getWidth() > 0) {
                t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(out.getWidth()));
                info.append(" w").append(out.getWidth());
            } else t.removeTranscodingHint(PNGTranscoder.KEY_WIDTH);

            if (out.getHeight() > 0) {
                t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(out.getHeight()));
                info.append(" h").append(out.getHeight());
            } else t.removeTranscodingHint(PNGTranscoder.KEY_HEIGHT);
            
            File outputFile = out.toOutputFile(input, cfg.getOutputDirectory(), cfg.getOutputName());
            if (outputFile.exists()) {
                outputFile.delete();
            }
            outputFile.getParentFile().mkdirs();
            outputFile.createNewFile();
            try (FileOutputStream outStram = new FileOutputStream(outputFile)) {
                t.transcode(ti, new TranscoderOutput(outStram));
                generated.add(outputFile);
                info.append(" ").append(outputFile.getAbsolutePath());
            }
            System.out.println(info.toString());
        }
        return generated;
    }
}
