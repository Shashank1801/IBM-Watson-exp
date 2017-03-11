package in.shekhar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

public class TextSpeech {
	public static void main(String[] args) {
		TextToSpeech ts = new TextToSpeech("c07b8ee1-37d1-4d73-ace0-e5ab39b20fc4", "2tpKGNdSqIQt");
		String text = null;

		try {
			text = getText();
		} catch (FileNotFoundException fe) {
			System.err.println("File error : " + fe.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error in extracting text");
		}

		if (text != null && text.length() > 0) {
			getAudioFile(ts, text);
		} else {
			System.out.println("NO TEXT TO CONVERT");
		}

		System.out.println("Fin.");
	}

	public static void getAudioFile(TextToSpeech ts, String text) {
		try {
			InputStream stream = ts.synthesize(text, Voice.EN_ALLISON, AudioFormat.WAV).execute();
			InputStream in = WaveUtils.reWriteWaveHeader(stream);
			OutputStream out = new FileOutputStream("hello_world.wav");
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			out.close();
			in.close();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getAudioFile(TextToSpeech ts, String text, Voice voice, AudioFormat af, String filename) {
		try {
			InputStream stream = ts.synthesize(text, voice, af).execute();
			InputStream in = WaveUtils.reWriteWaveHeader(stream);
			OutputStream out = new FileOutputStream(filename);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			out.close();
			in.close();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getText() throws IOException {
		FileInputStream fin = new FileInputStream("./input.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
		StringBuffer sb = new StringBuffer();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		return sb.toString();
	}
}
