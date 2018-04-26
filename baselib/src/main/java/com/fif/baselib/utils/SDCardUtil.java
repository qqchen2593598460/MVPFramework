package com.fif.baselib.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class SDCardUtil {
	/**
	 * 获得SD卡总大小
	 */
	public static long getSDTotalSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return  blockSize * totalBlocks;
	}

	/**
	 * 获得sd卡剩余容量，即可用大小
	 */
	public static long getSDAvailableSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		stat.restat(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return blockSize * availableBlocks;
	}

	/**
	 * 获得机身内存总大小
	 */
	public static long getRomTotalSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return blockSize * totalBlocks;
	}

	/**
	 * 获得机身可用内存
	 */
	public static long getRomAvailableSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return blockSize * availableBlocks;
	}
	
	  /**
     * sd卡是否存在
     * 
     * @return true表示存在
     *
     */
    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
}
