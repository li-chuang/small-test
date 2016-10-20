package com.lichuang.Oct21;

public class DownloadUtilsTest {

	public static void main(String args[]) throws Exception {
		final DownloadUtils downUtil = new DownloadUtils(
				"http://apache.fayea.com/tomcat/tomcat-7/v7.0.72/bin/apache-tomcat-7.0.72-windows-x64.zip",
				"tomcat-7.0.72.zip", 3);

		downUtil.download();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (downUtil.getCompleteRate() < 1) {
					System.out.println("已完成:" + downUtil.getCompleteRate());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
	}

}
