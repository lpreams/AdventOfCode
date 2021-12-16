package aocutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IO {
	
	private static final String SESSION;
	static {
		final File f = new File("txt/session.txt");
		if (!f.exists() || !f.isFile()) throw new RuntimeException("Please save your session token to " + f.getAbsolutePath());
		try (Scanner scan = new Scanner(f)) {
			SESSION = scan.next();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Scanner scanner(int year, int day) {
		return new Scanner(string(year, day));
	}
	
	public static String string(int year, int day) {
		return lines(year, day).collect(Collectors.joining(System.lineSeparator()));
	}
	
	public static Iterable<String> linesIter(int year, int day) {
		return new Iterable<String>() {
			public Iterator<String> iterator() {
				return lines(year, day).iterator();
			}
			
		};
	}
	
	public static Stream<String> lines(int year, int day) {
		try (BufferedReader br = new BufferedReader(new FileReader(pullFile(year, day)))) {
			return br.lines().toList().stream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static File pullFile(int year, int day) throws IOException {
		File d = new File("txt/" + year + "/");
		if (d.exists()) {
			if (!d.isDirectory()) throw new RuntimeException(d.getAbsolutePath() + " exists and is not a directory");
		} else {
			if (!d.mkdirs()) throw new RuntimeException("Unable to create directory " + d.getAbsolutePath());
		}
		File f = new File("txt/" + year + "/" + day + ".txt");
		if (f.exists()) {
			if (!f.isFile()) throw new RuntimeException(f.getAbsolutePath() + " exists but is not a file"); 
		} else {
			downloadFile(f, "https://adventofcode.com/"+year+"/day/"+day+"/input");
		}
		
		return f;
	}
	
	private static void downloadFile(File file, String fileURL) throws IOException {
		final HttpURLConnection httpConn = (HttpURLConnection) new URL(fileURL).openConnection();
		httpConn.setRequestProperty("Cookie", "session=" + SESSION);
		final int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {

//			System.err.println("Content-Type = " + httpConn.getContentType());
//			System.err.println("Content-Length = " + httpConn.getContentLength());

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(file);

			int bytesRead = -1;
			byte[] buffer = new byte[4096];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			System.err.println("File downloaded");
		} else {
			throw new IOException("No file to download. Server replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
	}

}
