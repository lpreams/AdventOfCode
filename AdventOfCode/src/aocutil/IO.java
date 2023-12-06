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
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IO {
		
	/**
	 * Scan over the problem input
	 * @param year
	 * @param day
	 * @return
	 */
	public static Scanner scanner(int year, int day) {
		return new Scanner(string(year, day));
	}
	
	/**
	 * Get the entire problem input in a single String
	 * @param year
	 * @param day
	 * @return
	 */
	public static String string(int year, int day) {
		return lines(year, day).collect(Collectors.joining(System.lineSeparator()));
	}
	
	/**
	 * Iterate over the problem input line by line, splitting each line
	 * @param year
	 * @param day
	 * @param split
	 * @return
	 */
	public static Iterable<String[]> linesIterSplit(int year, int day, String split) {
		return () -> lines(year, day).map(str -> str.split(split)).iterator();
	}
	
	/**
	 * Iterate over the problem input line by line
	 * @param year
	 * @param day
	 * @return
	 */
	public static Iterable<String> linesIter(int year, int day) {
		return () -> lines(year, day).iterator();
	}
	
	/**
	 * Stream the problem input line by line
	 * 
	 * Base method for other public methods in IO
	 * 
	 * @param year
	 * @param day
	 * @return
	 */
	public static Stream<String> lines(int year, int day) {
		try (BufferedReader br = new BufferedReader(new FileReader(pullFile(year, day)))) {
			return br.lines().toList().stream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Gets a problem input by year and day, either from the local filesystem if it exists, else from AOC
	 * @param year
	 * @param day
	 * @return handle to a file containing the problem input for the given year and day
	 * @throws IOException
	 */
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
	
	/**
	 * Blindly downloads the specified URL to the specified file
	 * @param file where to download to
	 * @param fileURL where to download from
	 * @throws IOException uh oh
	 */
	private static void downloadFile(File file, String fileURL) throws IOException {
		final String SESSION;
		final File f = new File("txt/session.txt");
		if (!f.exists() || !f.isFile()) throw new RuntimeException("Please save your session token to " + f.getAbsolutePath());
		try (Scanner scan = new Scanner(f)) {
			SESSION = scan.next();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		final HttpURLConnection httpConn = (HttpURLConnection) new URL(fileURL).openConnection();
		httpConn.setRequestProperty("Cookie", "session=" + SESSION);
		final int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			
			// opens input stream from the HTTP connection
			final InputStream inputStream = httpConn.getInputStream();
			// opens an output stream to save into file
			final FileOutputStream outputStream = new FileOutputStream(file);

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
