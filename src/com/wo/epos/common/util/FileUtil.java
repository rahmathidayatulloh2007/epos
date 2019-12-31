package com.wo.epos.common.util;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.primefaces.model.UploadedFile;

import com.wo.epos.common.constant.CommonConstants;

public class FileUtil {

	public static final String CHAR_COMMA = ",";

	static Logger logger = Logger.getLogger(FileUtil.class);

	private static final int DEFAULT_COLUMNS_COUNT = 24;

	private static FileUtil me;

	public static FileUtil getInstance() {
		if (FileUtil.me == null) {
			FileUtil.me = new FileUtil();
		}

		return FileUtil.me;
	}

	private FileUtil() {

	}

	private void close(Closeable resource, File file) throws IOException {
		if (resource != null) {
			resource.close();
		}
	}

	public static String getExtention(String fileName) {
		String ext = "";

		// find "." position
		int dotPos = 0;
		if (fileName.contains("."))
			dotPos = fileName.lastIndexOf(".");
		if (dotPos > 0) {
			ext = fileName.substring(dotPos + 1);
		}

		return ext;
	}

	public static String getFileNameWithoutExtention(String fileName) {
		int dotPos = 0;
		if (fileName.contains(".")) {
			dotPos = fileName.lastIndexOf(".");
			return fileName.substring(0, dotPos);
		}
		return fileName;
	}

	//
	// private List<String> readAsCsv(File file) throws IOException {
	// FileReader fileReader = new FileReader(file);
	// CsvListReader csvListReader = new CsvListReader(fileReader,
	// CsvPreference.STANDARD_PREFERENCE);
	// List<String> records = new ArrayList<String>();
	// List<String> singleLineList = new ArrayList<String>();
	//
	// while ((singleLineList = csvListReader.read()) != null) {
	// StringBuilder builder = new StringBuilder();
	//
	// for (String s : singleLineList) {
	// builder.append(s);
	// builder.append(CHAR_COMMA);
	// }
	//
	// // remove last comma;
	// if (builder.length() > 1) {
	// builder.deleteCharAt(builder.length() - 1);
	// }
	//
	// String line = builder.toString();
	//
	// if (line != null && line.length() > FileUtil.DEFAULT_COLUMNS_COUNT) {
	// records.add(line);
	// }
	// }
	//
	// fileReader.close();
	//
	// return records;
	// }

	@SuppressWarnings("unused")
	private List<String> readAsExcel(File file) throws IOException {
		List<String> result = new ArrayList<String>();
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;

		int rows; // No of rows
		rows = sheet.getPhysicalNumberOfRows();

		int cols = 0; // No of columns
		int tmp = 0;

		// This trick ensures that we get the data properly even if it
		// doesn't start from first few rows
		for (int i = 0; (i < 10) || (i < rows); i++) {
			row = sheet.getRow(i);
			if (row != null) {
				tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				if (tmp > cols) {
					cols = tmp;
				}
			}
		}

		StringBuilder builder = null;

		for (int r = 0; r < rows; r++) {
			builder = new StringBuilder();
			row = sheet.getRow(r);
			if (row != null) {
				for (int c = 0; c < FileUtil.DEFAULT_COLUMNS_COUNT; c++) {
					cell = row.getCell(c);
					if (cell != null) {
						int cellType = cell.getCellType();

						switch (cellType) {
						case HSSFCell.CELL_TYPE_FORMULA:
							builder.append((int) cell.getNumericCellValue());
							break;
						default:
							builder.append(cell.toString());
							break;
						}
					}
					builder.append(FileUtil.CHAR_COMMA);
				}
			}
			// remove last comma;
			if (builder.length() > 1) {
				builder.deleteCharAt(builder.length() - 1);
			}

			String line = builder.toString();

			if (line != null && line.length() > FileUtil.DEFAULT_COLUMNS_COUNT) {
				result.add(line);
			}
		}

		return result;
	}

	@SuppressWarnings("unused")
	private Reader readReader(File file) throws IOException {
		return new BufferedReader(new FileReader(file));
	}

	// /*
	// * Modified TPS to read excel file
	// */
	// public List<String> readRecords(File file) throws IOException {
	// if (file.getName().toLowerCase().endsWith("csv")) {
	// return this.readAsCsv(file);
	// } else if (file.getName().toLowerCase().endsWith("xls")) {
	// return this.readAsExcel(file);
	// } else {
	// throw new IOException(
	// "Unrecognized file format, only csv or xls allowed");
	// }
	// }

	public File uniqueFile(File filePath, String fileName) throws IOException {
		File file = new File(filePath, fileName);

		if (file.exists()) {
			// Split filename and add braces, e.g. "name.ext" --> "name[",
			// "].ext".
			String prefix;
			String suffix;
			int dotIndex = fileName.lastIndexOf(".");

			if (dotIndex > -1) {
				prefix = fileName.substring(0, dotIndex) + "[";
				suffix = "]" + fileName.substring(dotIndex);
			} else {
				prefix = fileName + "[";
				suffix = "]";
			}

			int count = 0;

			// Add counter to filename as long as file exists.
			while (file.exists()) {
				if (count < 0) { // int++ restarts at -2147483648 after
					// 2147483647.
					throw new IOException("No unique filename available for "
							+ fileName + " in path " + filePath.getPath() + ".");
				}

				// Glue counter between prefix and suffix, e.g. "name[" + count
				// + "].ext".
				file = new File(filePath, prefix + (count++) + suffix);
			}
		}

		return file;
	}

	public void write(File file, InputStream input, boolean append)
			throws IOException {
		BufferedOutputStream output = null;

		try {
			output = new BufferedOutputStream(
					new FileOutputStream(file, append));
			int data = -1;
			while ((data = input.read()) != -1) {
				output.write(data);
			}
		} finally {
			this.close(input, file);
			this.close(output, file);
		}

	}

	public void deleteFile(String filePath) throws IOException {
		if (filePath != null && !filePath.isEmpty()) {
			File temp = new File(filePath);
			if (temp.exists()) {
				boolean success = temp.delete();
				if (!success)
					throw new IOException("Fail to delete file");
			}
			temp = null;
		}
	}

	public String handleFileUpload(InputStream is, String oldFileName,
			String newFolderDir, String newFileNameWithoutExtension)
			throws IOException {
		String fullFileName = "";
		if (!newFolderDir.contains(CommonConstants.FILE_SEPARATOR))
			newFolderDir = newFolderDir.concat(CommonConstants.FILE_SEPARATOR);

		File folder = new File(newFolderDir);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		folder = null;

		if (newFileNameWithoutExtension == null
				|| newFileNameWithoutExtension.isEmpty())
			fullFileName = newFolderDir.concat(oldFileName);
		else
			fullFileName = newFolderDir.concat(newFileNameWithoutExtension
					.concat(".").concat(getExtention(oldFileName)));

		writeFile(is, fullFileName);

		return fullFileName;
	}

	public void writeFile(final InputStream is, final String fullFileName)
			throws FileNotFoundException, IOException {
		// check if file exist
		File fileExist = new File(fullFileName);
		if (!fileExist.exists()) {
			FileOutputStream fos = new FileOutputStream(fullFileName);
			int BUFFER_SIZE = 8192;
			byte[] buffer = new byte[BUFFER_SIZE];
			int a;
			while (true) {
				a = is.read(buffer);
				if (a < 0)
					break;
				fos.write(buffer, 0, a);
				fos.flush();
			}
			fos.close();
			is.close();
		}
		fileExist = null;
	}

	@SuppressWarnings("resource")
	public String copyFile(String oldFilePath, String newFolderPath,
			String newFileName, boolean moveFile) throws IOException {
		File destFile = new File(newFolderPath);
		FileChannel source = null;
		FileChannel destination = null;
		String fullFilePath = null;

		if (!newFolderPath.contains(CommonConstants.FILE_SEPARATOR))
			newFolderPath = newFolderPath
					.concat(CommonConstants.FILE_SEPARATOR);
		if (!destFile.exists())
			destFile.mkdirs();
		fullFilePath = newFolderPath.concat(newFileName);
		destFile = new File(fullFilePath);
		if (!destFile.exists())
			destFile.createNewFile();
		source = new FileInputStream(oldFilePath).getChannel();
		destination = new FileOutputStream(destFile).getChannel();
		destination.transferFrom(source, 0, source.size());

		if (source != null)
			source.close();
		source = null;
		if (destination != null)
			destination.close();
		destination = null;
		destFile = null;
		if (moveFile)
			deleteFile(oldFilePath);

		return fullFilePath;
	}

	public static String getFileNameFromPath(String fileFullPath) {
		String fileName;

		if (fileFullPath == null || fileFullPath.isEmpty())
			return null;

		if (!fileFullPath.contains(CommonConstants.FILE_SEPARATOR))
			fileName = fileFullPath;
		else
			fileName = fileFullPath.substring(fileFullPath
					.lastIndexOf(CommonConstants.FILE_SEPARATOR) + 1);

		return fileName;
	}

	public String getContentType(String filePath) throws MalformedURLException,
			IOException {
		return getContentType(filePath, null);
	}

	public String getContentType(String filePath, String defaultType)
			throws MalformedURLException, IOException {
		URL u = new URL("file:" + filePath);
		URLConnection uc = u.openConnection();
		String type = uc.getContentType();
		if (type.equals("content/unknown") && defaultType != null
				&& !defaultType.isEmpty())
			type = defaultType;
		return type;
	}

	public String getDownloadedFileName(String newFileName, String oldFileName) {
		return newFileName + "." + getExtention(oldFileName);
	}

	public static String getFullPathForReport(String fileFullPath,
			String rawFileName) {
		String fileName;
		if (fileFullPath == null || fileFullPath.isEmpty())
			return fileFullPath;

		if (!fileFullPath.endsWith(CommonConstants.FILE_SEPARATOR))
			fileName = fileFullPath + CommonConstants.FILE_SEPARATOR;
		else
			fileName = fileFullPath;

		return fileName + rawFileName;
	}

	public static String getPathFromFullPath(String fileFullPath) {
		String fullPath;
		if (fileFullPath == null || fileFullPath.isEmpty())
			return fileFullPath;

		if (fileFullPath.contains(CommonConstants.FILE_SEPARATOR)) {
			if (fileFullPath.endsWith(CommonConstants.FILE_SEPARATOR))
				fullPath = fileFullPath;
			else
				fullPath = fileFullPath.substring(0, fileFullPath
						.lastIndexOf(CommonConstants.FILE_SEPARATOR) + 1);
		} else {
			fullPath = fileFullPath + CommonConstants.FILE_SEPARATOR;
		}

		return fullPath;
	}

	@SuppressWarnings("unused")
	public static String saveUploadedImageToLocalHarddrive(
			UploadedFile uploadedFile, String localFullPath) throws IOException {

		String todaysDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
		todaysDate = sdf.format(new java.util.Date());

		String folderDir = localFullPath;

		String filePath = folderDir;
		String fileName = uploadedFile.getFileName();

		File folder = new File(folderDir);
		if (!folder.exists()) {
			logger.debug("Create Folder:" + folder.mkdirs());
		}

		String uploadFileName = uploadedFile.getFileName();
		String writeFileName = getFileNameWithoutExtention(uploadFileName)
				+ todaysDate + "." + getExtention(uploadFileName);
		String fullPath = folderDir + writeFileName;

		File imgFile = new File(fullPath);

		Image image = null;
		image = ImageIO.read(uploadedFile.getInputstream());
		ImageIO.write((RenderedImage) image, "jpg", imgFile);

		return writeFileName;
	}
}