package ru.kac;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.mozilla.universalchardet.UniversalDetector;

@Slf4j
@NoArgsConstructor
public class AutoDetectEncodingApp {

    @SneakyThrows
    private String parseToStringExample() {
        String encoding;
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("test_Windows1251.csv");
        byte[] textAsBytes = IOUtils.toByteArray(stream);
        InputStream bis = new ByteArrayInputStream(textAsBytes);
        UniversalDetector detector = new UniversalDetector();

        detector.handleData(textAsBytes, 0, textAsBytes.length);
        detector.dataEnd();

        encoding = detector.getDetectedCharset();
        if (encoding != null) {
            log.debug("Detected encoding = " + encoding);
        } else {
            log.debug("No encoding detected.");
        }

        return new String(textAsBytes, Charset.forName(encoding));
}


    @SneakyThrows
    public void run() {
        log.info("text = {}", parseToStringExample());
    }


    @SneakyThrows
    public static void main(String[] args) {
        AutoDetectEncodingApp demoApp = new AutoDetectEncodingApp();
        demoApp.run();
    }

}
