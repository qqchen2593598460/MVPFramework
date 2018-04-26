package com.fif.baselib.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 弹窗类
 *
 * @author RXXU
 *         暂时不用 谁用谁照着下面写
 */
public class FileUtil {

	/**
	 * 删除目录下所有的文件
	 * @param file 要删除的文件目录
	 */
	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (File childFile : childFiles) {
				delete(childFile);
			}
			file.delete();
		}
	}
	/**
	 * 删除目录下所有的文件,不删除的文件目录
	 * @param file 要删除的文件目录
	 */
	public static void deleteOnleFile(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				return;
			}

			for (File childFile : childFiles) {
				delete(childFile);
			}
		}
	}
	/**
	 * 检测文件是否存在
	 */
	public static boolean isFileExist(String path)
	{
		if (TextUtils.isEmpty(path)) {
			return false;
		}
		File file = new File(path);
        return file.exists();
    }

	/**
	 * 检测文件目录是否存在
	 */
	public static boolean isFileDirExist(String path){
		if (TextUtils.isEmpty(path)) {
			return false;
		}
		File file = new File(path);
        return file.exists() && file.isDirectory();
    }
	/**
	 * 检测文件夹是否存在，如果不存在则创建文件夹
	 */
	public static boolean checkFileDirOrMk(String path) {
		if(!isFileDirExist(path))
		{
			makeDir(path);
			return false;
		}
		return true;
	}
	/**
	 * 判断SD卡是否可用
	 */
	public static boolean isSdcardMounted()
	{
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
	/**
	 * 创建文件路径
	 * @param path 文件路径或文件名，例如：/sdcard/msc/ 或 /sdcard/msc/msc.log
	 */
	public static void makeDir(String path)
	{
		if(TextUtils.isEmpty(path))
			return;
		File f = new File(path);
		if(!path.endsWith("/"))
			f =  f.getParentFile();
		if(!f.exists())
			f.mkdirs();
	}

	/**
	 * 获取文件大小
	 */
	public static long getFileSize(String filePath) {
		long size = 0;

		File file = new File(filePath);
		if (file != null && file.exists()) {
			size = file.length();
		}
		return size;
	}

	/**
	 * 获取文件大小
	 */
	public static String getFileSize(long size) {
		if (size <= 0)
			return "0";
		java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
		float temp = (float) size / 1024;
		if (temp >= 1024) {
			return df.format(temp / 1024) + "M";
		} else {
			return df.format(temp) + "K";
		}
	}

	/**
	 * 转换文件大小
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSize(long fileS) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 获取目录文件大小
	 */
	public static long getDirSize(File dir) {
		if (dir == null) {
			return 0;
		}
		if (!dir.isDirectory()) {
			return 0;
		}
		long dirSize = 0;
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				dirSize += file.length();
			} else if (file.isDirectory()) {
				dirSize += file.length();
				dirSize += getDirSize(file); // 递归调用继续统计
			}
		}
		return dirSize;
	}

	/**
	 * 将bitmap转换为file对象
	 */
	public static File saveBitmap2File(Bitmap bm, String fileName) throws IOException {//将Bitmap类型的图片转化成file类型，便于上传到服务器
		String path = Environment.getExternalStorageDirectory() + "/Ask";
		File dirFile = new File(path);
		if(!dirFile.exists()){
			dirFile.mkdir();
		}
		File myCaptureFile = new File(path + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
		return myCaptureFile;

	}
}
